/**
 * Licensed to Cloudera, Inc. under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Cloudera, Inc. licenses this file
 * to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance  with the License.
 * You may obtain a copy of the License a
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
import com.cloudera.api.model.ApiServiceConfig;
import com.cloudera.api.v10.ServicesResourceV10;

import org.apache.log4j.Logger;

import org.ini4j.Ini;
import org.ini4j.Wini;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Base class for cluster services (HDFS, YARN, etc.). The general flow for
 * deploying a service is the following:
 * <p><ul>
 * <li> Create and populate the service-wide configuration.
 * <li> Create and populate role objects for each service role (e.g. for HDFS
 * the DataNode, NameNode, etc. roles).
 * <li> Use the above objects to create the service.
 * <li> Then iterate through each role and update the configuration parameters
 * associated with that role.
 * </ul></p>
 *
 * Valid service types as of CDH5: HDFS, MAPREDUCE, HBASE, OOZIE, ZOOKEEPER,
 * HUE, YARN, IMPALA, FLUME, HIVE, SOLR, SQOOP, KS_INDEXER, SQOOP_CLIENT,
 * SENTRY, ACCUMULO16, KMS, SPARK_ON_YARN
 */
public abstract class ClusterService {

  /**
   * Log4j logger.
   */
  private static final Logger LOG = Logger.getLogger(ClusterService.class);

  /**
   * Configuration object.
   */
  protected Wini config;

  /**
   * Cloudera Manager API object providing access to functionality for
   * configuring, creating, etc. services on a cluster.
   */
  protected ServicesResourceV10 servicesResource;

  /**
   * Configurable name that's assigned to a service.
   */
  protected String name;

  /**
   * Service type (e.g. HDFS, YARN, ZOOKEEPER, etc.)
   */
  protected String type;

  /**
   * Execute the workflow to deploy a service and associated roles to a
   * cluster.
   *
   */
  public abstract void deploy();

  /**
   * Set required parameters.
   *
   * @param config Configuration parameters.
   * @param servicesResource Cloudera Manager API object providing access
   * to functionality for configuring, creating, etc. services on a cluster.
   */
  public ClusterService(final Wini config,
                        final ServicesResourceV10 servicesResource) {
    this.config = config;
    this.servicesResource = servicesResource;
  }

  /**
   * Perform any required setup tasks for this service before starting.
   *
   * @return true if setup tasks complete successfully, false otherwise.
   */
  public abstract boolean preStartInitialization();

  /**
   * Perform any required setup tasks for this service after starting.
   *
   * @return true if setup tasks complete successfully, false otherwise.
   */
  public abstract boolean postStartInitialization();

  /**
   * Set name for this service.
   *
   * @param name Service name.
   */
  public final void setName(final String name) {
    this.name = name;
  }

  /**
   * Return name assigned to this service.
   *
   * @return service name.
   */
  public final String getName() {
    return name;
  }

  /**
   * Set service type.
   *
   * @param type Service type.
   */
  public final void setServiceType(final String type) {
    this.type = type;
  }

  /**
   * Get the service type.
   *
   * @return the service type.
   */
  public final String getServiceType() {
    return type;
  }

  /**
   * Create and populate a service configuration object.
   *
   * @param serviceConfigSection Section from configuration file containing
   * parameters specific to a service.
   *
   * @return ApiServiceConfig object populated with configuration parameters
   * for a service.
   */
  protected final ApiServiceConfig getServiceConfig(final Ini.Section serviceConfigSection) {

    ApiServiceConfig serviceConfig = new ApiServiceConfig();
    if (serviceConfigSection != null && serviceConfigSection.size() > 0) {
      for (Map.Entry<String, String> entry : serviceConfigSection.entrySet()) {
        LOG.debug("Adding service config key/value: " +
                  entry.getKey() + "=" + entry.getValue());
        serviceConfig.add(new ApiConfig(entry.getKey(), entry.getValue()));
      }
    }
    return serviceConfig;
  }

  /**
   * Create role (ApiRole) objects for a specific cluster role.
   *
   * @param roleType Type for role object(s), for example NAMENODE,
   * RESOURCEMANAGER, etc.
   * @param roleName Optional name for role.
   * @param hosts One or more hosts associated with the role.
   *
   * @return List of new role objects.
   */
  protected final List<ApiRole> createRoles(final String roleType,
                                            final String roleName,
                                            final String[] hosts) {

    List<ApiRole> roles = new ArrayList();

    for (String host : hosts) {
      ApiRole apiRole = new ApiRole();
      apiRole.setType(roleType);
      apiRole.setHostRef(new ApiHostRef(host));
      if (roleName != null && !roleName.isEmpty()) {
        apiRole.setName(roleName);
      }
      LOG.debug("Adding host " + host + " for role " + roleType);
      roles.add(apiRole);
    }

    return roles;
  }

  /**
   * Update configuration for roles associated with this service.
   */
  protected final void updateRoleConfigurations() {

    String roleType = null;
    for (ApiRoleConfigGroup roleConfigGroup : servicesResource.getRoleConfigGroupsResource(name).readRoleConfigGroups()) {
      roleType = roleConfigGroup.getRoleType();
      LOG.info("Looking for configuration params for role type=" + roleType);
      ApiConfigList roleConfigList = new ApiConfigList();
      // Multiple service types have the GATEWAY role, so we need a way
      // to differentiate config parameters:
      if (roleType.equals("GATEWAY")) {
        roleType = type + "_" + "GATEWAY";
      }
      Ini.Section roleConfigSection = config.get(roleType);
      if (roleConfigSection != null && roleConfigSection.size() > 0) {
        LOG.info("Found configuration params for role type=" + roleType);
        for (Map.Entry<String, String> entry : roleConfigSection.entrySet()) {
          LOG.debug("Role type=" + roleType +
                    ", adding config key/value: " +
                    entry.getKey() + "=" + entry.getValue());
          roleConfigList.add(new ApiConfig(entry.getKey(), entry.getValue()));
        }
      }
      ApiRoleConfigGroup apiRoleConfigGroup = new ApiRoleConfigGroup();
      apiRoleConfigGroup.setConfig(roleConfigList);
      servicesResource.getRoleConfigGroupsResource(name).
        updateRoleConfigGroup(roleConfigGroup.getName(),
                              apiRoleConfigGroup,
                              ("Updating role config for " +
                               roleConfigGroup.getName()));
    }
  }
}
