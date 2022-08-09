import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

final public class PostedParcels {

	public static ArrayList<Parcel> postedParcels = new ArrayList<Parcel>();
	
	private final static String PARCEL_NUMBER_PATTERN = "\\d+[/]?\\d+";
	private static Pattern pattern = Pattern.compile(PARCEL_NUMBER_PATTERN);
	
    final static String page = "https://foldem.hu/termofold-hirdetmenyek/?type=purchase";
	
    
	private static ArrayList<String> getParcelNumbersFromString(String txt) {
		ArrayList<String> results = new ArrayList<String>();
		
		Matcher m = pattern.matcher(txt);
		
		while (m.find()) {
			results.add(m.group());
		}
		
		return results;
	}
	
	public static void fillPostedParcels() {
		  
	      //Connecting to the web page
	      Connection conn = Jsoup.connect(page);
	      
	      //executing the get request
	      try {
	    	  Document doc = conn.get();
	      
	    	  //Retrieving the contents (body) of the web page
	    	  //String result = doc.body().text();
	      
	    	  //System.out.println(result);
	      
	    	  Elements itemTitles = doc.select("div[class=itemTitle]");
	      
	    	  for (Element e : itemTitles) {
	    	  
	    		  ArrayList<String> numbers = getParcelNumbersFromString(e.text());
	    	  
	    		  //System.out.println("Line: " + e.text());
	    	  
	    		  for (String n : numbers) {
	    		  
	    			  String u = e.getElementsByTag("a").first().attr("href");
	    		  
	    			  Parcel p = new Parcel(n, u);
	    			  
	    			  System.out.println("Add parcel: " + n + ", with url: " + u);
	    			  postedParcels.add(p);
	    		  
	    		  }
	    	  
	    	  }
	      } catch (IOException ex) {
	    	  System.out.println(ex);
	      }
	}
	
}
