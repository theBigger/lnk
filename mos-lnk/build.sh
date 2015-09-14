#!/bin/sh
echo "clean Lnk Server Build..."
echo "clean build/lib/*.jar" && rm build/lib/*.jar
echo "clean lnk/lib/*.jar" && rm lnk/lib/*.jar
#mvn install -DskipTests
mvn clean install -Dmaven.test.skip=true
echo "Build Mos-Lnk Server Success ."
echo "Install Lnk Server... "
echo "copy build/lib/*.jar lnk/lib/" && cp build/lib/*.jar lnk/lib/
echo "copy etc/*.xml lnk/etc/" && cp etc/*.xml lnk/etc/
echo "copy etc/*.sh lnk/bin/" && cp etc/*.sh lnk/bin/
echo "move lnk/lib/mos-lnk-1.0.0-RELEASE.jar lnk/bin/" && mv lnk/lib/mos-lnk-1.0.0-RELEASE.jar lnk/bin/
echo "Install Lnk Server Success."
