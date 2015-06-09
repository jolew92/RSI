import java.util.Random;




/*
Klasa ktora operacje przewidziane przez algorytm genetyczny; w konstruktorze podajemy parametry naszego 
algorytmu, uruchamiamy go metoda run ; zmienne informujace o miejscach przed i po przecinku troche nie bardzo
pasuja do tej klasy, ale w sumie to nie mialem pomyslu gdzie je wcisnac

 * */
public class GeneticAlgorithm 
{
	private int number_of_generations;
	private int number_of_individuals_in_population;
	
	private int mutation_probability;
	private int crossover_probability;
	
	private int number_of_places_before_delimiter;
	private int number_of_places_after_delimiter;
	
	public GeneticAlgorithm(int number_of_generations, int number_of_individuals_in_population,
			int mutation_probability, int crossover_probability, int number_of_places_before_delimiter,
			int number_of_places_after_delimiter) 
	{
		this.number_of_generations = number_of_generations;
		this.number_of_individuals_in_population = number_of_individuals_in_population;
		this.mutation_probability = mutation_probability;
		this.crossover_probability = crossover_probability;
		this.number_of_places_before_delimiter = number_of_places_before_delimiter;
		this.number_of_places_after_delimiter = number_of_places_after_delimiter;
	}

	public void run()
	{
		PID_individual[] population = generateStartPopulation();
		
		int generation = 0;
		while(generation < number_of_generations)
		{
			population = selection(population);
			population = crossover(population);
			population = mutation(population);
			
			//Test.printPID_array(population);
			//System.out.println(generation + "  #########################################################");
			
			//---------------
			Selection.clearCounter();
			Crossover.clearCounter();
			//-------------
			generation++;
		}
	}
	
	private PID_individual[] generateStartPopulation()
	{
		DecimalFormatter decimal_formatter = new DecimalFormatter(".", 
				number_of_places_before_delimiter, number_of_places_after_delimiter);
		
		StartPopulationGenerator generator = new StartPopulationGenerator();
		PID_individual[] start_population = generator.generateStartPopulation
				(number_of_individuals_in_population, decimal_formatter);
		System.out.println(start_population.length);
		return start_population;
	}
	
//	private PID_individual[] selection(PID_individual[] population)
//	{
//		
//		/* --------------------- POPRAWNE ROZWIAZANIE BEZ WSPOLBIEZNOSCI  -------------- */
//		PID_individual[] selected = new PID_individual[population.length];
//		Random r = new Random();
//		PID_individual first_PID;
//		PID_individual second_PID;
//		int first_r;
//		int second_r;	
//		
//		for(int i=0; i<population.length; i++)
//		{
//			first_r = r.nextInt(population.length);
//			second_r = r.nextInt(population.length);
//			
//			first_PID = population[first_r];
//			second_PID = population[second_r];
//			
//			selected[i] = first_PID.fintnessFunction() >= second_PID.fintnessFunction() ? first_PID : second_PID;
//		}
//		
//		return selected;
//	}
	
	private PID_individual[] selection(PID_individual[] population)
	{
		Selection selection = new Selection();
		
		SelectionThread[] selectionThreads = new SelectionThread[ProjectProperties.SELECTION_THREADS_NUMBER];
		
		for(int i = 0; i < ProjectProperties.SELECTION_THREADS_NUMBER; i++)
		{
			selectionThreads[i] = new SelectionThread(population);
		}
		
		for(int i = 0; i < ProjectProperties.SELECTION_THREADS_NUMBER; i++)
		{
			selectionThreads[i].start();
		}

		System.out.println("SELECTED POP: " + Selection.population.length);
		return Selection.population;
	}
	
	private PID_individual[] crossover(PID_individual[] population)
	{
		Crossover crossover = new Crossover();
		CrossoverThread[] crossoverThreads = new CrossoverThread[ProjectProperties.CROSSOVER_THREADS_NUMBER];
		
		for(int i = 0; i < ProjectProperties.CROSSOVER_THREADS_NUMBER; i++)
		{
			crossoverThreads[i] = new CrossoverThread(population);
		}
		
		for(int i = 0; i < ProjectProperties.CROSSOVER_THREADS_NUMBER; i++)
		{
			crossoverThreads[i].start();
		}
		
		System.out.println("CROSSOV POP: " + Selection.population.length);
		return Crossover.population;
	}
	
	
//	private PID_individual[] crossover(PID_individual[] population)
//	{
//		int crossover_position;
//		Random r = new Random();
//		int first_r;
//		int second_r;	
//		PID_individual firstAdult;
//		PID_individual secondAdult;
//		PID_individual[] populationAfterCrossover = new PID_individual[population.length];
//		
//		for(int i=0; i<population.length/2; i++)
//		{
//			first_r = r.nextInt(population.length);
//			second_r = first_r;
//			while(first_r != second_r)
//			{
//				second_r = r.nextInt(population.length);
//			}
//			
//			firstAdult = population[first_r];
//			secondAdult = population[second_r];
//			//nie zachodzi krzyzowanie =
//			if(r.nextInt(101) > this.crossover_probability)
//			{
//				populationAfterCrossover[2*i] = firstAdult;
//				populationAfterCrossover[(2*i)+1] = secondAdult;
//			}
//			else //zachodzi krzyzowanie
//			{
//				//dzieci
//				if(r.nextInt(2) == 0) 
//				{
//					populationAfterCrossover[2*i] = new PID_individual(firstAdult.getP(), secondAdult.getI(), secondAdult.getD());
//					populationAfterCrossover[(2*i)+1] = new PID_individual(secondAdult.getP(), firstAdult.getI(), firstAdult.getD());
//				}
//				else 
//				{
//					populationAfterCrossover[(2*i)+1] = new PID_individual(firstAdult.getP(), secondAdult.getI(), secondAdult.getD());
//					populationAfterCrossover[2*i] = new PID_individual(secondAdult.getP(), firstAdult.getI(), firstAdult.getD());
//				}
//			}	
//		}
//		return populationAfterCrossover;
//	}
	
	private PID_individual[] mutation(PID_individual[] population)
	{
		Random r = new Random();
		
		for(int i=0; i<population.length; i++)
		{
			if(r.nextInt(101) <= this.mutation_probability)
			{
				DecimalFormatter decimal_formatter = new DecimalFormatter(".", 
						number_of_places_before_delimiter, number_of_places_after_delimiter);
				
				int multiplier = MyMath.power(10, r.nextInt(3));
				
				int element_nr = r.nextInt(3);
				if(element_nr == 0)
				{
					population[i].setP(decimal_formatter.format(multiplier * r.nextFloat()));
				}
				else if(element_nr == 1)
				{
					population[i].setI(decimal_formatter.format(multiplier * r.nextFloat()));
				}
				else // element_nr == 2
				{
					population[i].setD(decimal_formatter.format(multiplier * r.nextFloat()));
				}
			}
		}
		return population;
		
	}
	
	
}
