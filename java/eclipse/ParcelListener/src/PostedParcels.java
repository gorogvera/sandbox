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
	
	private final static String PARCEL_NUMBER_PATTERN = "\\d+(\\/\\d+)?";
	private static Pattern pattern = Pattern.compile(PARCEL_NUMBER_PATTERN);
	
	// TODO maybe this locality regexp can be better
	private final static String LOCALITY_PATTERN = "- (.*?) hrsz";
	private static Pattern locality_pattern = Pattern.compile(LOCALITY_PATTERN);
	
    final static String page = "https://foldem.hu/termofold-hirdetmenyek/?type=purchase";
	
    
	
	private static ArrayList<Parcel> getParcelsFromString(String txt) {
		var results = new ArrayList<Parcel>();
		
		Matcher m = pattern.matcher(txt);
		Matcher l = locality_pattern.matcher(txt);
		
		String loc = new String();
		
		while (m.find()) {
			if (l.find()) loc = l.group(1);
			Parcel p = new Parcel(m.group(),loc);
			results.add(p);
			System.out.println("getParcelsFromString: " + p);
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
	    	  
	    		  ArrayList<Parcel> parcels = getParcelsFromString(e.text());
	    	  
	    		  //System.out.println("Line: " + e.text());
	    	  
	    		  for (Parcel p : parcels) {
	    		  
	    			  String u = e.getElementsByTag("a").first().attr("href");
	    		  
	    			  p.setUrl(u);
	    			  
	    			  System.out.println("Add parcel: " + p + ", with url: " + u);
	    			  postedParcels.add(p);
	    		  
	    		  }
	    	  
	    	  }
	      } catch (IOException ex) {
	    	  System.out.println(ex);
	      }
	}
	
}
