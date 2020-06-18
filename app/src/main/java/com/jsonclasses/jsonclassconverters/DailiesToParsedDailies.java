package com.jsonclasses.jsonclassconverters;

import com.datastructures.FullAchievement;
import com.datastructures.ParsedDailyAchievements;
import com.gw2apiparser.FailedHttpCallException;
import com.jsonclasses.DailiesClasses;
import com.jsonclasses.ItemClass;
import com.jsonclasses.JsonParser;

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
        if (!dailies.special.isEmpty()) {
            parsedDailyAchievements.setSpecial(parseAchievements(dailies.special));
        }

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
        ArrayList<DailiesClasses.SingleAchievement> singleAchievements =
                JsonParser.getAchievements(dailyList);
        ArrayList<FullAchievement> tempList = new ArrayList<>();
        if (!dailyList.isEmpty()) {
            for (int i = 0; i < dailyList.size(); i++) {
                DailiesClasses.DailyAchievement dailyAchievement = dailyList.get(i);
                DailiesClasses.SingleAchievement singleAchievement = null;
                //this makes sure all the dailies get parsed, even though there is a duplicate
                for (DailiesClasses.SingleAchievement achieve : singleAchievements) {
                    if (dailyAchievement.id == achieve.getId()) {
                        singleAchievement = achieve;
                    }
                }
                FullAchievement tempAchievement = new FullAchievement();
                tempAchievement.setLevelMax(dailyAchievement.level.max);
                tempAchievement.setLevelMin(dailyAchievement.level.min);
                tempAchievement.setId(dailyAchievement.id);
                tempAchievement.setName(singleAchievement.getName());
                tempAchievement.setDescription(singleAchievement.getDescription());
                tempAchievement.setRequirement(singleAchievement.getRequirement());
                tempAchievement.setLocked_text(singleAchievement.getLocked_text());
                tempAchievement.setType(singleAchievement.getType());
                tempAchievement.setFlags(singleAchievement.getFlags());
                tempAchievement.setRewards(singleAchievement.getRewards());
                tempList.add(tempAchievement);
            }
        }
        tempList = parseRewards(tempList);
        return tempList;
    }

    /**
     * adds the parsed reward information to the achievements they belong to
     *
     * @param dailyList list of achievements to add rewards to
     * @return dailyList after rewards have been added
     * @throws FailedHttpCallException thrown if there is a data acquisition issue
     */
    private static ArrayList<FullAchievement> parseRewards(ArrayList<FullAchievement> dailyList)
            throws FailedHttpCallException {
        ArrayList<ItemClass> parsedRewards = JsonParser.getAchievementRewards(dailyList);
        for (FullAchievement achievement : dailyList) {
            for (int i = 0; i < achievement.getRewards().size(); i++) {
                int id = achievement.getRewards().get(i).id;
                for (int c = 0; c < parsedRewards.size(); c++) {
                    if (parsedRewards.get(c).getId() == id && !checkIfRewardInList(id, achievement)) {
                        achievement.addReward(parsedRewards.get(c));
                    }
                }
            }
        }
        return dailyList;
    }

    /**
     * checks if the reward is in the reward list
     *
     * @param id          id of reward to check
     * @param achievement achievement to make sure there is no duplicate rewards
     * @return true if reward is in the achievement's reward list
     */
    private static Boolean checkIfRewardInList(int id, FullAchievement achievement) {
        if (!achievement.getRewardList().isEmpty()) {
            for (ItemClass reward : achievement.getRewardList()) {
                if (reward.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }
}
