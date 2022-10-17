
public class Parcel {

	private String parcelNumber;
	private String url;
	private String locality;
	private int parcelId;
	
	Parcel(String pNumber, String locality) {
		parcelNumber = pNumber;
		url = null;
		parcelId = -1;
		this.locality = locality;
	}
	
	Parcel(String pNumber, String locality, String pUrl) {
		parcelNumber = pNumber;
		url = pUrl;
		parcelId = -1;
		this.locality = locality;
	}
	
	Parcel(String pNumber, String locality, int pId) {
		parcelNumber = pNumber;
		parcelId = pId;
		this.locality = locality;
	}
	
	Parcel(String pNumber, String locality, String pUrl, int pId) {
		parcelNumber = pNumber;
		url = pUrl;
		parcelId = pId;
		this.locality = locality;
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
	
	public String getLocality() {
		return locality;
	}
 	
	@Override
	public String toString() {
		return parcelId + " : " + parcelNumber + " , " + locality;
	}
}

