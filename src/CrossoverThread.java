import java.util.Random;


public class CrossoverThread extends Thread
{
	private static Random random = new Random();
	private static PID_individual[] population;
	
	public CrossoverThread(PID_individual[] population) 
	{
		CrossoverThread.population = population;
	}
	
	
	@Override
	public void run()
	{
		while(Crossover.positionCounter < ProjectProperties.POPULATION_SIZE)
		{
			int first_r = random.nextInt(population.length);
			int second_r = first_r;
			
			while(second_r != first_r)
			{
				second_r = random.nextInt(population.length);
			}

			PID_individual firstAdult = population[first_r];
			PID_individual secondAdult = population[second_r];	

            if(firstAdult != null)
            {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }

            if(secondAdult != null)
            {
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            }
			if(random.nextInt(101) > ProjectProperties.CROSSOVER_PROBABILITY)
			{
				Crossover.addPair(firstAdult, secondAdult);
			}
			else
			{
				if(random.nextInt(2) == 0) 
				{
					
					PID_individual first_child = new PID_individual(firstAdult.getP(), secondAdult.getI(), secondAdult.getD());
					PID_individual second_child = new PID_individual(secondAdult.getP(), firstAdult.getI(), firstAdult.getD());
					Crossover.addPair(first_child, second_child);
				}
				else
				{
					PID_individual first_child = new PID_individual(firstAdult.getP(), secondAdult.getI(), secondAdult.getD());
					PID_individual second_child = new PID_individual(secondAdult.getP(), firstAdult.getI(), firstAdult.getD());
					Crossover.addPair(first_child, second_child);
				}
			}
			
			
		}	
	}
}
