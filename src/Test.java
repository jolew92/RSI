import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class Test 
{

	static int power(int numb, int pow)
	{
		return pow == 0 ? 1 : numb * power(numb, pow-1);
	}
	
	static void printPID_array(PID_individual[] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			System.out.println(arr[i]);
			//System.out.printf("%-12f, %-12f, %-12f \n", arr[i].getP(), arr[i].getI(),arr[i].getD());
		}
	}
	
	
	public static void main(String[] args) 
	{
		GeneticAlgorithm alg = new GeneticAlgorithm(5, 50, 3, 60, 2, 3);
		alg.run();
	}

}
