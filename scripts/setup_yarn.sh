#!/bin/sh

data_dir=/usr/local/data/yarn
conf_dir=/usr/local/conf/yarn

install() {
    create_directories
    setup_configuration
}

create_directories() {
    mkdir -p $data_dir $conf_dir
}

setup_configuration() {
    setup_localhost_configuration
}

setup_localhost_configuration() {
    cat <<EOF # > $conf_dir/yarn-local.xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
	<property>
		<name>yarn.resourcemanager.hostname</name>
		<value>localhost</value>
	</property>
	<property>
		<name>yarn.nodemanager.aux-services</name>
		<value>mapreduce_shuffle</value>
	</property>
</configuration>
EOF
}

install
