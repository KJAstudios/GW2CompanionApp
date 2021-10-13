package com.gw2apiparser;

import android.content.Context;

import com.datastructures.FullAchievement;
import com.main.gw2companionapp.MainActivity;
import com.example.gw2companionapp.R;
import com.jsonclasses.DailiesClasses;

import java.util.ArrayList;

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
     * saved for expansion later
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

    /**
     * returns the url of an unknown amount of achievements
     *
     * @param achievements list of achievements to make a url for
     * @return the completed URL as a String
     */
    public static String getAchievementsURL(ArrayList<DailiesClasses.DailyAchievement> achievements) {
        String Url = getBaseUrl(MainActivity.context, 2);
        Url += MainActivity.context.getString(R.string.multiple_achievements_endpoint);
        for (int i = 0; i < achievements.size(); i++) {
            String id = Integer.toString(achievements.get(i).id);
            if (i == 0) {
                Url += id;
            } else {
                Url = Url + "," + id;
            }
        }
        return Url;
    }

    /**
     * returns the url of an unknown amount of item ids
     *
     * @param achievements list of achievements to get the items for
     * @return the completed URL of achievement items to get
     */
    public static String getDailyAchievementsRewardsURL(ArrayList<FullAchievement> achievements) {
        String Url = getBaseUrl(MainActivity.context, 2);
        Url += MainActivity.context.getString(R.string.items_endpoint);
        for (int i = 0; i < achievements.size(); i++) {
            for (DailiesClasses.SingleAchievement.Reward reward : achievements.get(i).getRewards()) {
                if (i == 0) {
                    Url += reward.id;
                } else {
                    Url = Url + "," + reward.id;
                }
            }
        }
        return Url;
    }

    /*
    private static <T> String createIdString(List<T> list) {

    }

     */
}
