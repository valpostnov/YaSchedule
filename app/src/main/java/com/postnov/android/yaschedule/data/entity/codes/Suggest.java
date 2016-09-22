package com.postnov.android.yaschedule.data.entity.codes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 24.05.2016.
 */
public class Suggest
{
    @SerializedName("full_title")
    private String fullTitle;

    @SerializedName("title")
    private String title;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("object_id")
    private int objectId;

    @SerializedName("full_title_ru")
    private String fullTitleRu;

    @SerializedName("title_ru")
    private String titleRu;

    @SerializedName("ttype")
    private String ttype;

    @SerializedName("type")
    private String type;

    @SerializedName("point_key")
    private String pointKey;

    public String getPointKey() {
        return pointKey;
    }

    public void setPointKey(String pointKey) {
        this.pointKey = pointKey;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getFullTitleRu() {
        return fullTitleRu;
    }

    public void setFullTitleRu(String fullTitleRu) {
        this.fullTitleRu = fullTitleRu;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Suggest suggest = (Suggest) o;

        if (countryId != suggest.countryId) return false;
        if (objectId != suggest.objectId) return false;
        if (!fullTitle.equals(suggest.fullTitle)) return false;
        if (!title.equals(suggest.title)) return false;
        if (!fullTitleRu.equals(suggest.fullTitleRu)) return false;
        if (!titleRu.equals(suggest.titleRu)) return false;
        if (!ttype.equals(suggest.ttype)) return false;
        if (!type.equals(suggest.type)) return false;
        return pointKey.equals(suggest.pointKey);

    }

    @Override
    public int hashCode() {
        int result = fullTitle.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + countryId;
        result = 31 * result + objectId;
        result = 31 * result + fullTitleRu.hashCode();
        result = 31 * result + titleRu.hashCode();
        result = 31 * result + ttype.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + pointKey.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Suggest{" +
                "fullTitle='" + fullTitle + '\'' +
                ", title='" + title + '\'' +
                ", countryId=" + countryId +
                ", objectId=" + objectId +
                ", fullTitleRu='" + fullTitleRu + '\'' +
                ", titleRu='" + titleRu + '\'' +
                ", ttype='" + ttype + '\'' +
                ", type='" + type + '\'' +
                ", pointKey='" + pointKey + '\'' +
                '}';
    }
}
