#
# LTS - long-term support
#
#FROM jenkins/jenkins:lts
FROM jenkinsci/blueocean

#
# We need to be root to create directories and assign them to the runtime user, but we'll switch back to the
# runtime user as soon as we can.
#

USER root

#
# We create the image directories to store state that changes only once and then becomes part of the image.
# Anything else will be maintained in JENKINS_HOME.
#

ENV JENKINS_PLUGIN_DIR /var/jenkins/plugins
ENV JENKINS_WAR_DIR /var/jenkins/war

RUN mkdir -p ${JENKINS_PLUGIN_DIR} && \
    chown -R jenkins:jenkins ${JENKINS_PLUGIN_DIR} && \
    mkdir -p ${JENKINS_WAR_DIR} && \
    chown -R jenkins:jenkins ${JENKINS_WAR_DIR}

#
# TODO Experimental: We need a bind mount that maps Docker host /var/run/docker.sock into the container, and
#      the mapping must have specific permissions. Not sure how to do that, so we "seed" the bind mount point
#      in the hope the permissions will propagate to the mapping. It seems to be working.
#

RUN touch /var/run/docker.sock && chgrp jenkins /var/run/docker.sock && chmod 0660 /var/run/docker.sock

#
# Run the Jenkins server as "jenkins"
#

USER jenkins:jenkins

#
# We override the entrypoint because we always want Jenkins to use its image-embedded plugins and WAR directories,
# irrespective of an externally set JENKINS_HOME. We need to be careful if we change the values of JENKINS_PLUGIN_DIR
# and JENKINS_WAR_DIR above and synchronize the values of --pluginroot and --webroot below. We cannot use
# environment variables because ENTRYPOINT exec form won't perform environment variable expansion.
#

ENTRYPOINT [ "/sbin/tini", "--", "/usr/local/bin/jenkins.sh", "--pluginroot=/var/jenkins/plugins", "--webroot=/var/jenkins/war" ]

#
# Install recommended plugins. The list was obtained by manually installing the recommended plugins on a test
# instance and reading the content of the plugin directory. If more plugins need to be installed, just add them
# to the list and build a new image.
#

RUN /usr/local/bin/install-plugins.sh \
 ace-editor \
 ant \
 antisamy-markup-formatter \
 apache-httpcomponents-client-4-api \
 authentication-tokens \
 bouncycastle-api \
 branch-api \
 build-timeout \
 cloudbees-folder \
 command-launcher \
 credentials-binding \
 credentials \
 display-url-api \
 docker-commons \
 docker-workflow \
 durable-task \
 email-ext \
 git-client \
 git-server \
 git \
 github-api \
 github-branch-source \
 github \
 gradle \
 handlebars \
 jackson2-api \
 jquery-detached \
 jsch \
 junit \
 ldap \
 mailer \
 mapdb-api \
 matrix-auth \
 matrix-project \
 momentjs \
 pam-auth \
 pipeline-build-step \
 pipeline-github-lib \
 pipeline-graph-analysis \
 pipeline-input-step \
 pipeline-milestone-step \
 pipeline-model-api \
 pipeline-model-declarative-agent \
 pipeline-model-definition \
 pipeline-model-extensions \
 pipeline-rest-api \
 pipeline-stage-step \
 pipeline-stage-tags-metadata \
 pipeline-stage-view \
 plain-credentials \
 resource-disposer \
 scm-api \
 script-security \
 ssh-credentials \
 ssh-slaves \
 structs \
 timestamper \
 token-macro \
 workflow-aggregator \
 workflow-api \
 workflow-basic-steps \
 workflow-cps-global-lib \
 workflow-cps \
 workflow-durable-task-step \
 workflow-job \
 workflow-multibranch \
 workflow-scm-step \
 workflow-step-api \
 workflow-support \
 ws-cleanup \
 # individually added
 docker-plugin


