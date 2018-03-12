package edu.uw.medhas.aroundthecorner.listener.impl;

import android.content.Context;
import android.widget.Toast;

import edu.uw.medhas.aroundthecorner.model.Category;
import edu.uw.medhas.aroundthecorner.presenter.CategoryPresenter;
import edu.uw.medhas.aroundthecorner.presenter.MapPresenter;
import edu.uw.medhas.aroundthecorner.presenter.PlacePresenter;
import edu.uw.medhas.aroundthecorner.view.CategoryListFragment;

/**
 * Created by medhas on 1/28/18.
 */

public class CategoryListFragmentListenerImpl implements CategoryListFragment.Listener {
    private final MapPresenter mMapPresenter;
    private final CategoryPresenter mCategoryPresenter;
    private final PlacePresenter mPlacePresenter;
    private final Context mContext;

    public CategoryListFragmentListenerImpl(MapPresenter mapPresenter,
                                            CategoryPresenter categoryPresenter,
                                            PlacePresenter placePresenter,
                                            Context context) {
        mMapPresenter = mapPresenter;
        mCategoryPresenter = categoryPresenter;
        mPlacePresenter = placePresenter;
        mContext = context;
    }

    @Override
    public void onItemClicked(int position) {
        final Category category = mCategoryPresenter.getCategoryForPosition(position);

        if (mPlacePresenter.getPointOfInterest() == null) {
            Toast.makeText(
                    mContext,
                    "Please select a point of interest before searching for categories",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        mMapPresenter.searchPlacesForCategory(category);
    }
}
