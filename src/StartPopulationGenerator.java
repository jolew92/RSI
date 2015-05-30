import java.util.HashSet;
import java.util.Random;


/*
 Klasa odpowiedzialna w zasadzie tylko za wygenerowanie populacji startowej, robi to z uzyciem HashSeta
 pamietajcie ze HashSet nie jest synchronizowany
 * */
public class StartPopulationGenerator 
{
	public StartPopulationGenerator(){}

	public PID_individual[]  generateStartPopulation(int start_population_size, DecimalFormatter decimal_formatter)
	{
		HashSet<PID_individual> start_population_temp_set = new HashSet<PID_individual>();
		Random random = new Random();
	
		while(start_population_temp_set.size() < start_population_size)
		{
			/* we will multiply by 1 or 10 or 100 */
			int multiplier = power(10, random.nextInt(3));
			
			start_population_temp_set.add(new PID_individual(
					decimal_formatter.format(multiplier * random.nextFloat()), 
					decimal_formatter.format(multiplier * random.nextFloat()),
					decimal_formatter.format(multiplier * random.nextFloat())
					)
			);
		}
		
		PID_individual[] start_population = start_population_temp_set.toArray(new PID_individual[start_population_temp_set.size()]);
		return start_population;
	}
	
	/* returns power of number greater than 0, f.e. power(2,3) == 8 */
	static int power(int number, int power)
	{
		return power == 0 ? 1 : number * power(number, power-1);
	}
	
	
}
