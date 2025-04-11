package org.example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // 👈 This is the fix!
public class Section {

    @JsonProperty("sectionID")
    private String sectionID;

    @JsonProperty("sectionData")
    private List<Stream> sectionData;

    // Getters and setters
    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public List<Stream> getSectionData() {
        return sectionData;
    }

    public void setSectionData(List<Stream> sectionData) {
        this.sectionData = sectionData;
    }
}

