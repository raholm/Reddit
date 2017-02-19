#!/bin/sh

install() {
    version="1.8"

    if [ -z "`java -version 2>&1 | grep $version`" ]; then
        echo "Incorrect Java version found."
        echo "Installing Java..."
        add-apt-repository ppa:webupd8team/java
        apt-get update
        apt-get install oracle-java8-installer
        apt-get install oracle-java8-set-default
        echo "Installation complete."
    else
        echo "Java already installed."
    fi
}

install
