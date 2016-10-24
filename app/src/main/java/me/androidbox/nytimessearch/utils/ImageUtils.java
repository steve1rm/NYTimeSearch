package me.androidbox.nytimessearch.utils;

import android.text.TextUtils;

import me.androidbox.nytimessearch.model.NYTimesSearch;

/**
 * Created by steve on 10/23/16.
 */

public final class ImageUtils {
    public static boolean isValidImagePath(NYTimesSearch nyTimesSearch, int position) {

        if(nyTimesSearch.getResponse().getDocs().get(position).getMultimedia().isEmpty()) {
            return false;
        }

        if(TextUtils.isEmpty(nyTimesSearch.getResponse().getDocs().get(position).getMultimedia().get(0).getUrl())) {
            return false;
        }

        return true;
    }
}
