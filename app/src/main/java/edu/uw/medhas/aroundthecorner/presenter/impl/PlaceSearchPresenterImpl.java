package edu.uw.medhas.aroundthecorner.presenter.impl;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import edu.uw.medhas.aroundthecorner.client.AtcApiClient;
import edu.uw.medhas.aroundthecorner.dto.Place;
import edu.uw.medhas.aroundthecorner.dto.PlaceList;
import edu.uw.medhas.aroundthecorner.model.Category;
import edu.uw.medhas.aroundthecorner.model.PlaceDetail;
import edu.uw.medhas.aroundthecorner.presenter.MapPresenter;
import edu.uw.medhas.aroundthecorner.presenter.PlaceSearchPresenter;

/**
 * Created by medhas on 1/28/18.
 */

public class PlaceSearchPresenterImpl implements PlaceSearchPresenter {
    private final String mApiBaseUrl;
    private MapPresenter mMapPresenter;

    public PlaceSearchPresenterImpl(String apiBaseUrl) {
        mApiBaseUrl = apiBaseUrl;
    }

    @Override
    public void searchPlaces(LatLng pointOfInterest, Category category) {


        final String latLngStr = pointOfInterest.latitude + "," + pointOfInterest.longitude;

        final AsyncTask<String, Void, PlaceList> searchPlacesTask = new AtcApiClient(mApiBaseUrl, this);
        searchPlacesTask.execute(latLngStr, category.getApiKeyWord(), "10");
    }

    @Override
    public void setMapPresenter(MapPresenter mapPresenter) {
        mMapPresenter = mapPresenter;
    }

    @Override
    public void placeSearchCallback(PlaceList placeList) {
        final List<PlaceDetail> placeDetailList = new ArrayList<>();
        if (placeList == null) {
            return;
        }

        int i = 0;

        for (Place place : placeList.getPlaceList()) {
            final PlaceDetail placeDetail = newPlaceDetail(i++, place);
            placeDetailList.add(placeDetail);
        }

        mMapPresenter.setPlaces(placeDetailList);
    }


    private PlaceDetail newPlaceDetail(int position, Place place) {
        final PlaceDetail placeDetail = new PlaceDetail();
        placeDetail.setPosition(position);
        placeDetail.setName(place.getName());
        placeDetail.setAddress(place.getAddress());
        placeDetail.setRating(place.getRating());
        placeDetail.setDistance(place.getDistance()/1609.34); //Convert to miles

        LatLng latLng = new LatLng(place.getLocation().getLat(), place.getLocation().getLng());
        placeDetail.setLatLng(latLng);

        return placeDetail;
    }
}
