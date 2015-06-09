
public class ProjectProperties 
{
	public static int POPULATION_SIZE = 10000;
	public static int START_POPULATION_THREADS_NUMBER = 500;
	public static int SELECTION_THREADS_NUMBER = 500;
	public static int CROSSOVER_THREADS_NUMBER = 500;
	public static int CROSSOVER_PROBABILITY = 60;
	
	public ProjectProperties(int START_POPULATION_SIZE) {

		ProjectProperties.POPULATION_SIZE = START_POPULATION_SIZE;
	}
	
}
