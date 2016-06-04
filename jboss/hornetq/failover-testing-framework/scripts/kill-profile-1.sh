#!/bin/bash

node_name="A"

pid=$(ps -ef | grep java | grep "jboss.node.name=${node_name}" | awk '{print $2}')

[ -z "${pid}" ] && { echo "no JBoss profile ${node_name} found running"; exit 1; }

kill -9 ${pid}