import java.util.ArrayList;

public class ParcelChecker {

	private static ArrayList<Parcel> foundParcels = new ArrayList<Parcel>();
	
	public static void main(String[] args) {

		// 1. napi kifuggesztesek listaba
		PostedParcels.fillPostedParcels();
		
		// 2. adatbazisbol kiolvasni mely hrsz-ek erdekesek szamunkra
		ObservedParcels.fillObservedParcels();
		
		// 3. megnezni van-e talalat, talalatokat listaba
		getPostedAndObservedParcels();
		
		// 4. talalatokhoz tartozo emai cimekre elkuldeni a kifuggesztes reszleteit
		
		
		// 5. mindezt contab-bal naponta lefuttatni
		
	}
	
	private static void getPostedAndObservedParcels() {
		
		// TODO : ezt a keresest hatekonyabba tenni
		for (Parcel postedP : PostedParcels.postedParcels) {
			
			for (Parcel observedP : ObservedParcels.observedParcels) {
				
				if (postedP.getParcelNumber().equals(observedP.getParcelNumber())) {
					
					Parcel commonP = new Parcel(postedP.getParcelNumber(), postedP.getParcelUrl(), observedP.getParcelId());
					
					foundParcels.add(commonP);
					
					System.out.println("Found posted and observed parcel: " + commonP.getParcelId() + ", " + commonP.getParcelNumber());
				}
				
			}
			
		}
	}

}
