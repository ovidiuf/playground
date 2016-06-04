#!/bin/sh
#
# Infinispan PoC run script. Starts a processing node.
#
# $Id: NovaOrdisWorkEnvironmentRunSh.txt,v 1.8 2011/07/12 17:28:43 wiki Exp wiki $
#

INFINISPAN_HOME=C:/runtime/infinispan-5.1.4.FINAL
NODE_NAME=C

reldir=`dirname $0`

cygwin=false;
os=unix
sep=":"
case "`uname`" in
    CYGWIN*)
        cygwin=true
        os=windows
        sep="\;"
        ;;
esac

CLASSPATH="\
${reldir}/../src/main/resources${sep}\
${reldir}/../target/infinispan-harness.jar${sep}\
${INFINISPAN_HOME}/lib/log4j-1.2.16.jar${sep}\
${INFINISPAN_HOME}/infinispan-core.jar${sep}\
${INFINISPAN_HOME}/lib/rhq-pluginAnnotations-3.0.4.jar${sep}\
${INFINISPAN_HOME}/lib/jboss-logging-3.1.0.GA.jar${sep}\
${INFINISPAN_HOME}/lib/jgroups-3.0.9.Final.jar${sep}\
${INFINISPAN_HOME}/lib/jboss-marshalling-1.3.11.GA.jar${sep}\
${INFINISPAN_HOME}/lib/jboss-marshalling-river-1.3.11.GA.jar${sep}\
${INFINISPAN_HOME}/lib/jboss-transaction-api_1.1_spec-1.0.0.Final.jar"

while [ "$1" != "" ]; do
    if [ "$1" = "-debug" ]; then
        DEBUG_OPTS_SOCKET="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=12348"
        DEBUG_OPTS_SHMEM="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_shmem,server=y,suspend=y,address=run"
        JAVA_OPTS="${JAVA_OPTS} ${DEBUG_OPTS_SHMEM}"
    elif [ "$1" = "-profile" ]; then
        PROFILING_OPTS="-agentlib:yjpagent"
        JAVA_OPTS="${JAVA_OPTS} ${PROFILING_OPTS}"
    else
        ARGS="${ARGS} $1"
    fi
    shift
    continue;
done

${JAVA_HOME}/bin/java ${JAVA_OPTS} -cp "${CLASSPATH}" -Dplayground.infinispan.node.config.file=squaretrade-infinispan.xml -Dlog.file.name=${reldir}/../logs/nodeC.log -Djgroups.tcp.port=7802 com.novaordis.playground.infinispan.Main ${NODE_NAME}