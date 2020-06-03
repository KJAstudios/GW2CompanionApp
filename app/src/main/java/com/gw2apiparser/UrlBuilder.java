package com.gw2apiparser;

import android.content.Context;

import com.example.gw2companionapp.MainActivity;
import com.example.gw2companionapp.R;

public class UrlBuilder {

    /**
     * returns the base necessary url for the gw2 api
     *
     * @param context       context of the activity to retrieve necessary resource data
     * @param versionNumber version number of the api to collect
     * @return the api version url string
     */
    protected static String getBaseUrl(Context context, int versionNumber) {
        String baseURL;
        switch (versionNumber) {
            case 1:
                baseURL = context.getString(R.string.api_v1_url);
                return baseURL;
            case 2:
                baseURL = context.getString(R.string.api_v2_url);
                return baseURL;
            default:
                return null;
        }
    }

    /**
     * returns the URL for retrieving daily achievements
     *
     * @return complete URL for dailies
     */
    public static String getDailyURL() {
        Context context = MainActivity.context;
        String baseURL;
        baseURL = getBaseUrl(context, 2);
        String returnURL = baseURL + context.getString(R.string.daily_endpoint);
        returnURL.trim();
        return returnURL;
    }

    /**
     * returns the URL for getting data on a specific achievement
     *
     * @param id id of achievement
     * @return complete String for the achievement URL
     */
    public static String getAchievementURL(int id) {
        Context context = MainActivity.context;
        String baseURL = getBaseUrl(context, 2);
        String returnURL = baseURL + context.getString(R.string.achievement_endpoint) + id;
        returnURL.trim();
        return returnURL;
    }

    private String getURL(String requestType) {
        requestType.trim();
        requestType.toLowerCase();
        switch (requestType) {
            case "daily":
                return UrlBuilder.getDailyURL();
            default:
                return "Invalid Request";
        }

    }

    private String getURL(String requestType, int id) {
        requestType.trim();
        requestType.toLowerCase();
        switch (requestType) {
            case "achievement":
                return UrlBuilder.getAchievementURL(id);
            default:
                return "Invalid Request";
        }

    }
}
