package edu.uw.medhas.aroundthecorner.presenter.impl;

import edu.uw.medhas.aroundthecorner.presenter.CategoryListFragmentPresenter;
import edu.uw.medhas.aroundthecorner.view.CategoryListFragment;

/**
 * Created by medhas on 1/28/18.
 */

public class CategoryListFragmentPresenterImpl implements CategoryListFragmentPresenter {
    private final CategoryListFragment.Listener mCategoryListFragmentListener;

    public CategoryListFragmentPresenterImpl(CategoryListFragment.Listener categoryListFragmentListener) {
        mCategoryListFragmentListener = categoryListFragmentListener;
    }

    @Override
    public CategoryListFragment.Listener getCategoryListFragmentListener() {
        return mCategoryListFragmentListener;
    }
}
