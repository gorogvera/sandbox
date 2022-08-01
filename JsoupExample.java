
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.*;


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
      
      String url = parcel.getParcelUrlFromElements(itemTitles);
      
      if (url != null) {
    	// show results in a new webpage
    	  Desktop d = Desktop.getDesktop();
    	  try {
			d.browse(new URI(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      } 
      
      
   }
}