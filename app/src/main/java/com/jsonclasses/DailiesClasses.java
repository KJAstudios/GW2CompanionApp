package com.jsonclasses;

import com.datastructures.AbstractAchievement;

import java.util.ArrayList;

/**
 * this class stores all temp classes needed to parse the Daily Acheivement JSON into Java objects
 * via GSON
 */

public class DailiesClasses {

    public class AllDailies {
        public ArrayList<DailyAchievement> pve;
        public ArrayList<DailyAchievement> pvp;
        public ArrayList<DailyAchievement> wvw;
        public ArrayList<DailyAchievement> fractals;
        public ArrayList<DailyAchievement> special;


    }

    public class SingleAchievement extends AbstractAchievement {


        public class Tier {
            int count;
            int points;
        }

        public class Reward {
            public String type;
            public int id;
            public int count;
        }
    }

    public class DailyAchievement {
        public int id;
        public LevelReq level;
        public String[] required_access;

        public class LevelReq {
            public int min;
            public int max;

            public int getMin() {
                return min;
            }

            public int getMax() {
                return max;
            }
        }
    }
}
