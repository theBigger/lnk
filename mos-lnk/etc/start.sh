#!/bin/sh
java -Dsun.net.inetaddr.ttl=60 -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -jar mos-lnk-1.0.0-RELEASE.jar
