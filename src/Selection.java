import java.util.Random;


public class Selection 
{
	//private PID_individual[] selection(PID_individual[] population)
	
	public static final PID_individual[] population = new PID_individual[ProjectProperties.POPULATION_SIZE];
	public static int positionCounter = 0;
	
	public static synchronized void addSelected(PID_individual first_PID, PID_individual second_PID)
	{
		if(positionCounter < population.length)
		{
			population[positionCounter++] = first_PID.fintnessFunction() >= second_PID.fintnessFunction() ? 
					first_PID : second_PID;
		}	
	}
	
	public PID_individual[] getPopulation()
	{
		return population;
	}
	
	public static void clearCounter()
	{
		positionCounter = 0;
	}
}
