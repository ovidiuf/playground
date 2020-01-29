#
#
#
FROM httpd:2.4
RUN apt-get update; apt-get install -y curl iputils-ping dnsutils iproute2 net-tools traceroute iptables nfs-common; mkdir -p /test-directory/test-subdirectory-01; mkdir /test-directory/test-subdirectory-02; echo "test 01" > /test-directory/test-subdirectory-01/test-file-01.txt; echo "test 02" > /test-directory/test-subdirectory-02/test-file-02.txt;
