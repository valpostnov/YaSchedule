package com.postnov.android.yaschedule.data.entity.codes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by platon on 24.05.2016.
 */
public class Cities
{
    @SerializedName("suggests")
    private List<Suggest> suggests;

    public List<Suggest> getSuggests()
    {
        return suggests;
    }

    public void setSuggests(List<Suggest> suggests)
    {
        this.suggests = suggests;
    }
}
