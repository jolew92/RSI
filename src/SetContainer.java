import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public  class SetContainer 
{
	public static Set<PID_individual> set =  Collections.synchronizedSet(new HashSet<PID_individual>());

	public static synchronized void add(PID_individual pid)
	{
//		System.out.println(set.size());
//		
		if(set.size() < ProjectProperties.POPULATION_SIZE){
			set.add(pid);
		}
	}
	

	
}
