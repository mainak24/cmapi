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
package com.cloudera.cmapi.deploy;

/**
 * Configuration parameter constants for CM API deployment code.
 */
public final class Constants {

  private Constants() {
  }

  public static final String CM_PUBLIC_HOSTNAME_PARAMETER = "cm_public_hostname";
  public static final String CM_PRIVATE_HOSTNAME_PARAMETER = "cm_private_hostname";
  public static final String CM_USERNAME_PARAMETER = "cm_user";
  public static final String CM_PASSWORD_PARAMETER = "cm_password";
  public static final String CM_MGMT_SERVICE_NAME_PARAMETER = "mgmt_service_name";
  public static final String CM_MANAGEMENT_ROLETYPES_PARAMETER = "cm_management_roletypes";
  public static final String CLUSTER_CONFIG_SECTION = "CLUSTER";
  public static final String CLUSTER_NAME_PARAMETER = "cluster_name";
  public static final String CLUSTER_CDH_VERSION_PARAMETER = "cluster_version";
  public static final String CLUSTER_HOSTS_PARAMETER = "cluster_hosts";
  public static final String CLUSTER_SERVICES_PARAMETER = "services";
  public static final String ZOOKEEPER_NAME_PARAMETER = "zk_name";
  public static final String ZOOKEEPER_HOSTS_PARAMETER = "zk_hosts";
  public static final String ZOOKEEPER_SERVICE_CONFIG_SECTION = "ZOOKEEPER_SERVICE_CONFIG";
  public static final String ZOOKEEPER_ROLE_CONFIG_SECTION = "ZOOKEEPER_ROLE_CONFIG";
  public static final String HDFS_CONFIG_SECTION = "HDFS";
  public static final String HDFS_SERVICE_CONFIG_SECTION = "HDFS_SERVICE_CONFIG";
  public static final String HDFS_SERVICE_NAME_PARAMETER = "hdfs_name";
  public static final String HDFS_NAMENODE_NAME_PARAMETER = "hdfs_namenode_name";
  public static final String HDFS_NAMENODE_HOST_PARAMETER = "hdfs_namenode_host";
  public static final String HDFS_SECONDARYNAMENODE_HOST_PARAMETER = "hdfs_secondary_namenode_host";
  public static final String HDFS_DATANODE_HOSTS_PARAMETER = "hdfs_datanode_hosts";
  public static final String HDFS_GATEWAY_HOSTS_PARAMETER = "hdfs_gateway_hosts";
  public static final String YARN_CONFIG_SECTION = "YARN";
  public static final String YARN_SERVICE_CONFIG_SECTION = "YARN_SERVICE_CONFIG";
  public static final String YARN_SERVICE_NAME_PARAMETER = "yarn_name";
  public static final String YARN_RESOURCEMANAGER_HOST_PARAMETER = "yarn_resourcemanager_host";
  public static final String YARN_JOBHISTORY_SERVER_HOST_PARAMETER = "yarn_jobhistory_server_host";
  public static final String YARN_NODEMANAGER_HOSTS_PARAMETER = "yarn_nodemanager_hosts";
  public static final String YARN_GATEWAY_HOSTS_PARAMETER = "yarn_gateway_hosts";
  public static final String HIVE_CONFIG_SECTION = "HIVE";
  public static final String HIVE_SERVICE_CONFIG_SECTION = "HIVE_SERVICE_CONFIG";
  public static final String HIVE_SERVICE_NAME_PARAMETER = "hive_name";
  public static final String HIVE_METASTORE_HOST_PARAMETER = "hive_metastore_host";
  public static final String HIVE_HS2_HOSTS_PARAMETER = "hive_hs2_hosts";
  public static final String HIVE_GATEWAY_HOSTS_PARAMETER = "hive_gateway_hosts";
  public static final String IMPALA_CONFIG_SECTION = "IMPALA";
  public static final String IMPALA_SERVICE_CONFIG_SECTION = "IMPALA_SERVICE_CONFIG";
  public static final String IMPALA_SERVICE_NAME_PARAMETER = "impala_name";
  public static final String IMPALA_STATESTORE_HOST_PARAMETER = "impala_statestore_host";
  public static final String IMPALA_CATALOGSERVER_HOST_PARAMETER = "impala_catalogserver_host";
  public static final String IMPALA_IMPALAD_HOSTS_PARAMETER = "impala_impalad_hosts";
  public static final String OOZIE_CONFIG_SECTION = "OOZIE";
  public static final String OOZIE_SERVER_HOST_PARAMETER = "oozie_server_host";
  public static final String OOZIE_SERVICE_CONFIG_SECTION = "OOZIE_SERVICE_CONFIG";
  public static final String OOZIE_SERVICE_NAME_PARAMETER = "oozie_name";
  public static final String SPARK_CONFIG_SECTION = "SPARK_ON_YARN";
  public static final String SPARK_HISTORYSERVER_HOST_PARAMETER = "spark_yarn_historyserver_host";
  public static final String SPARK_GATEWAY_HOSTS_PARAMETER = "spark_gateway_hosts";
  public static final String SPARK_SERVICE_CONFIG_SECTION = "SPARK_SERVICE_CONFIG";
  public static final String SPARK_SERVICE_NAME_PARAMETER = "spark_name";
  public static final String KAFKA_CONFIG_SECTION = "KAFKA";
  public static final String KAFKA_SERVICE_CONFIG_SECTION = "KAFKA_SERVICE_CONFIG";
  public static final String KAFKA_SERVICE_NAME_PARAMETER = "kafka_name";
  public static final String KAFKA_MIRRORMAKER_HOST_PARAMETER = "kafka_mirrormaker_host";
  public static final String KAFKA_BROKER_HOSTS_PARAMETER = "kafka_broker_hosts";
  public static final String HUE_CONFIG_SECTION = "HUE";
  public static final String HUE_SERVICE_CONFIG_SECTION = "HUE_SERVICE_CONFIG";
  public static final String HUE_SERVICE_NAME_PARAMETER = "hue_name";
  public static final String HUE_SERVER_HOST_PARAMETER = "hue_server_host";
  public static final String SQOOP2_CONFIG_SECTION = "SQOOP2";
  public static final String SQOOP2_SERVICE_CONFIG_SECTION = "SQOOP2_SERVICE_CONFIG";
  public static final String SQOOP2_SERVICE_NAME_PARAMETER = "sqoop2_name";
  public static final String SQOOP2_SERVER_HOST_PARAMETER = "sqoop2_server_host";
  public static final String FLUME_CONFIG_SECTION = "FLUME";
  public static final String FLUME_SERVICE_CONFIG_SECTION = "FLUME_SERVICE_CONFIG";
  public static final String FLUME_SERVICE_NAME_PARAMETER = "flume_name";
  public static final String FLUME_AGENT_HOSTS_PARAMETER = "flume_agent_hosts";
}
