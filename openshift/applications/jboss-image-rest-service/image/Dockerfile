FROM registry.access.redhat.com/jboss-eap-6/eap64-openshift

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

#
# Optional startup wrapper, in case we need to experiment with it. If the subsequent
# COPY and ENTRYPOINT are commented out, the image will use its default entrypoint.
#
#COPY ./root/opt/startup-wrapper /opt/startup-wrapper
#COPY ./root/opt/second-level-script /opt/second-level-script
#ENTRYPOINT /opt/startup-wrapper
