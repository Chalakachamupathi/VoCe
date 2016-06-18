import java.util.Date;

public class Statistics
{
	public long unorderedTotal = 0;
	public long lostTotal = 0;
	public int uOverflow = 0; // if long
	public int lOverflow = 0;//  overflows
	public long aprTime = 0; // time of conversation
	public long startTime;

	//constructor
	public Statistics()
	{
		startTime = new Date().getTime();
	}

	//update current object parameters
	public void update(ErrCorrection ec)
	{
		long lPkts = ec.getLoss();
		long uPkts = ec.getUnorderedPkts();

		/*unordered packets update*/
		if( (unorderedTotal+uPkts) < 0)//if long value overflows
		{
			uOverflow++;
			unorderedTotal = uPkts;
		}				
		else
			unorderedTotal+=uPkts;

		/*lost packets update*/
		if( (lostTotal+lPkts) < 0)//if long value overflows
		{
			lOverflow++;
			lostTotal = lPkts;
		}				
		else
			lostTotal+=lPkts;
		
		/*time update*/
		getApproxTime();
	}

	//get total loss of packets from start time
	public long getTotalLoss()
	{
		if(lOverflow == 0)
			return lostTotal;
		return -1; // more than 9223372036854775807

	}

	//get total count of unordered packets from start time
	public long getTotalUOrdered()
	{
		if(uOverflow == 0)
			return unorderedTotal;
		return -1; // more than 9223372036854775807
	}

	//get time since start time
	public long getApproxTime()
	{
		long timeNow = new Date().getTime();
		aprTime = (timeNow - startTime)/1000;

		return aprTime; // time in seconds
	}
}



