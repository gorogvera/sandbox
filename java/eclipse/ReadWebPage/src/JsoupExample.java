
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.*;


// TODO run this with crontab
// 1. crontab -e
// 2. 0 23 * * * script
// script contains: java thisprogram.jar

public class JsoupExample {
	
   public static void main(String args[]) throws IOException {
	   
	   // 1. napi kifuggesztesek listaba
	   // 2. adatbazisbol kiolvasni mely hrsz-ek erdekesek szamunkra
	   // 3. megnezni van-e talalat, talalatokat listaba
	   // 4. talalatokhoz tartozo emai cimekre elkuldeni a kifuggesztes reszleteit
	   // 5. mindezt contab-bal naponta lefuttatni
	   
      final String page = "https://foldem.hu/termofold-hirdetmenyek/?type=purchase";
      
      Parcel parcel = new Parcel();
      parcel.setNumber("2201");
      
      
      //Connecting to the web page
      Connection conn = Jsoup.connect(page);
      
      //executing the get request
      Document doc = conn.get();
      
      //Retrieving the contents (body) of the web page
      //String result = doc.body().text();
      
      //System.out.println(result);
      
      Elements itemTitles = doc.select("div[class=itemTitle]");
      
      String url = parcel.getParcelUrlFromElements(itemTitles);
      
      if (url != null) {
    	// TODO send an email with the link
      } 
      
      
   }
}