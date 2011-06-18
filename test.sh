#!/bin/bash
# Script to test the Bitly java API.
#
# USAGE:
#    ./test.sh
#

CLASSPATH=Bitly-1.0.1.jar
for clas in `ls lib/*.jar`; do
    CLASSPATH=${CLASSPATH}:$clas
done
javac -classpath ${CLASSPATH} BitlyTest.java

echo -n "Bitly Login: "
read login
echo -n "Bitly ApiKey: "
read apiKey
echo
java -classpath ${CLASSPATH}:. BitlyTest $login $apiKey

rm BitlyTest.class

