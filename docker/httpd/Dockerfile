FROM httpd:2.4
RUN apt-get update; apt-get install -y curl iputils-ping dnsutils iproute2 net-tools traceroute iptables nfs-common wget vim procps;
COPY index.html /usr/local/apache2/htdocs
# we intercept httpd's startup command to execute custom logic before the server starts
COPY configure-httpd httpd-foreground /usr/local/bin/
COPY README.md /
RUN chmod a+x /usr/local/bin/configure-httpd /usr/local/bin/httpd-foreground
