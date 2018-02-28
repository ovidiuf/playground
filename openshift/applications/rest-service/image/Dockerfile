FROM registry.access.redhat.com/jboss-webserver-3/webserver31-tomcat7-openshift

#
# Note: use local entitlements, they need to be copied in advance in ./entitlements
#
#COPY ./entitlements/redhat.repo /etc/yum.repos.d/redhat.repo
#COPY ./entitlements/ent-key.pem /etc/pki/entitlement/ent-key.pem
#COPY ./entitlements/ent.pem /etc/pki/entitlement/ent.pem
#COPY ./entitlements/RPM-GPG-KEY-redhat-release /etc/pki/rpm-gpg/RPM-GPG-KEY-redhat-release
#COPY ./entitlements/redhat-uep.pem /etc/rhsm/ca/redhat-uep.pem
#USER root
#RUN yum -y install xfsprogs && rm /etc/pki/entitlement/*.pem

#COPY ./artifacts/* /opt/dsmanager/lib/
COPY ./rest-service/target/rest-service.war /deployments/
COPY ./root/opt/live /opt/live
COPY ./root/opt/ready /opt/ready

#COPY ./cron-launcher /opt/cron-launcher
#ENTRYPOINT [ "/opt/cron-launcher" ]