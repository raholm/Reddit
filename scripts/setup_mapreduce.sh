#!/bin/sh

conf_dir=/usr/local/bin/conf/mapred

install() {
    create_directories
    setup_configuration
}

create_directories() {
    mkdir -p $conf_dir
}

setup_configuration() {
cat <<EOF > $conf_dir/mapred-local.xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration>
	<property>
		<name>mapreduce.framework.name</name>
		<value>yarn</value>
	</property>
</configuration>
EOF
}

install
