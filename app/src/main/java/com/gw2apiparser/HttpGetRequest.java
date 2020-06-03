package com.gw2apiparser;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(params[0].trim());
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
            } catch (IOException e) {
                return "json reading failed cause: " + e + "message" + e.getMessage();
            }
        } catch (MalformedURLException e) {
            return "Bad URL given";
        }
        return json.toString().trim();

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }


}
