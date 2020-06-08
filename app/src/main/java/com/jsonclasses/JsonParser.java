package com.jsonclasses;

import com.datastructures.ParsedDailyAchievements;
import com.google.gson.Gson;
import com.gw2apiparser.FailedHttpCallException;
import com.gw2apiparser.HttpGetRequest;
import com.gw2apiparser.UrlBuilder;
import com.jsonclasses.DailiesClasses;
import com.jsonclasses.jsonclassconverters.DailiesToParsedDailies;

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
        String result = null;
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
        DailiesClasses.AllDailies dailies = gson.fromJson(result, DailiesClasses.AllDailies.class);
        ParsedDailyAchievements parsedDailyAchievements = DailiesToParsedDailies.getParsedDailies(dailies);
        return parsedDailyAchievements;
    }

    /**
     * calls the api for an individual achievement
     *
     * @param id id of the achievement to return
     * @return returns the info of the achievement
     * @throws FailedHttpCallException thrown if any API calls fail
     */
    public static DailiesClasses.SingleAchievement getAchievement(int id) throws FailedHttpCallException {
        String result = null;
        Gson gson = new Gson();
        String url = UrlBuilder.getAchievementURL(id);
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
        DailiesClasses.SingleAchievement achievement = gson.fromJson(result, DailiesClasses.SingleAchievement.class);
        return achievement;
    }
}


