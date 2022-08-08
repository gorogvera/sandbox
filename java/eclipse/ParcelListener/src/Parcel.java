
public class Parcel {

	private String parcelNumber;
	private String url;
	
	Parcel(String pNumber) {
		parcelNumber = pNumber;
		url = null;
	}
	
	Parcel(String pNumber, String pUrl) {
		parcelNumber = pNumber;
		url = pUrl;
	}
	
	public String getParcelNumber() {
		return parcelNumber;
	}
	
}

