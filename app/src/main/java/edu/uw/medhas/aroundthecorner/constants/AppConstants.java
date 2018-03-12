package edu.uw.medhas.aroundthecorner.constants;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by medhas on 1/28/18.
 */

public class AppConstants {
    public static final String POI_TEXT = "POI";
    public static final int POI_ZOOM = 18;
    public static final int PLACE_ZOOM = 15;

    public static final LatLng DEFAULT_LATLNG = new LatLng(47.609937, -122.340777);
    public static final int DEFAULT_ZOOM = 12;

    public static final int LOCATION_UPDATE_INTERVAL = 5000;
    public static final int LOCATION_UPDATE_DISTANCE = 150;

    // Only public static variables allowed
    private AppConstants() {}
}
