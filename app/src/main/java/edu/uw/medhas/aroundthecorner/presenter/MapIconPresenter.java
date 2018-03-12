package edu.uw.medhas.aroundthecorner.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by medhas on 1/28/18.
 */

public interface MapIconPresenter {
    MarkerOptions getPointOfInterestIcon(LatLng poiLatLong, String iconText, String title);

    MarkerOptions getPlaceIcon(LatLng placeLatLong, String iconText, String title);
}
