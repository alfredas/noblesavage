#!/bin/sh

mvn clean install
cd  noblesavage-webapp/
mvn gwt:run -Dgwt.extraJvmArgs=-Xmx1024m
