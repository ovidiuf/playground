#!/bin/bash
#
# bash script for running a generic project
#
# $Id: NovaOrdisWorkEnviromentRunSh2.txt,v 1.1 2011/07/27 14:45:14 wiki Exp wiki $
#

reldir=`dirname $0`

cygwin=false;
os=unix
case "`uname`" in
    CYGWIN*)
        cygwin=true
        os=windows
        sep="\;"
        ;;
esac

CLASSPATH="${reldir}/../target/encryption.jar"

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

${JAVA_HOME}/bin/java ${JAVA_OPTS} -cp "${CLASSPATH}" playground.encryption.Main ${ARGS}
