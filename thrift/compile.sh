#!/bin/sh
thrift -gen java --out ../AIBombahServer/src/java Bombah.thrift
thrift -gen js:jquery -o ../AIBombahServer/web-app Bombah.thrift
thrift -gen js:node -o ../AIBombahServer/web-app Bombah.thrift
