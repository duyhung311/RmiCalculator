#!/bin/sh

cd src &

javac -d CalculatorClient.java &

java cd-classpath CalculatorClient &