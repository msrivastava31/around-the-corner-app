package edu.uw.medhas.aroundthecorner.presenter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.medhas.aroundthecorner.model.Category;
import edu.uw.medhas.aroundthecorner.presenter.CategoryPresenter;

/**
 * Created by medhas on 1/28/18.
 */

public class CategoryPresenterImpl implements CategoryPresenter {
    private final Map<Integer, Category> mCategoryMap;

    public CategoryPresenterImpl() {
        mCategoryMap = new HashMap<>();

        for (Category category : Category.values()) {
            mCategoryMap.put(category.getPosition(), category);
        }
    }

    @Override
    public String getCategoryNameForPosition(int position) {
        return getCategoryForPosition(position).getDisplayText();
    }

    @Override
    public Category getCategoryForPosition(int position) {
        return mCategoryMap.get(position);
    }

    @Override
    public List<Category> getCategories() {
        return Collections.unmodifiableList(new ArrayList<>(mCategoryMap.values()));
    }
}
