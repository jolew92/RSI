
public class Test {
	
	static void printPID_array(PID_individual[] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			//System.out.println(arr[i]);
			//System.out.printf("%-12f, %-12f, %-12f \n", arr[i].getP(), arr[i].getI(),arr[i].getD());
		}
	}
	
	public static void main(String[] args) {

        GeneticAlgorithm ga = new GeneticAlgorithm(50, 50, 3, 60, 2, 3);
        PID_individual[] test = ga.run();

        PID_individual best = test[0];

        for(int i = 0; i < test.length;i++)
        {
            if(test[i].fintnessFunction() < best.fintnessFunction())
            {
                best = test[i];
            }
        }

        System.out.println(best == null);

	}
}
