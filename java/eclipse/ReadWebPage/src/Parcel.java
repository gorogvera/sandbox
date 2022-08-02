import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	
	private ArrayList<String> getParcelNumbersFromString(String txt) {
		ArrayList<String> results = new ArrayList<String>();
		
		Matcher m = pattern.matcher(txt);
		
		while (m.find()) {
			results.add(m.group());
		}
		
		return results;
	}
	
	public String getParcelUrlFromElements(Elements itemTitles) {
		
		String url = new String();
		
	      for (Element e : itemTitles) {
	    	  
	    	  ArrayList<String> numbers = getParcelNumbersFromString(e.text());
	    	  
	    	  System.out.println("Line: " + e.text());
	    	  
	    	  for (String n : numbers) {
	    		  //System.out.println("Parcel number: {" + n + "}, {" + parcel.getNumber() + "}");
	    		  
	    		  if (n.equals(getNumber())) {
	    			  System.out.println("Found parcel number: " + n);
	    			  
	    			  url = e.getElementsByTag("a").first().attr("href");
	    			  
	    			  // get the link
	    			  System.out.println(url);
	    			  
	    			  return url;
	    		  }
	    	  }
	    	  
	      }
	      
	      return null;
	}
	
}
