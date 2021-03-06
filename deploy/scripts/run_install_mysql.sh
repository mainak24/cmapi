#!/bin/bash
# Licensed to Cloudera, Inc. under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  Cloudera, Inc. licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

configfile=$1

if [ -z $configfile ]
then
    echo "usage: $0 config-file-name"
    echo "config-file-name should be the name of a file containing configuration parameters for use by these scripts"
    exit 1
fi

source $configfile

scp -i $pemfile -o StrictHostKeyChecking=no -p install_mysql.sh $user@$cmserver:~/
ssh -t -i $pemfile $user@$cmserver "~/install_mysql.sh"
