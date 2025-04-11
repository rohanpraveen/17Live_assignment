package org.example.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Stream {
    @JsonProperty("streamerID")
    private String streamerID;

    public String getStreamerID() {
        return streamerID;
    }

    public void setStreamerID(String streamerID) {
        this.streamerID = streamerID;
    }
}
