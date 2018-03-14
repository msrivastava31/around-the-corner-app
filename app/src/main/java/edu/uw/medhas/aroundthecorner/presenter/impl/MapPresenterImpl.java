package edu.uw.medhas.aroundthecorner.presenter.impl;

import android.util.DisplayMetrics;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import edu.uw.medhas.aroundthecorner.constants.AppConstants;
import edu.uw.medhas.aroundthecorner.model.Category;
import edu.uw.medhas.aroundthecorner.model.PlaceDetail;
import edu.uw.medhas.aroundthecorner.presenter.MapIconPresenter;
import edu.uw.medhas.aroundthecorner.presenter.MapPresenter;
import edu.uw.medhas.aroundthecorner.presenter.PlacePresenter;
import edu.uw.medhas.aroundthecorner.presenter.PlaceSearchPresenter;

/**
 * Created by medhas on 1/28/18.
 */

public class MapPresenterImpl implements MapPresenter {
    private GoogleMap mGoogleMap;
    private final MapIconPresenter mMapIconPresenter;
    private final PlacePresenter mPlacePresenter;
    private final PlaceSearchPresenter mPlaceSearchPresenter;
    private OnMapClickListener mOnMapClickListener;

    private final int mMapHeight;
    private final int mMapWidth;
    private final int mMapPadding;

    public MapPresenterImpl(GoogleMap googleMap,
                            MapIconPresenter mapIconPresenter,
                            PlacePresenter placePresenter,
                            PlaceSearchPresenter placeSearchPresenter,
                            DisplayMetrics displayMetrics) {
        mGoogleMap = googleMap;
        mMapIconPresenter = mapIconPresenter;
        mPlacePresenter = placePresenter;
        mPlaceSearchPresenter = placeSearchPresenter;
        mPlaceSearchPresenter.setMapPresenter(this);

        mMapHeight = displayMetrics.heightPixels - 600;
        mMapWidth = displayMetrics.widthPixels;
        mMapPadding = (int) (mMapHeight * 0.08);
    }

    @Override
    public void setCurrentLocation(LatLng currentLatLng, boolean clearPlaces) {
        if (clearPlaces) {
            clearMap();
        }

        mPlacePresenter.setCurrentLocation(currentLatLng);

        if (mPlacePresenter.getPlaces().isEmpty()) {
            adjustCameraZoom();
        }
    }

    private void clearMap() {
        mPlacePresenter.clearAllPlaces();
        mGoogleMap.clear();
    }

    @Override
    public void setPointOfInterest(PlaceDetail poiPlace) {
        if (poiPlace == null) {
            return;
        }

        clearMap();
        final Marker poiMarker = mGoogleMap.addMarker(
                mMapIconPresenter.getPointOfInterestIcon(poiPlace.getLatLng(), AppConstants.POI_TEXT,
                        poiPlace.getName())
        );

        mPlacePresenter.setPointOfInterest(poiMarker, poiPlace);
        adjustCameraZoom();
    }

    @Override
    public void setOnMapClickListener(OnMapClickListener onMapClickListener) {
        mOnMapClickListener = onMapClickListener;
    }

    @Override
    public void searchPlacesForCategory(Category category) {
        final PlaceDetail poiPlace = mPlacePresenter.getPointOfInterestPlaceDetail();
        clearMap();
        setPointOfInterest(poiPlace);

        if (mOnMapClickListener != null) {
            mOnMapClickListener.onMapClick(null);
        }

        mPlaceSearchPresenter.searchPlaces(mPlacePresenter.getPointOfInterest(), category);
    }

    @Override
    public void setPlaces(List<PlaceDetail> places) {
        for (PlaceDetail place : places) {
            final Marker placeMarker = mGoogleMap.addMarker(
                    mMapIconPresenter.getPlaceIcon(place.getLatLng(), String.valueOf(place.getPosition() + 1), place.getName())
            );
            mPlacePresenter.addPlace(placeMarker, place);
        }

        adjustCameraZoom();
    }

    private void zoomToLatLng(LatLng position, int zoomLevel) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel));
    }

    @Override
    public void adjustCameraZoom() {
        final List<PlaceDetail> places = mPlacePresenter.getPlaces();

        if (places.isEmpty()) {
            zoomToLatLng(mPlacePresenter.getPointOfInterest(), AppConstants.POI_ZOOM);
            return;
        }

        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (PlaceDetail place : places) {
            builder.include(place.getMarker().getPosition());
        }
        builder.include(mPlacePresenter.getPointOfInterest());

        final LatLngBounds bounds = builder.build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, mMapWidth, mMapHeight, mMapPadding));
    }

    @Override
    public void clickPlace(PlaceDetail placeDetail) {
        Marker placeMarker = null;

        for (PlaceDetail place : mPlacePresenter.getPlaces()) {
            placeMarker = place.getMarker();
            if (placeMarker != null && placeMarker.isInfoWindowShown()) {
                placeMarker.hideInfoWindow();
            }
        }

        placeMarker = placeDetail.getMarker();

        if (placeMarker != null) {
            placeMarker.showInfoWindow();
            zoomToLatLng(placeMarker.getPosition(), AppConstants.PLACE_ZOOM);
        }
    }

    @Override
    public void zoomOnStartup() {
        mGoogleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(AppConstants.DEFAULT_LATLNG, AppConstants.DEFAULT_ZOOM)
        );
    }
}
