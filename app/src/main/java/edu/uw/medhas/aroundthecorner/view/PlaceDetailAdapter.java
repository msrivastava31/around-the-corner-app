package edu.uw.medhas.aroundthecorner.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import edu.uw.medhas.aroundthecorner.model.PlaceDetail;

/**
 * Created by medhas on 2/4/18.
 */

public class PlaceDetailAdapter extends FragmentStatePagerAdapter {
    private final List<PlaceDetail> mPlaceDetails;

    public PlaceDetailAdapter(FragmentManager fragmentManager, List<PlaceDetail> placeDetails) {
        super(fragmentManager);
        mPlaceDetails = placeDetails;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceDetailFragment.newInstance(mPlaceDetails.get(position), mPlaceDetails.size());
    }

    @Override
    public int getCount() {
        return mPlaceDetails.size();
    }
}
