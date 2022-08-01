import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parcel {

	final static String PARCEL_NUMBER_PATTERN = "\\d+[/]?\\d+";

	private String number; // helyrajzi szam
	private Pattern pattern;
	
	Parcel() {
		pattern = Pattern.compile(PARCEL_NUMBER_PATTERN);
	}
	
	public void setNumber(String n) {
		// TODO add some checkings if it is a good format of parcel number
		number = n;
	}
	
	public String getNumber() {
		return number;
	}
	
	public ArrayList<String> getParcelNumbersFromString(String txt) {
		ArrayList<String> results = new ArrayList<String>();
		
		Matcher m = pattern.matcher(txt);
		
		while (m.find()) {
			results.add(m.group());
		}
		
		return results;
	}
	
}

