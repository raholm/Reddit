#!/bin/sh

version=1.2.4

home_dir=/usr/local/bin/hbase
data_dir=/usr/local/data/hbase
conf_dir=/usr/local/conf/hbase

install() {
    if [ -z "`hbase version 2>&1 | grep $version`" ]; then
        echo "Incorrect HBase version found."
        echo "Installing HBase..."
        download
        setup
        echo "Installation complete"
    else
        echo "HBase already installed."
    fi
}

download() {
    tarfile="hbase-$version.tar.gz"
    download_link="https://www-eu.apache.org/dist/hbase/$version/$tarfile"

    if [ ! -f "$tarfile" ]; then
        wget $download_link
    fi

    if [ -f "$tarfile" ]; then
        tar -xf "$tarfile"
        rm "$tarfile"

        if [ -d "hbase" ]; then
            mv "hbase" "hbase_scpt"
        fi

        mv "hbase-$version" "hbase"
    fi
}

setup() {
    desination="/user/local/bin"

    if [ -d "$destination/hbase"]; then
        mv "$destination/hbase" "$destination/hbase_scpt"
    fi

    if [ -d "hbase" ]; then
        echo "Hbase directory exists."
        mv "hbase" "$destination"
        create_directories
        set_environmental_variables
        setup_configuration
    else
        echo "Hbase directory does not exists."
    fi
}

create_directories() {
    mkdir -p $data_dir $conf_dir
}

set_environmental_variables() {
    if [ -z "`cat ~/.bashrc | grep "HBase"`"]; then
        cat <<EOF >> ~/.bashrc
# ----> HBase
export HBASE_HOME=$home_dir
export HBASE_DATA=$data_dir
export HBASE_CONF=$conf_dir
export HBASE_VERSION=$version

export PATH=\$PATH:\$HBASE_PATH/bin
# HBase <----
EOF
    fi
}

setup_configuration() {
    setup_local_configuration
}

setup_local_configuration() {
        cat <<EOF > $conf_dir/hdfs-local.xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration>
	<property>
		<name>hbase.rootdir</name>
		<value>$data_dir</value>
	</property>
	<property>
		<name>hbase.zookeeper.property.dataDir</name>
		<value>/usr/local/data/zookeeper</value>
	</property>
</configuration>
EOF
}
