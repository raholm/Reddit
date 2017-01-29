#!/bin/sh

version="2.7.2"

home_dir="/usr/local/bin/hadoop"
data_dir="/usr/local/data/hadoop"
conf_dir="/usr/local/conf/hadoop"

install() {

    if [ -z "`hadoop version 2>&1 | grep $version`" ]; then
        echo "Incorrect Hadoop version found."
        echo "Installing Hadoop..."

        download
        setup

        echo "Installation complete"
    else
        echo "Hadoop already installed."
    fi
}

download() {
    tarfile="hadoop-$version.tar.gz"
    download_link="https://www-eu.apache.org/dist/hadoop/common/hadoop-$version/$tarfile"

    if [ ! -f "$tarfile" ]; then
        wget $download_link
    fi

    if [ -f "$tarfile" ]; then
        tar -xf "$tarfile"
        rm "$tarfile"

        if [ -d "hadoop" ]; then
            mv "hadoop" "hadoop_scpt"
        fi

        mv "hadoop-$version" "hadoop"
    fi
}

setup() {
    destination="/usr/local/bin"

    # if [ -d "$destination/hadoop"]; then
    #     mv "$destination/hadoop" "$destination/hadoop_scpt"
    # fi

    if [ -d "hadoop" ]; then
        echo "Hadoop directory exists."
        # mv "hadoop" "$destination"
    else
        echo "Hadoop directory does not exists."
    fi
}

directories() {
    mkdir -p $data_dir $conf_dir
}

environmental_variables() {
    cat <<EOF # >> ~/.bashrc
# ----> Hadoop
export HADOOP_HOME=$home_dir
export HADOOP_INSTALL=\$HADOOP_HOME
export HADOOP_DATA=$data_dir
export HADOOP_CONF_DIR=$conf_dir
export HADOOP_VERSION=$version
export HADOOP_MAPRED_HOME=\$HADOOP_HOME
export HADOOP_COMMON_HOME=\$HADOOP_HOME
export HADOOP_HDFS_HOME=\$HADOOP_HOME
export YARN_HOME=\$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=\$HADOOP_HOME/lib/native
export PATH=\$PATH:\$HADOOP_HOME/bin:\$HADOOP_HOME/sbin
# Hadoop <----
EOF
}

configuration() {
    local_configuration
    localhost_configuration
    cluster_configuration
}

local_configuration() {
    cat <<EOF # > $conf_dir/hadoop-local.xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration>
  <property>
	<name>fs.defaultFS</name>
	<value>file:///</value>
  </property>

  <property>
    <name>hadoop.tmp.dir</name>
    <value>$data_dir</value>
  </property>

  <property>
    <name>mapreduce.framework.name</name>
    <value>local</value>
  </property>
</configuration>
EOF
}

localhost_configuration() {
    cat <<EOF # > $conf_dir/hadoop-localhost.xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration>
  <property>
	<name>fs.defaultFS</name>
	<value>hdfs://localhost/</value>
  </property>

  <property>
    <name>hadoop.tmp.dir</name>
    <value>$data_dir</value>
  </property>

  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>

  <property>
    <name>yarn.resourcemanager.address</name>
    <value>localhost:8032</value>
  </property>
</configuration>
EOF
}

cluster_configuration() {
    cat <<EOF # > $conf_dir/hadoop-cluster.xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration>
  <property>
	<name>fs.defaultFS</name>
	<value>hdfs://namenode/</value>
  </property>

  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>

  <property>
    <name>yarn.resourcemanager.address</name>
    <value>resourcemanager:8032</value>
  </property>
</configuration>
EOF
}

# install
