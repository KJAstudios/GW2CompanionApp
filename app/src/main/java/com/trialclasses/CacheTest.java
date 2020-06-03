package com.trialclasses;

import android.content.Context;

import com.gw2apiparser.FailedHttpCallException;
import com.gw2apiparser.HttpGetRequest;
import com.gw2apiparser.UrlBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

//TODO translate this into a permanent class once finished building the system
public class CacheTest {
    /**
     * this loads the dailies to a file in the android system cache
     *
     * @param context context in order to know where the cache is
     * @throws FailedHttpCallException thrown if any errors occur in calling/saving api data
     */
    public static void saveDailies(Context context) throws FailedHttpCallException {
        String result = null;

        //this portion pulls submits data calls and gets data from the api
        String url = UrlBuilder.getDailyURL();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(url).get();
        } catch (InterruptedException e) {
            throw new FailedHttpCallException("HTTP call interrupted");
        } catch (ExecutionException e) {
            throw new FailedHttpCallException("HTTP call failed");
        }
        if (result == null) {
            throw new FailedHttpCallException("Failed to retrieve info");
        }

        //File Saving portion of method
        String fileName = "dailies";
        try {
            File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            throw new FailedHttpCallException(e.toString() + " " + e.getMessage());
        }
        File tempDailyFile = new File(context.getCacheDir(), fileName);
        if (tempDailyFile != null) {
            try {
                FileWriter myWrite = new FileWriter(tempDailyFile.getPath());
                myWrite.write(result);
                myWrite.close();
                ;
            } catch (IOException e) {
                throw new FailedHttpCallException("Saving Dailies Failed");
            }
        }
    }
}
