package edu.uw.medhas.aroundthecorner.model;

/**
 * Created by medhas on 1/28/18.
 */

public enum Category {
    BANK("Banks/ATM", "bank", 0),
    BAR("Bars", "bar", 1),
    GAS_STATION("Gas Stations", "gas_station", 2),
    BUS_STATION("Bus Stations/Bus Stops", "bus_station", 3),
    HOSPITAL("Hospitals", "hospital", 4),
    HOTEL("Hotels", "hotel", 5),
    RESTAURANT("Restaurants", "restaurant", 6),
    GROCERY("Grocery/Supermarket", "grocery", 7),
    PHARMACY("Pharmacy", "pharmacy", 8),
    PARKING("Parking", "parking", 9),
    MOVIE("Movies", "movie", 10),
    CAFE("Cafes", "cafe", 11);

    private final String mDisplayText;
    private final String mApiKeyWord;
    private final int mPosition;

    private Category(String displayText, String apiKeyWord, int position) {
        mDisplayText = displayText;
        mApiKeyWord = apiKeyWord;
        mPosition = position;
    }

    public String getDisplayText() {
        return mDisplayText;
    }

    public String getApiKeyWord() {
        return mApiKeyWord;
    }

    public int getPosition() {
        return mPosition;
    }
}
