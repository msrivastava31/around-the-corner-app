package edu.uw.medhas.aroundthecorner.listener.impl;

import android.support.v4.view.ViewPager;

import java.util.List;

import edu.uw.medhas.aroundthecorner.model.PlaceDetail;
import edu.uw.medhas.aroundthecorner.presenter.MapPresenter;

/**
 * Created by medhas on 2/5/18.
 */

public class PlaceDetailPageChangeListener implements ViewPager.OnPageChangeListener {
    private final MapPresenter mMapPresenter;
    private final List<PlaceDetail> mPlaceDetails;

    public PlaceDetailPageChangeListener(MapPresenter mapPresenter, List<PlaceDetail> placeDetails) {
        mMapPresenter = mapPresenter;
        mPlaceDetails = placeDetails;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mMapPresenter.clickPlace(mPlaceDetails.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
