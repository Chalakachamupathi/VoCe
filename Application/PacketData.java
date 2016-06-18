import java.io.*;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.nio.ByteBuffer;

public class PacketData implements Serializable{

	
	private static final long serialVersionUID = -6470090944414208496L;
	private byte [] buf;
	private long sequenceNo;
	private int id;

	/***
	*This constructor take sequenceNo and voice byte array make a object
	*
	***/
	public PacketData(long sequenceNo,byte [] voice){
		buf = voice;		
		this.sequenceNo = sequenceNo;
		this.id=ByteBuffer.wrap(getLocalAddress().getAddress()).getInt();
		
	}
	/***
	*This constructor take a byte array of packetData object and make a packetData object
	*
	***/

	public PacketData(byte [] data) throws IOException, ClassNotFoundException {
		
              PacketData   tem = PacketData.readobject(data);
              this.sequenceNo  = tem.sequenceNo;
              this.buf = tem.buf;
              this.id  = tem.id;     

	}
	/***
	*This method returns a packetData objects's byte array
	*
	***/
     
        
         public  static PacketData readobject(byte [] data) throws IOException{

         	ObjectInputStream  o = null;

         	try{
             
                	ByteArrayInputStream b = new ByteArrayInputStream(data);
                					     o = new ObjectInputStream(b);

                    return (PacketData)o.readObject();
			             
                
            }catch(Exception e){

            	e.printStackTrace();
            	return null;
            }finally{
            	o.close();
            }

          
         }

		public static  byte[] fullByteArray(PacketData pa) throws IOException  {
			ObjectOutputStream  oos = null;
              try{  
               
                	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                					      oos    = new ObjectOutputStream(buffer);
                	oos.flush();
                    oos.writeObject(pa);

                    return buffer.toByteArray();
                
              }catch(Exception e){
              	e.printStackTrace();
              	return null;
              }finally{
              	oos.close();
              }
		}

	/***
	*This method returns a SequenceNumber of packetData object
	*
	***/

	public long getSequenceNo(){
		return this.sequenceNo;
	}

	/***
	*This method returns a voice part of the packetData object as a byte array
	*
	***/
	public byte[] getVoice(){
            return buf;
	}

	public InetAddress getLocalAddress() {

		try{
		    Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
		    while( ifaces.hasMoreElements() ){
		      	NetworkInterface iface = ifaces.nextElement();
		     	Enumeration<InetAddress> addresses = iface.getInetAddresses();

			    while( addresses.hasMoreElements() ){

			        InetAddress addr = addresses.nextElement();
			        if( addr instanceof Inet4Address && !addr.isLoopbackAddress() ){
			          return addr;
			        }
		      	}
		    }

		    return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public int getId()
	{
		return this.id;
	}





	
}
