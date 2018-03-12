package edu.uw.medhas.aroundthecorner.presenter.impl;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.medhas.aroundthecorner.model.PlaceDetail;
import edu.uw.medhas.aroundthecorner.presenter.PlacePresenter;

/**
 * Created by medhas on 1/28/18.
 */

public class PlacePresenterImpl implements PlacePresenter {
    private final Map<Marker, PlaceDetail> mPlaceDetails;
    private PlaceDetail mPoiPlaceDetail;
    private LatLng mCurrentLocation;

    public PlacePresenterImpl() {
        mPlaceDetails = new HashMap<>();
    }

    @Override
    public void setPointOfInterest(Marker marker, PlaceDetail poiPlaceDetail) {
        clearAllPlaces();
        poiPlaceDetail.setMarker(marker);
        mPoiPlaceDetail = poiPlaceDetail;
    }

    @Override
    public PlaceDetail getPointOfInterestPlaceDetail() {
        return mPoiPlaceDetail;
    }

    @Override
    public void setCurrentLocation(LatLng currentLatLng) {
        mCurrentLocation = currentLatLng;
    }

    @Override
    public List<PlaceDetail> getPlaces() {
        List<PlaceDetail> placeDetailList = new ArrayList<PlaceDetail>(mPlaceDetails.values());
        Collections.sort(placeDetailList);
        return Collections.unmodifiableList(placeDetailList);
    }

    @Override
    public void addPlace(Marker placeMarker, PlaceDetail placeDetail) {
        placeDetail.setMarker(placeMarker);
        mPlaceDetails.put(placeMarker, placeDetail);
    }

    @Override
    public PlaceDetail getPlaceByMarker(Marker placeMarker) {
        return mPlaceDetails.get(placeMarker);
    }

    @Override
    public LatLng getPointOfInterest() {
        return (mPoiPlaceDetail != null) ? mPoiPlaceDetail.getLatLng() : mCurrentLocation;
    }

    @Override
    public void clearPlaces() {
        mPlaceDetails.clear();
    }

    private void clearPoi() {
        mPoiPlaceDetail = null;
    }

    @Override
    public void clearAllPlaces() {
        clearPoi();
        clearPlaces();
    }
}
