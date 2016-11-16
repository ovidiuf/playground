#!/bin/bash
#
# example of shell script that replaces an old logging subsystem handler with a new one and re-establishes
# the category/handler relationships.
#

function update-logging-subsystem-handler() {

    local jdg_home=$1
    local cli_interface=$2
    local cli_port=$3

    [ -z "${jdg_home}" ] && { echo "'jdg_home' not provided" 1>&2; exit 1; }
    [ -d ${jdg_home} ] || { echo "'jdg_home' ${jdg_home} not a directory" 1>&2; exit 1; }
    [ -z "${cli_interface}" ] && { echo "'cli_interface' not provided" 1>&2; exit 1; }
    [ -z "${cli_port}" ] && { echo "'cli_port' not provided" 1>&2; exit 1; }

${jdg_home}/bin/cli.sh --connect=${cli_interface}:${cli_port} <<EOF
#
# batch the CLI operations, they should be executed in just one interaction with the server
#
batch
#
# disassociate the INFINISPAN file-handler from categories that use it, otherwise we won't be able to delete it
#
/subsystem=logging/logger=org.infinispan:remove-handler(name="INFINISPAN")
/subsystem=logging/logger=org.infinispan.server.endpoint:remove-handler(name="INFINISPAN")
/subsystem=logging/logger=org.jboss.as.clustering.infinispan:remove-handler(name="INFINISPAN")
#
# remove the obsolete INFINISPAN file-handler
#
/subsystem=logging/file-handler=INFINISPAN:remove
#
# create a new INFINISPAN periodic-rotating-file-handler and configure appropriately
#
/subsystem=logging/periodic-rotating-file-handler=INFINISPAN:add(file={"path"=>"infinispan.log", "relative-to"=>"jboss.server.log.dir"}, suffix=".yyyy-MM-dd", append="false")
/subsystem=logging/periodic-rotating-file-handler=INFINISPAN:write-attribute(name="formatter", value="%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n")
/subsystem=logging/periodic-rotating-file-handler=INFINISPAN:write-attribute(name="autoflush", value="true")
#
# re-create the category/handler associations
#
/subsystem=logging/logger=org.infinispan:add-handler(name="INFINISPAN")
/subsystem=logging/logger=org.infinispan.server.endpoint:add-handler(name="INFINISPAN")
/subsystem=logging/logger=org.jboss.as.clustering.infinispan:add-handler(name="INFINISPAN")
run-batch
EOF
}

#update-logging-subsystem-handler /Users/ovidiu/runtime/jboss-datagrid-6.6.0-server localhost 9999
update-logging-subsystem-handler /Users/ovidiu/runtime/jboss-datagrid-6.6.0-server localhost 10199

