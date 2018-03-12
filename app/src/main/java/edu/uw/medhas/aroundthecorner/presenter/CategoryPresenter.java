package edu.uw.medhas.aroundthecorner.presenter;

import java.util.List;

import edu.uw.medhas.aroundthecorner.model.Category;

/**
 * Created by medhas on 1/28/18.
 */
public interface CategoryPresenter {
    String getCategoryNameForPosition(int position);

    Category getCategoryForPosition(int position);

    List<Category> getCategories();
}
