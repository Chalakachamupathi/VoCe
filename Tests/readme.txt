To run the unit tests in linux execute the following
  chmod +x run.sh
  ./run.sh 

OR 

To compile: 
javac -cp junit-4.10.jar PacketData.java PacketDataTest.java TestRunner.java 

To run:
java -cp junit-4.10.jar:. org.junit.runner.JUnitCore PacketDataTest

