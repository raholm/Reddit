#!/bin/sh

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

install_hadoop_ecosystem
