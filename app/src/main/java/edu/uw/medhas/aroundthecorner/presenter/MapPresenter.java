package edu.uw.medhas.aroundthecorner.presenter;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import edu.uw.medhas.aroundthecorner.model.Category;
import edu.uw.medhas.aroundthecorner.model.PlaceDetail;

/**
 * Created by medhas on 1/28/18.
 */

public interface MapPresenter {
    void setCurrentLocation(LatLng currentLatLng, boolean clearPlaces);

    void setPointOfInterest(PlaceDetail poiPlace);

    void setOnMapClickListener(OnMapClickListener onMapClickListener);

    void searchPlacesForCategory(Category category);

    void setPlaces(List<PlaceDetail> places);

    void adjustCameraZoom();

    void clickPlace(PlaceDetail placeDetail);

    void zoomOnStartup();
}
