#!/bin/sh

data_dir=/usr/local/data/zookeeper

install() {
    create_directories
    environmental_variables
}

create_directories() {
    mkdir -p $data_dir
}

environmental_variables() {
    if [ -z "`cat ~/.bashrc | grep "Zookeeper"`" ]; then
        cat <<EOF >> ~/.bashrc
# ----> Zookeeper
export ZOOKEEPER_DATA_PATH=$data_dir
# Zookeeper <----
EOF
    fi
}

install
