/**
 * Licensed to Cloudera, Inc. under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Cloudera, Inc. licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance  with the License.  
 * You may obtain a copy of the License at
 * 
 *    http:www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.cmapi.deploy.services;

import com.cloudera.api.model.ApiConfig;
import com.cloudera.api.model.ApiConfigList;
import com.cloudera.api.model.ApiHostRef;
import com.cloudera.api.model.ApiRole;
import com.cloudera.api.model.ApiRoleConfigGroup;
import com.cloudera.api.model.ApiRoleConfigGroupRef;
import com.cloudera.api.model.ApiService;
import com.cloudera.api.model.ApiServiceConfig;
import com.cloudera.api.model.ApiServiceList;
import com.cloudera.api.v10.ServicesResourceV10;

import com.cloudera.cmapi.deploy.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.ini4j.Ini;
import org.ini4j.Wini;

public class HDFSService extends ClusterService {

  private static String SERVICE_TYPE="HDFS";
  private enum RoleType { DATANODE, NAMENODE, SECONDARYNAMENODE, BALANCER, GATEWAY, HTTPFS, FAILOVERCONTROLLER, JOURNALNODE, NFSGATEWAY };
  private static final Logger LOG = Logger.getLogger(HDFSService.class);

  public void deploy(Wini config, ServicesResourceV10 servicesResource) {

    setName(config.get(Constants.HDFS_CONFIG_SECTION, 
                       Constants.HDFS_SERVICE_NAME_PARAMETER));
    setServiceType(SERVICE_TYPE);

    // Make sure service isn't already deployed:
    boolean provisionRequired = false;
    try {
      provisionRequired = servicesResource.readService(name) == null;
    } catch (Exception e) {
      provisionRequired = true;
    }

    if (!provisionRequired) {
      LOG.info(SERVICE_TYPE + " services already deployed. Skipping...");
    } else {
      LOG.info("Deploying " +  SERVICE_TYPE + " service...");
      ApiServiceList hdfsServices = new ApiServiceList();
      ApiService hdfsService = new ApiService();
      hdfsService.setType(SERVICE_TYPE);
      hdfsService.setName(name);

      // Set service configuration:
      Ini.Section serviceConfigSection = 
        config.get(Constants.HDFS_SERVICE_CONFIG_SECTION);
      ApiServiceConfig serviceConfig = getServiceConfig(serviceConfigSection);
      hdfsService.setConfig(serviceConfig);
      
      // Create service roles:
      List<ApiRole> hdfsRoles = new ArrayList<ApiRole>();
      
      LOG.info("Adding NameNode role...");
      hdfsRoles.addAll(createRoles(RoleType.NAMENODE.name(), null,
                                   config.get(Constants.HDFS_CONFIG_SECTION, 
                                              Constants.HDFS_NAMENODE_HOST_PARAMETER).split(",")));

      LOG.info("Adding Secondary NameNode role...");
      hdfsRoles.addAll(createRoles(RoleType.SECONDARYNAMENODE.name(), null,
                                   config.get(Constants.HDFS_CONFIG_SECTION, 
                                              Constants.HDFS_SECONDARYNAMENODE_HOST_PARAMETER).split(",")));

      LOG.info("Adding DataNode roles...");
      hdfsRoles.addAll(createRoles(RoleType.DATANODE.name(), null,
                                   config.get(Constants.HDFS_CONFIG_SECTION, 
                                              Constants.HDFS_DATANODE_HOSTS_PARAMETER).split(",")));

      LOG.info("Adding Gateway roles...");
      hdfsRoles.addAll(createRoles(RoleType.GATEWAY.name(), null,
                                   config.get(Constants.HDFS_CONFIG_SECTION,
                                              Constants.HDFS_GATEWAY_HOSTS_PARAMETER).split(",")));

      for (ApiRole role : hdfsRoles) {
        LOG.debug("role type=" + role.getType() + ", host=" + role.getHostRef());
      }
      
      hdfsService.setRoles(hdfsRoles);
      hdfsServices.add(hdfsService);
      servicesResource.createServices(hdfsServices);

      LOG.info("HDFS services successfully created, now setting role configurations...");
  
      updateRoleConfigurations(config, servicesResource);
    }
  }
}