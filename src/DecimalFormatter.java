import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;



/*
 Generalnie klasa zajmuje się formatowaniem liczb, w naszym przypadku chodzi o to, aby okreslic
 liczbe miejsc przed i po przecinku we float'cie. W zasadzie mozna juz jej nie ruszac
 * */
public class DecimalFormatter 
{
	private DecimalFormatSymbols other_symbols = new DecimalFormatSymbols();
	private DecimalFormat decimal_format;
	
	public DecimalFormatter(String decimal_separator, int places_before_delimiter, int places_after_delimiter)
	{
		this.other_symbols.setDecimalSeparator(decimal_separator.charAt(0));
		String decimal_format = constructDecimalFormat(decimal_separator,places_before_delimiter,places_after_delimiter);	
		
		System.out.println(decimal_format);
		this.decimal_format = new DecimalFormat(decimal_format, other_symbols);
	}
	
	public float format(Float f)
	{
		return new Float(decimal_format.format(f));
	}
	
	private String constructDecimalFormat(String decimal_separator, int places_before_delimiter, int places_after_delimiter)
	{
		String decimal_format = multiplyChar('#', places_before_delimiter);
		decimal_format = decimal_format.concat(decimal_separator);
		decimal_format = decimal_format.concat(multiplyChar('#', places_after_delimiter));
		
		return decimal_format;
	}
	
	private static String multiplyChar(char c, int multiplier)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < multiplier; i++)
		{
			sb.append(c);
		}
		return sb.toString();
	}
	
}
