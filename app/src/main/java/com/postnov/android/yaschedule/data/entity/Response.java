package com.postnov.android.yaschedule.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 19.05.2016.
 */
public class Response {

    @SerializedName("threads")
    private Variants[] mVariants;

    @SerializedName("pagination")
    private Pagination mPagination;

    public Variants[] getVariants ()
    {
        return mVariants;
    }

    public void setVariants (Variants[] variants)
    {
        this.mVariants = variants;
    }

    public Pagination getPagination ()
    {
        return mPagination;
    }

    public void setPagination (Pagination pagination)
    {
        this.mPagination = pagination;
    }
}
