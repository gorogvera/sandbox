
public class Parcel {

	private String parcelNumber;
	private String url;
	private int parcelId;
	
	Parcel(String pNumber) {
		parcelNumber = pNumber;
		url = null;
		parcelId = -1;
	}
	
	Parcel(String pNumber, String pUrl) {
		parcelNumber = pNumber;
		url = pUrl;
		parcelId = -1;
	}
	
	Parcel(String pNumber, int pId) {
		parcelNumber = pNumber;
		parcelId = pId;
	}
	
	Parcel(String pNumber, String pUrl, int pId) {
		parcelNumber = pNumber;
		url = pUrl;
		parcelId = pId;
	}
	
	public String getParcelNumber() {
		return parcelNumber;
	}
	
	public String getParcelUrl() {
		return url;
	}
	
	public int getParcelId() {
		return parcelId;
	}
	
}

