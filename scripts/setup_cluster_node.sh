#!/bin/sh

setup_directories() {
    mkdir -p /usr/local/bin /usr/local/data /usr/local/conf
}

install_prerequisite() {
    apt-get update
    apt-get upgrade -y
    apt-get install ssh
    apt-get install rsync
}

install_hadoop_ecosystem() {
    bash ./setup_java.sh
    bash ./setup_hadoop.sh
    bash ./setup_hdfs.sh
    bash ./setup_mapreduce.sh
    bash ./setup_zookeeper.sh
    bash ./setup_yarn.sh
    bash ./setup_hbase.sh
}

setup_directories
install_prerequisite
install_hadoop_ecosystem
