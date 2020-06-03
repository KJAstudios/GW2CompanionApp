package com.datastructures;

/**
 * holds all information for a single achievement, extends AbstractAchievement which holds most fields
 * for information storage
 */
public class FullAchievement extends AbstractAchievement {
    private int levelMax;
    private int levelMin;

    /**
     * setters for the level requirements
     */
    public void setLevelMax(int levelMax) {
        this.levelMax = levelMax;
    }

    public void setLevelMin(int levelMin) {
        this.levelMin = levelMin;
    }
}
