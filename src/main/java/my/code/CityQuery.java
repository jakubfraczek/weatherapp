package my.code;

public class CityQuery {

	private final String city;
	private final String countryCode;

	public CityQuery(String city) {
		this.city = city;
		this.countryCode = null;
	}
	
	public CityQuery(String city, String cantyCode) {
		this.city = city;
		this.countryCode = cantyCode;
	}

	@Override
	public String toString() {
		StringBuilder builer = new StringBuilder(city);
		
		if (countryCode != null){
			builer.append(",").append(countryCode);
		}
		
		return builer.toString();
	}
	

	

}
