#!/bin/sh
thrift -gen java -o ../GroovyServer Bombah.thrift
thrift -gen js:jquery -o ../WebGL Bombah.thrift
thrift -gen js:node -o ../WebGL Bombah.thrift
