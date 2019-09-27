#
# Run it as follows:
#
#    docker run -d -e SUBJECT_NAME="something" novaordis/centos-loop
#
#
FROM centos:latest
RUN yum install -y iproute net-tools iptables
ADD ./loop.sh /root
CMD [ "/root/loop.sh" ]
