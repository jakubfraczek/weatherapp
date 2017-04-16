package my.code;

public enum Countries {
    POLAND("PL");

    private String countryCode;

    Countries(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode(){
        return countryCode;
    }

}
