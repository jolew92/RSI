import java.util.Random;


public class SelectionThread extends Thread
{
	private static Random random = new Random();
	private static PID_individual[] population;
	
	public SelectionThread(PID_individual[] population) 
	{
		SelectionThread.population = population;
	}
	
	@Override
	public void run()
	{
		while(Selection.positionCounter < ProjectProperties.POPULATION_SIZE)
		{
			int first_r = random.nextInt(population.length);
			int second_r = random.nextInt(population.length);
			
			PID_individual first_PID = population[first_r];
			PID_individual second_PID = population[second_r];	
			
			Selection.addSelected(first_PID, second_PID);
		}	
	}
}
