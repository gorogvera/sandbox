import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcelChecker {

	private static ArrayList<Parcel> foundParcels = new ArrayList<Parcel>();
	
	public static void main(String[] args) {

		// 0. do some database maintanance and clearing
		Maintenance.clearDatabase();
		
		// 1. napi kifuggesztesek listaba
		PostedParcels.fillPostedParcels();
		
		// 2. adatbazisbol kiolvasni mely hrsz-ek erdekesek szamunkra
		ObservedParcels.fillObservedParcels();
		
		// 3. megnezni van-e talalat, talalatokat listaba
		getPostedAndObservedParcels();
		
		// 4. talalatokhoz tartozo email cimekre elkuldeni a kifuggesztes reszleteit
		EmailSender.sendAll(foundParcels);		
	}
		
	private static void getPostedAndObservedParcels() {
		
		Map<String, List<Parcel>> postedMap = new HashMap<>();
		
		for (Parcel postedP : PostedParcels.postedParcels) {
			String pn = postedP.getParcelNumber();
			List<Parcel> lp = postedMap.get(pn);
			if (lp==null) {
				lp = new ArrayList<>();
			}
			lp.add(postedP);
			postedMap.put(pn, lp);
		}
		
		for (Parcel observedP : ObservedParcels.observedParcels) {
			String observedLocality = observedP.getLocality().toUpperCase();
			String parcelNr = observedP.getParcelNumber();
			List<Parcel> parcels = postedMap.get(parcelNr);
			if (parcels != null) {
				for (Parcel p : parcels) {
					String postedLocality = p.getLocality();
					Parcel commonP = null;
					if (postedLocality == null || postedLocality.length() == 0) {
						commonP = new Parcel(parcelNr, "???", p.getParcelUrl(), observedP.getParcelId());
					}
					else {
						postedLocality = postedLocality.toUpperCase();
						if (postedLocality.equals(observedLocality) || 
								postedLocality.contains(observedLocality)) {
							commonP = new Parcel(parcelNr, postedLocality, p.getParcelUrl(), observedP.getParcelId());
						}
					}
					
					if (commonP != null) {
						foundParcels.add(commonP);
						System.out.println("Found posted and observed parcel: " + commonP);
					}
				}
			}
		}
		
	}
 
}
