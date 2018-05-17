package com.kenyrim.mikonatoruri.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("events")
    @Expose
    private List<Event> events = null;

    public List<Event> getEvents() {
        return events;
    }
}
