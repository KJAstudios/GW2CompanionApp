package com.jsonclasses;

import com.datastructures.FullAchievement;
import com.datastructures.ParsedDailyAchievements;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.gw2apiparser.FailedHttpCallException;
import com.gw2apiparser.HttpGetRequest;
import com.gw2apiparser.UrlBuilder;
import com.jsonclasses.jsonclassconverters.DailiesToParsedDailies;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class JsonParser {
    /**
     * gets the dailies from the api, and returns them as a ParsedDailyAchievements object that holds
     * all the data of the current achievements
     *
     * @return the parsed daily information from the api call
     * @throws FailedHttpCallException thrown if any api calls fail, so that the fail info can be
     *                                 propagated to the actual application fragment
     */
    public static ParsedDailyAchievements getDailies() throws FailedHttpCallException {
        String result;
        Gson gson = new Gson();
        String url = UrlBuilder.getDailyURL();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(url).get();
        } catch (InterruptedException e) {
            throw new FailedHttpCallException("HTTP call interrupted");
        } catch (ExecutionException e) {
            throw new FailedHttpCallException("HTTP call failed");
        }
        if (result == null) {
            throw new FailedHttpCallException("Failed to retrieve info");
        }
        
        DailiesClasses.AllDailies dailies;
        try {
            dailies = gson.fromJson(result, DailiesClasses.AllDailies.class);
        } catch (JsonSyntaxException e) {
            throw new FailedHttpCallException("Dailies failed to load");
        }
        ParsedDailyAchievements parsedDailyAchievements = DailiesToParsedDailies.getParsedDailies(dailies);
        return parsedDailyAchievements;
    }

    /**
     * calls the api to get the full list of achievements
     *
     * @param achievements list of dailyAchievements to turn into SingleAchievements
     * @return the list of SingleAchievements to parse the dailies from
     * @throws FailedHttpCallException thrown if something fails somewhere
     */
    public static ArrayList<DailiesClasses.SingleAchievement> getAchievements(
            ArrayList<DailiesClasses.DailyAchievement> achievements)
            throws FailedHttpCallException {
        String result = null;
        Gson gson = new Gson();
        String url = UrlBuilder.getAchievementsURL(achievements);
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(url).get();
        } catch (InterruptedException e) {
            throw new FailedHttpCallException("HTTP call interrupted");
        } catch (ExecutionException e) {
            throw new FailedHttpCallException("HTTP call failed");
        }
        if (result == null) {
            throw new FailedHttpCallException("Failed to retrieve info");
        }
        Type userListType = new TypeToken<ArrayList<DailiesClasses.SingleAchievement>>() {
        }.getType();
        ArrayList<DailiesClasses.SingleAchievement> allSingleAchievements = gson.fromJson(
                result, userListType);
        return allSingleAchievements;

    }

    /**
     * gets a list of rewards for the daily achievements
     *
     * @param achievements list of daily achievements
     * @return returns the list of rewards for ALL achievements in list given
     * @throws FailedHttpCallException thrown if error making api calls
     */
    public static ArrayList<ItemClass> getAchievementRewards(
            ArrayList<FullAchievement> achievements) throws FailedHttpCallException {
        String result = null;
        Gson gson = new Gson();
        HttpGetRequest getRequest = new HttpGetRequest();
        String url = UrlBuilder.getDailyAchievementsRewardsURL(achievements);
        try {
            result = getRequest.execute(url).get();
        } catch (InterruptedException e) {
            throw new FailedHttpCallException("HTTP call interrupted");
        } catch (ExecutionException e) {
            throw new FailedHttpCallException("HTTP call failed");
        }
        if (result == null) {
            throw new FailedHttpCallException("Failed to retrieve info");
        }
        Type userListType = new TypeToken<ArrayList<ItemClass>>() {
        }.getType();
        ArrayList<ItemClass> allDailyItems = gson.fromJson(result, userListType);
        return allDailyItems;
    }
}


