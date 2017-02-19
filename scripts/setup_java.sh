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

    set_environmental_variables
}

set_environmental_variables() {
    if [ -z "`cat ~/.bashrc | grep "Java"`" ]; then
        java="`which java`"
        chrlen=${#java_home}
        # Remove /bin/java
        java_home=${java:0:(chrlen - 9)}

        cat <<EOF >> ~/.bashrc
# ----> Java
export JAVA_HOME=$java_home
export PATH=\$JAVA_HOME/bin:$PATH
# Java <----
EOF
        source ~/.bashrc
    fi
}

install
