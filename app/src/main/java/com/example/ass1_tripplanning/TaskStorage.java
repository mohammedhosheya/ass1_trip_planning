package com.example.ass1_tripplanning;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TaskStorage {

    private static final String PREF_NAME = "trip_prefs";
    private static final String KEY_TASKS = "tasks_json";


    public static ArrayList<TripTask> loadTasks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_TASKS, null);

        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TripTask>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public static void saveTasks(Context context, ArrayList<TripTask> tasks) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        prefs.edit().putString(KEY_TASKS, json).apply();
    }


    public static int getNextId(ArrayList<TripTask> tasks) {
        int maxId = 0;
        for (TripTask t : tasks) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1;
    }


    public static TripTask findTaskById(ArrayList<TripTask> tasks, int id) {
        for (TripTask t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
}