import java.nio.ByteBuffer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.TestCase;


public class PacketDataTest extends TestCase{
    
    /**
     * Test of fullByteArray method, of class PacketData.
     */
    @Test
    public void testFullByteArray() {
	PacketData instance = null, rebuild = null;

        byte [] tem="voice".getBytes(), result = null;

        System.out.println("fullByteArray test:");
		instance = new PacketData(0l,tem);
		try
		{
			result = PacketData.fullByteArray(instance);//method output
			rebuild = new PacketData(result);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
        assertEquals(instance, rebuild);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getSequenceNo method, of class PacketData.
     */
    @Test
    public void testGetSequenceNo() {
        System.out.println("getSequenceNo test:");
        PacketData instance = new PacketData(0l,"voice".getBytes());
        long expResult = 0l;
        long result = instance.getSequenceNo();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getVoice method, of class PacketData.
     */
    @Test
    public void testGetVoice() {
        System.out.println("getVoice test:");
        PacketData instance = new PacketData(0l,"voice".getBytes());
        byte[] expResult = "voice".getBytes();
        byte[] result = instance.getVoice();
        assertArrayEquals(expResult, result);
        
    }

    /**
     * Test of readObject() method of class PacketData
     */
  
    @Test
    public void testReadObject()
    {
		PacketData initial = new PacketData(1l, "voice".getBytes());	
		PacketData result = null;
		try{
			byte[] arr = PacketData.fullByteArray(initial);	
			result = PacketData.readobject(arr);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		assertEquals(initial, result);
    }

}
