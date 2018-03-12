package edu.uw.medhas.aroundthecorner.presenter.impl;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import edu.uw.medhas.aroundthecorner.presenter.MapIconPresenter;

/**
 * Created by medhas on 1/28/18.
 */

public class MapIconPresenterImpl implements MapIconPresenter {
    private final IconGenerator mIconGenerator;

    public MapIconPresenterImpl(Context context) {
        mIconGenerator = new IconGenerator(context);
    }

    private MarkerOptions newMarkerOptions(LatLng latLong, String iconText, String title, int styleId) {
        mIconGenerator.setStyle(styleId);
        return new MarkerOptions()
                .position(latLong)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon(iconText)))
                .anchor(mIconGenerator.getAnchorU(), mIconGenerator.getAnchorV());
    }

    @Override
    public MarkerOptions getPointOfInterestIcon(LatLng poiLatLong, String iconText, String title) {
        return newMarkerOptions(poiLatLong, iconText, title, IconGenerator.STYLE_BLUE);
    }

    @Override
    public MarkerOptions getPlaceIcon(LatLng placeLatLong, String iconText, String title) {
        return newMarkerOptions(placeLatLong, iconText, title, IconGenerator.STYLE_ORANGE);
    }
}
