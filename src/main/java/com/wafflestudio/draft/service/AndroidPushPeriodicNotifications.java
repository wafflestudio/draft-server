package com.wafflestudio.draft.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AndroidPushPeriodicNotifications {

    public static String PeriodicNotificationJson() throws JSONException {
        LocalDate localDate = LocalDate.now();

        String[] sampleData = {"device token value 1", "device token value 2", "device token value 3"};

        JSONObject body = new JSONObject();

        List<String> tokens = new ArrayList<>(Arrays.asList(sampleData));

        JSONArray array = new JSONArray();

        for (String token : tokens) {
            array.put(token);
        }

        body.put("registration_ids", array);

        JSONObject notification = new JSONObject();
        notification.put("title", "hello!");
        notification.put("body", "Today is " + localDate.getDayOfWeek().name() + "!");

        body.put("notification", notification);

        System.out.println(body.toString());

        return body.toString();
    }
}
