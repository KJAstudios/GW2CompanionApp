package com.jsonclasses;

import java.util.ArrayList;

//class to hold an item description
public class ItemClass {
    String name;
    String description;
    String type;
    int level;
    String rarity;
    int vendor_value;
    ArrayList<String> game_types;
    ArrayList<String> flags;
    ArrayList<String> restrictions;
    int id;
    String chat_link;
    String icon;
    Details details;

    public class Details {
        String type;

        public String getType() {
            return type;
        }
    }

    /**
     * getters
     */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public String getRarity() {
        return rarity;
    }

    public int getVendor_value() {
        return vendor_value;
    }

    public ArrayList<String> getGame_types() {
        return game_types;
    }

    public ArrayList<String> getFlags() {
        return flags;
    }

    public ArrayList<String> getRestrictions() {
        return restrictions;
    }

    public int getId() {
        return id;
    }

    public String getChat_link() {
        return chat_link;
    }

    public String getIcon() {
        return icon;
    }

    public Details getDetails() {
        return details;
    }
}
