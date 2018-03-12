package edu.uw.medhas.aroundthecorner.listener.impl;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import edu.uw.medhas.aroundthecorner.model.PlaceDetail;
import edu.uw.medhas.aroundthecorner.presenter.MapPresenter;

/**
 * Created by medhas on 1/28/18.
 */

public class PlaceSelectionListenerImpl implements PlaceSelectionListener {
    private final Context mContext;
    private final MapPresenter mMapPresenter;

    public PlaceSelectionListenerImpl(Context context,
                                      MapPresenter mapPresenter) {
        mContext = context;
        mMapPresenter = mapPresenter;
    }

    @Override
    public void onPlaceSelected(Place place) {
        final PlaceDetail poiPlace = new PlaceDetail();
        poiPlace.setLatLng(place.getLatLng());
        poiPlace.setName(place.getName().toString());
        mMapPresenter.setPointOfInterest(poiPlace);
    }

    @Override
    public void onError(Status status) {
        Toast.makeText(
                mContext,
                "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT
        ).show();
    }
}
