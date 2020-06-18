package com.datastructures;

import com.jsonclasses.ItemClass;

import java.util.ArrayList;

/**
 * holds all information for a single achievement, extends AbstractAchievement which holds most fields
 * for information storage
 */
public class FullAchievement extends AbstractAchievement {
    private int levelMax;
    private int levelMin;
    private ArrayList<ItemClass> rewards = new ArrayList<>();

    /**
     * getters and setters for the level requirements and reward list
     */
    public void setLevelMax(int levelMax) {
        this.levelMax = levelMax;
    }

    public void setLevelMin(int levelMin) {
        this.levelMin = levelMin;
    }

    public int getLevelMax() {
        return levelMax;
    }

    public int getLevelMin() {
        return levelMin;
    }

    public ArrayList<ItemClass> getRewardList() {
        return rewards;
    }

    /**
     * adds a reward to the reward list
     */
    public void addReward(ItemClass reward){
        rewards.add(reward);
    }
}
