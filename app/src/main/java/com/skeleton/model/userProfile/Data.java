package com.skeleton.model.userProfile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("Orientation")
    private List<String> Orientation;
    @SerializedName("RelationshipHistory")
    private List<String> RelationshipHistory;
    @SerializedName("Ethnicity")
    private List<String> Ethnicity;
    @SerializedName("Religion")
    private List<String> Religion;
    @SerializedName("BodyType")
    private List<String> BodyType;
    @SerializedName("Smoking")
    private List<String> Smoking;
    @SerializedName("Drinking")
    private List<String> Drinking;
    @SerializedName("Height")
    private List<String> Height;

    public List<String> getOrientation() {
        return Orientation;
    }

    public void setOrientation(List<String> Orientation) {
        this.Orientation = Orientation;
    }

    public List<String> getRelationshipHistory() {
        return RelationshipHistory;
    }

    public void setRelationshipHistory(List<String> RelationshipHistory) {
        this.RelationshipHistory = RelationshipHistory;
    }

    public List<String> getEthnicity() {
        return Ethnicity;
    }

    public void setEthnicity(List<String> Ethnicity) {
        this.Ethnicity = Ethnicity;
    }

    public List<String> getReligion() {
        return Religion;
    }

    public void setReligion(List<String> Religion) {
        this.Religion = Religion;
    }

    public List<String> getBodyType() {
        return BodyType;
    }

    public void setBodyType(List<String> BodyType) {
        this.BodyType = BodyType;
    }

    public List<String> getSmoking() {
        return Smoking;
    }

    public void setSmoking(List<String> Smoking) {
        this.Smoking = Smoking;
    }

    public List<String> getDrinking() {
        return Drinking;
    }

    public void setDrinking(List<String> Drinking) {
        this.Drinking = Drinking;
    }

    public List<String> getHeight() {
        return Height;
    }

    public void setHeight(List<String> Height) {
        this.Height = Height;
    }
}
