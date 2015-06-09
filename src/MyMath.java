
public class MyMath {
	
	public static int power(int number, int power)
	{
		return power == 0 ? 1 : number * power(number, power-1);
	}
}
