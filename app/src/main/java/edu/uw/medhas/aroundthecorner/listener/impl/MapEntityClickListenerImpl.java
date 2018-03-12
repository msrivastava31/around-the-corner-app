package edu.uw.medhas.aroundthecorner.listener.impl;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import edu.uw.medhas.aroundthecorner.R;
import edu.uw.medhas.aroundthecorner.model.PlaceDetail;
import edu.uw.medhas.aroundthecorner.presenter.MapPresenter;
import edu.uw.medhas.aroundthecorner.presenter.PlacePresenter;
import edu.uw.medhas.aroundthecorner.view.PlaceDetailAdapter;

/**
 * Created by medhas on 2/1/18.
 */

public class MapEntityClickListenerImpl implements OnMarkerClickListener, OnMapClickListener {
    private BottomSheetBehavior mPlaceDetailsBottomSheet;
    private final View mplaceDetailsView;
    private final PlacePresenter mPlacePresenter;
    private final MapPresenter mMapPresenter;

    private final ViewPager mViewPager;
    private final FragmentManager mFragmentManager;

    public MapEntityClickListenerImpl(FragmentManager fragmentManager,
                                      View placeDetailsView,
                                      PlacePresenter placePresenter,
                                      MapPresenter mapPresenter) {
        mFragmentManager = fragmentManager;
        mplaceDetailsView = placeDetailsView;
        mViewPager = placeDetailsView.findViewById(R.id.place_detail_pager);
        mPlacePresenter = placePresenter;
        mMapPresenter = mapPresenter;

        mPlaceDetailsBottomSheet = BottomSheetBehavior.from(placeDetailsView);
        mPlaceDetailsBottomSheet.setHideable(true);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        PlaceDetail place = mPlacePresenter.getPlaceByMarker(marker);

        if (place == null) {
            return false;
        }

        mplaceDetailsView.setVisibility(View.VISIBLE);

        final List<PlaceDetail> placeDetails = mPlacePresenter.getPlaces();

        final PlaceDetailAdapter placeDetailAdapter = new PlaceDetailAdapter(mFragmentManager, placeDetails);
        final PlaceDetailPageChangeListener placeDetailPageChangeListener
                = new PlaceDetailPageChangeListener(mMapPresenter, placeDetails);

        mViewPager.setAdapter(placeDetailAdapter);
        mViewPager.setCurrentItem(place.getPosition());
        mViewPager.addOnPageChangeListener(placeDetailPageChangeListener);

        mPlaceDetailsBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

        mMapPresenter.clickPlace(place);

        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mplaceDetailsView.setVisibility(View.GONE);
        mMapPresenter.adjustCameraZoom();
    }
}
