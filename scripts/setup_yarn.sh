#!/bin/sh

data_dir=/usr/local/data/yarn
conf_dir=/usr/local/conf/yarn

directories() {
    mkdir -p $data_dir $conf_dir
}

configuration() {

}

localhost_configuration() {
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
