 import java.net.* ;

public class Control{
	

	public static void main(String args[]){
		
		 InetAddress host;
		 int port;
	 /***
	 *Taking user input 
	 *IP address and the port number from command line
	 ***/

		if( args.length != 1 ){
	      
	         System.out.println( "usage: java Control <Multicast IP>" ) ;
	         return ;
	    }else{
	      		//creating two thread pass the input values
		      try{
			      	 host = InetAddress.getByName( args[0] ) ;
					 port = Integer.parseInt( "7000") ;
					 
					 UdpReciver ur = new UdpReciver(host,port);

					 ur.start();

					 UdpSender  us = new UdpSender(host,port);

					 us.start();

				}catch(Exception e){

					 System.out.println(e);
					 e.printStackTrace();
				}
			
		}

	}

}