package com.datastructures;

import java.util.ArrayList;

/**
 * stores parsed data from the daily api calls, combining the daily achievement info with the
 * full achievement info
 */
public class ParsedDailyAchievements {
    ArrayList<FullAchievement> pve;
    ArrayList<FullAchievement> pvp;
    ArrayList<FullAchievement> wvw;
    ArrayList<FullAchievement> fractals;
    ArrayList<FullAchievement> special;
    String error;


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
}
