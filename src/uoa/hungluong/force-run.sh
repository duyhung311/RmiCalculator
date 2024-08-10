#!/bin/sh

cd src
#turn off all rmiregistry
ps -ef | grep rmiregistry | awk '{ print $2 }'  | xargs kill -9

rmiregistry


# compiling src
javac -d . Calculator.java CalculatorServer.java CalculatorClient.java
#run server
java -classpath . CalculatorServer &
#run client
java -classpath . CalculatorClient &





