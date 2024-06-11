FROM centos:centos7

# FIXME: update user to non-root user
# FIXME: use light weight base image such as alpine

ENV container=docker
ENV HOME /root
WORKDIR /root

ENV LANG en_US.utf8

RUN (cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == systemd-tmpfiles-setup.service ] || rm -f $i; done); \
rm -f /lib/systemd/system/multi-user.target.wants/*;\
rm -f /etc/systemd/system/*.wants/*;\
rm -f /lib/systemd/system/local-fs.target.wants/*; \
rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
rm -f /lib/systemd/system/basic.target.wants/*;\
rm -f /lib/systemd/system/anaconda.target.wants/*;

RUN yum install -y epel-release # for nginx
RUN yum install -y initscripts.x86_64

RUN yum install -y -t which.x86_64 gzip.x86_64 unzip.x86_64 zip.x86_64 curl.x86_64 \ 
openssh-server.x86_64 openssh-clients.x86_64 net-tools.x86_64 bind-utils.x86_64 \
nginx.x86_64 httpd.x86_64 initscripts.x86_64 glib.x86_64 gcc.x86_64 pcre.x86_64 \
spice-glib-devel.x86_64 cairo-devel libxml2-devel pango-devel pango libpng-devel \
freetype freetype-devel libart_lgpl-devel perl-ExtUtils-MakeMaker

RUN yum install -y -t java-1.8.0-openjdk.x86_64

# customize environment
ADD ./target/atwater-1.0.0-BUILD-SNAPSHOT.jar ./atwater-1.0.0-BUILD-SNAPSHOT.jar

VOLUME /run /tmp

ENTRYPOINT ["/usr/bin/java","-jar","./atwater-1.0.0-BUILD-SNAPSHOT.jar"]

