import java.util.Random;
import java.util.Set;


public class PIDThreadGenerator extends Thread{

	private static Random r = new Random();
	private static DecimalFormatter formatter;
	
	public PIDThreadGenerator(DecimalFormatter formatter)
	{
		setPriority(10);
		PIDThreadGenerator.formatter = formatter;
	}

	public static final Set<PID_individual> set = SetContainer.set;
	
	@Override
	public void run()
	{
		while(set.size() < ProjectProperties.POPULATION_SIZE)
		{
			int multiplier = MyMath.power(10, r.nextInt(3));
		    
			SetContainer.add(new PID_individual(
					formatter.format(multiplier * r.nextFloat()), 
					formatter.format(multiplier * r.nextFloat()),
					formatter.format(multiplier * r.nextFloat())));
		}
	}
}
