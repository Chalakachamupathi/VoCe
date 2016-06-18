#!/bin/bash
echo "Unit Tests"
javac -cp junit-4.10.jar PacketData.java PacketDataTest.java TestRunner.java 
java -cp junit-4.10.jar:. org.junit.runner.JUnitCore PacketDataTest

