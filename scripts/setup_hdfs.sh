#!/bin/sh

data_dir=/usr/local/data/hdfs
conf_dir=/usr/local/conf/hdfs

install() {
    create_directories
    setup_configuration
}

create_directories() {
    mkdir -p $data_dir $conf_dir
}

setup_configuration() {
    setup_local_configuration
}

setup_local_configuration() {
    cat <<EOF > $conf_dir/hdfs-local.xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
	<property>
		<name>dfs.replication</name>
		<value>1</value>
	</property>
	<property>
		<name>dfs.datanode.data.dir</name>
		<value>file://$data_dir/datanode/</value>
	</property>
	<property>
		<name>dfs.namenode.name.dir</name>
		<value>file://$data_dir/namenode/</value>
	</property>
</configuration>
EOF
}

install
