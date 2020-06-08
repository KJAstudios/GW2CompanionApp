package com.jsonclasses.jsonclassconverters;

import com.datastructures.FullAchievement;
import com.datastructures.ParsedDailyAchievements;
import com.example.gw2companionapp.MainActivity;
import com.gw2apiparser.FailedHttpCallException;
import com.jsonclasses.JsonParser;
import com.jsonclasses.DailiesClasses;

import java.util.ArrayList;

public class DailiesToParsedDailies {
    /**
     * converts the json object retrieved from the daily API call to a ParsedDailyAchievement object
     * to allow for data collection
     *
     * @param dailies dailies from the api call
     * @return ParsedDailyAchievements, holding detailed information of the dailies
     * @throws FailedHttpCallException thrown if api calls fail
     */
    public static ParsedDailyAchievements getParsedDailies(DailiesClasses.AllDailies dailies)
            throws FailedHttpCallException {
        ParsedDailyAchievements parsedDailyAchievements = new ParsedDailyAchievements();
        parsedDailyAchievements.setPve(parseAchievements(dailies.pve));
        parsedDailyAchievements.setPvp(parseAchievements(dailies.pvp));
        parsedDailyAchievements.setWvw(parseAchievements(dailies.wvw));
        parsedDailyAchievements.setFractals(parseAchievements(dailies.fractals));
        parsedDailyAchievements.setSpecial(parseAchievements(dailies.special));

        return parsedDailyAchievements;
    }

    /**
     * parses the individual achievements of the daily type list given
     *
     * @param dailyList the subcategory list of daily achievements to parse into FullAchievements
     * @return the parsed list of FullAchievements
     * @throws FailedHttpCallException thrown if any API calls fail
     */
    private static ArrayList<FullAchievement> parseAchievements(ArrayList<DailiesClasses.DailyAchievement> dailyList)
            throws FailedHttpCallException {
        ArrayList<FullAchievement> tempList = new ArrayList<>();
        if (!dailyList.isEmpty()) {
            for (DailiesClasses.DailyAchievement daily : dailyList) {
                FullAchievement tempAchievement = new FullAchievement();
                tempAchievement.setLevelMax(daily.level.max);
                tempAchievement.setLevelMin(daily.level.min);
                tempAchievement.setId(daily.id);
                DailiesClasses.SingleAchievement achievementData = JsonParser.getAchievement(tempAchievement.getId());
                tempAchievement.setName(achievementData.getName());
                tempAchievement.setDescription(achievementData.getDescription());
                tempAchievement.setRequirement(achievementData.getRequirement());
                tempAchievement.setLocked_text(achievementData.getLocked_text());
                tempAchievement.setType(achievementData.getType());
                tempAchievement.setFlags(achievementData.getFlags());
                tempAchievement.setRewards(achievementData.getRewards());

                tempList.add(tempAchievement);
            }
        }
        return tempList;

    }
}
