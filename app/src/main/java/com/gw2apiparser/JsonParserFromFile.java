package com.gw2apiparser;

import android.content.Context;

import com.google.gson.Gson;
import com.jsonclasses.DailiesClasses;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class JsonParserFromFile {

    /**
     * converts the json file holding the dailies into an object to retrieve data
     * @param context activity context to grab file from cache
     * @return AllDailies object of converted json
     * @throws Exception if reading from dailies fail
     */
    public static DailiesClasses.AllDailies getDailiesFromFile(Context context) throws Exception {
        Reader reader;
        File dailyFile = new File(context.getCacheDir(), "dailies");
        reader = new FileReader(dailyFile);
        Gson gson = new Gson();
        DailiesClasses.AllDailies dailies = gson.fromJson(reader, DailiesClasses.AllDailies.class);
        return dailies;
    }
}
