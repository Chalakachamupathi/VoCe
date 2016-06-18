import java.util.*;

public class ErrCorrection
{ 
	public Map<Integer, ArrayList<Long>> details;//map of Inetaddresses and seqNo list
	public boolean enable = true; // can call getUnorderedPkts() or not
	public long unorderedCount = 0; 

	//constructor
	public ErrCorrection()
	{
		this.details = new HashMap<Integer, ArrayList<Long> >();
	}

	//add packet seq number to the list
	public void addPckt(long seq, int adr)
	{
		ArrayList<Long> list;
		if(details.containsKey(adr))// existing user's packets
			this.details.get(adr).add(seq);
		else // new user
		{
			list = new ArrayList<Long>();
			list.add(seq);
			this.details.put(adr, list);
		}
	}

	//calculate the current loss
	public long getLoss()
	{
		getUnorderedPkts(); 
		enable = false;			

		int size;
		long last, first, loss, totalLoss = 0;		

		/*go through every value in details map*/
		Set <Map.Entry<Integer, ArrayList<Long>>> set = details.entrySet(); 
      		Iterator <Map.Entry<Integer, ArrayList<Long>>> iterator = set.iterator();
		
      		while(iterator.hasNext()) 
		{
         		Map.Entry<Integer, ArrayList<Long>> entry = iterator.next();
         		
         		ArrayList<Long> list = entry.getValue();
			Collections.sort(list);//sort the list
			size = list.size();
			
			if(size == 0)
				continue;
			last = list.get(size-1);
			first = list.get(0);
			
			if( (last - first) > Long.MAX_VALUE/2)//end of seqNo counting
				continue; 
		
			loss = last - first - size + 1;	
			if(loss>0)
				totalLoss+=loss;
      		}		

		
		return totalLoss;
	}

	//calculate current count of unordered packets
	public long getUnorderedPkts()
	{
		if(!enable)// if getLoss() is called, can't call this method
			return unorderedCount;
		
		long unordered = 0;
		
		/*go through every element in the details map*/
		Set <Map.Entry<Integer, ArrayList<Long>>> set = details.entrySet();
      		Iterator <Map.Entry<Integer, ArrayList<Long>>> iterator = set.iterator();
      		while(iterator.hasNext()) 
		{
         		Map.Entry<Integer, ArrayList<Long>> entry = iterator.next();
         		
         		ArrayList<Long> list = entry.getValue();

			for(int i = 0; i<list.size()-1; i++)
			{
				if(list.get(i) > list.get(i+1) || list.get(0) > list.get(i))
					unordered++;
			}	
      		}		

		unorderedCount+=unordered;

		return unordered;
	}

	//total number of packets expected
	public int expectedPkts()
	{
		int size = 0;
		Set <Map.Entry<Integer, ArrayList<Long>>> set = details.entrySet();
      		Iterator <Map.Entry<Integer, ArrayList<Long>>> iterator = set.iterator();

      		while(iterator.hasNext()) 
		{
         		Map.Entry<Integer, ArrayList<Long>> entry = iterator.next();
         		
         		ArrayList<Long> list = entry.getValue();
			size+=list.size();
      		}		

		return size;
	}

	

}
