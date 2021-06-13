#
# Run it as follows:
#
#    docker run -d -e SUBJECT_NAME="something" novaordis/centos-loop
#
#
FROM centos:latest
ADD ./loop.sh /root
RUN yum install -y iproute net-tools iptables && \
 chmod +x /root/loop.sh
ENTRYPOINT [ "/root/loop.sh" ]
