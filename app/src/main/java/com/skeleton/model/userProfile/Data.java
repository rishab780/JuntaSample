package com.skeleton.model.userProfile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rishab on 18-05-2017.
 */

public class Data {
    @SerializedName("categories")
    private List<Categories> categories;

    /**
     * @return categories of interests
     */
    public List<Categories> getCategories() {
        return categories;
    }
}
