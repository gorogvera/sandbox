
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.*;
import org.jsoup.nodes.Element;


public class JsoupExample {
	
   public static void main(String args[]) throws IOException {
	   
      final String page = "https://foldem.hu/termofold-hirdetmenyek/?type=purchase";
      
      Parcel parcel = new Parcel();
      parcel.setNumber("1907");
      
      
      //Connecting to the web page
      Connection conn = Jsoup.connect(page);
      
      //executing the get request
      Document doc = conn.get();
      
      //Retrieving the contents (body) of the web page
      //String result = doc.body().text();
      
      //System.out.println(result);
      
      Elements itemTitles = doc.select("div[class=itemTitle]");
      
      for (Element e : itemTitles) {
 
    	  ArrayList<String> numbers = parcel.getParcelNumbersFromString(e.text());
    	  
    	  //System.out.println("Line: " + e.text());
    	  
    	  for (String n : numbers) {
    		  //System.out.println("Parcel number: {" + n + "}, {" + parcel.getNumber() + "}");
    		  
    		  if (n.equals(parcel.getNumber())) {
    			  System.out.println("Found parcel number: " + n);
    		  }
    	  }
    	  
      }
      
      // TODO get link of this hrsz
      
      // TODO show results in a new webpage
      
   }
}