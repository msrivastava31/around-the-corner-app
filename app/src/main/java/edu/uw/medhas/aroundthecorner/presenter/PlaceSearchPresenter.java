package edu.uw.medhas.aroundthecorner.presenter;

import com.google.android.gms.maps.model.LatLng;

import edu.uw.medhas.aroundthecorner.dto.PlaceList;
import edu.uw.medhas.aroundthecorner.model.Category;

/**
 * Created by medhas on 1/28/18.
 */

public interface PlaceSearchPresenter {
    void searchPlaces(LatLng pointOfInterest, Category category);

    void setMapPresenter(MapPresenter mapPresenter);

    void placeSearchCallback(PlaceList placeList);
}
