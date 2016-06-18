import java.net.* ;
import java.util.*;
import java.io.IOException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import java.net.MulticastSocket;


public class UdpSender extends Thread {

public final static int packetsize = 900;  

private MulticastSocket socket=null;
private InetAddress host;
private int port;
private AudioFormat audioFormat;
private TargetDataLine targetDataLine;
private byte tempBuffer[] = new byte[packetsize];
private static long sequenceNo=0;
private boolean stopCapture = false;


public UdpSender(InetAddress host,int port){
	this.host=host;
	this.port=port;
}

/***
*Send packet to the network
*
***/

private void packetSend(MulticastSocket socket){
        byte [] newByte;
        PacketData pd;
        stopCapture = false;

        try {
            int readCount;
            while (!stopCapture) {
                readCount = targetDataLine.read(tempBuffer, 0, tempBuffer.length);  //capture sound into tempBuffer

                if (readCount > 0) {
                    ++sequenceNo;
                    sequenceNo=sequenceNo%Integer.MAX_VALUE;
                    //creating packet with a sequence NUmber
                    pd=new PacketData(sequenceNo,tempBuffer);
                    newByte=PacketData.fullByteArray(pd);
                    
                    DatagramPacket packet =new DatagramPacket(newByte,newByte.length,host,port);  
                     
                     // Send the packet
                    socket.setTimeToLive(2);
                    socket.send( packet ) ;
                    socket.setLoopbackMode(true);
                }
            }
         
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(0);
        }
}

   
public void run(){
  	
	try{
     
	 socket = new MulticastSocket() ; 
         targetDataLine=new RecoderAndPlay(1).captureAudio();
         packetSend(socket);

	}catch(Exception e){
		System.out.println(e);
		e.printStackTrace();
	
	}
	finally{
	   socket.close() ;
	}
     
   }

}
