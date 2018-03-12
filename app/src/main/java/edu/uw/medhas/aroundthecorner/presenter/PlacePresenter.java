package edu.uw.medhas.aroundthecorner.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import edu.uw.medhas.aroundthecorner.model.PlaceDetail;

/**
 * Created by medhas on 1/28/18.
 */

public interface PlacePresenter {
    void setPointOfInterest(Marker marker, PlaceDetail poiPlaceDetail);

    PlaceDetail getPointOfInterestPlaceDetail();

    void setCurrentLocation(LatLng currentLatLng);

    List<PlaceDetail> getPlaces();

    void addPlace(Marker placeMarker, PlaceDetail placeDetail);

    PlaceDetail getPlaceByMarker(Marker placeMarker);

    LatLng getPointOfInterest();

    void clearPlaces();

    void clearAllPlaces();
}
