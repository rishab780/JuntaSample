package com.skeleton.model.userProfile;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rishab on 18-05-2017.
 */

public class PicURL {
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("original")
    private String original;

    /**
     * @return URL  to thumbnail of image
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return URL to original image
     */
    public String getOriginal() {
        return original;
    }
}
