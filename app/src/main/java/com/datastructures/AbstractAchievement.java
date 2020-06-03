package com.datastructures;

import com.jsonclasses.DailiesClasses;

import java.util.ArrayList;


/**
 * abstract class to allow for achievement classes of different types, while still holding the
 * base info every achievement has
 */
public abstract class AbstractAchievement {
    private int id;
    private String name;
    private String description;
    private String requirement;
    private String locked_text;
    private String type;
    private String[] flags;
    private ArrayList<DailiesClasses.SingleAchievement.Tier> tiers;
    private ArrayList<DailiesClasses.SingleAchievement.Reward> rewards;

    /**
     * getters and setters for the achievement information stored in the fields here
     *
     * @return
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getLocked_text() {
        return locked_text;
    }

    public void setLocked_text(String locked_text) {
        this.locked_text = locked_text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getFlags() {
        return flags;
    }

    public void setFlags(String[] flags) {
        this.flags = flags;
    }

    public ArrayList<DailiesClasses.SingleAchievement.Tier> getTiers() {
        return tiers;
    }

    public void setTiers(ArrayList<DailiesClasses.SingleAchievement.Tier> tiers) {
        this.tiers = tiers;
    }

    public ArrayList<DailiesClasses.SingleAchievement.Reward> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<DailiesClasses.SingleAchievement.Reward> rewards) {
        this.rewards = rewards;
    }
}
