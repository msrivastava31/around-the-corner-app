package edu.uw.medhas.aroundthecorner.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uw.medhas.aroundthecorner.R;
import edu.uw.medhas.aroundthecorner.model.PlaceDetail;

/**
 * Created by medhas on 2/4/18.
 */

public class PlaceDetailFragment extends Fragment {
    private PlaceDetail mPlaceDetail;
    private int mTotalPositions;

    public static PlaceDetailFragment newInstance(PlaceDetail placeDetail, int totalPositions) {
        final PlaceDetailFragment placeDetailFragment = new PlaceDetailFragment();
        placeDetailFragment.setPlaceDetail(placeDetail);
        placeDetailFragment.setTotalPositions(totalPositions);
        return placeDetailFragment;
    }

    private void setPlaceDetail(PlaceDetail placeDetail) {
        mPlaceDetail = placeDetail;
    }

    private void setTotalPositions(int totalPositions) {
        mTotalPositions = totalPositions;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            final Bundle bundle = savedInstanceState.getBundle("place_bundle");
            mPlaceDetail = (PlaceDetail) bundle.getParcelable("place");
            mTotalPositions = bundle.getInt("total_positions");
        }

        if (mPlaceDetail == null) {
            return null;
        }

        View placeDetailsView = inflater.inflate(R.layout.place_detail_item, container, false);

        ((TextView) placeDetailsView.findViewById(R.id.place_name)).setText(mPlaceDetail.getName());
        ((TextView) placeDetailsView.findViewById(R.id.place_address)).setText(mPlaceDetail.getAddress());
        ((TextView) placeDetailsView.findViewById(R.id.place_rating)).setText(mPlaceDetail.getRating());
        ((TextView) placeDetailsView.findViewById(R.id.place_distance)).setText(mPlaceDetail.getDistance() + "m");

        final String positionStr = Integer.valueOf(mPlaceDetail.getPosition() + 1).toString()
                + " out of " + mTotalPositions;
        ((TextView) placeDetailsView.findViewById(R.id.place_position)).setText(positionStr);

        return placeDetailsView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable("place", mPlaceDetail);
        bundle.putInt("total_positions", mTotalPositions);

        outState.putBundle("place_bundle", bundle);
        super.onSaveInstanceState(outState);
    }
}
