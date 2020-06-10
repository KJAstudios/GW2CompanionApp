package com.datastructures;

import android.content.Context;

import com.gw2apiparser.FailedHttpCallException;
import com.jsonclasses.JsonParser;

import java.util.ArrayList;

/**
 * stores parsed data from the daily api calls, combining the daily achievement info with the
 * full achievement info
 */
public class ParsedDailyAchievements {
    private ArrayList<FullAchievement> pve;
    private ArrayList<FullAchievement> pvp;
    private ArrayList<FullAchievement> wvw;
    private ArrayList<FullAchievement> fractals;
    private ArrayList<FullAchievement> special;
    private String error;
    private static ParsedDailyAchievements dailyAchievements;


    //made this a singleton so that I can access it from anywhere, and only make one copy
    public static ParsedDailyAchievements getInstance(Context context){
        if (dailyAchievements == null){
            try {
                dailyAchievements = JsonParser.getDailies();
            }
            catch (FailedHttpCallException e){
                dailyAchievements = new ParsedDailyAchievements();
                dailyAchievements.setError(e.getMessage());
            }
        }
        return dailyAchievements;
    }

    //checker to see if dailies have loaded/failed yet
    public static boolean checkDailiesLoaded(){
        if(dailyAchievements == null){
            return false;
        }
        return true;
    }

    //getter so you can select what array to get, instead of having to specify
    public ArrayList<FullAchievement> getAchieveList(String type) {
        switch (type) {
            case "pve":
                return pve;
            case "pvp":
                return pvp;
            case "fractal":
                return fractals;
            case "wvw":
                return wvw;
        }
        return null;
    }

    public FullAchievement getAchievement(String type, int id){
        ArrayList<FullAchievement> list = getAchieveList(type);
        for(FullAchievement achievement: list){
            if(achievement.getId() == id){
                return achievement;
            }
        }
        return null;
    }

    /**
     * getters and setters for the different achievement lists
     */
    public ArrayList<FullAchievement> getPve() {
        return pve;
    }

    public void setPve(ArrayList<FullAchievement> pve) {
        this.pve = pve;
    }

    public ArrayList<FullAchievement> getPvp() {
        return pvp;
    }

    public void setPvp(ArrayList<FullAchievement> pvp) {
        this.pvp = pvp;
    }

    public ArrayList<FullAchievement> getWvw() {
        return wvw;
    }

    public void setWvw(ArrayList<FullAchievement> wvw) {
        this.wvw = wvw;
    }

    public ArrayList<FullAchievement> getFractals() {
        return fractals;
    }

    public void setFractals(ArrayList<FullAchievement> fractals) {
        this.fractals = fractals;
    }

    public ArrayList<FullAchievement> getSpecial() {
        return special;
    }

    public void setSpecial(ArrayList<FullAchievement> special) {
        this.special = special;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError(){
        return error;
    }
}
