#!/bin/sh

version=1.2.4

home_dir=/usr/local/bin/hbase
data_dir=/usr/local/data/hbase
conf_dir=/usr/local/conf/hbase

install() {
    if [ -z "`hbase version 2>&1 | grep $version`" ]; then
        echo "Incorrect HBase version found."
        echo "Installing HBase..."

        echo "Installation complete"
    else
        echo "HBase already installed."
    fi
}

create_directories() {
    mkdir -p $data_dir $conf_dir
}

environmental_variables() {
    cat <<EOF
# ----> HBase
export HBASE_HOME=$home_dir
export HBASE_DATA=$data_dir
export HBASE_CONF=$conf_dir
export HBASE_VERSION=$version

export PATH=\$PATH:\$HBASE_PATH/bin
# HBase <----
EOF
}
