package com.postnov.android.yaschedule.data.entity.schedule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 19.05.2016.
 */
public class Pagination
{
    @SerializedName("total")
    private int total;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("page")
    private int page;

    @SerializedName("has_next")
    private boolean hasNext;

    @SerializedName("page_count")
    private int pageCount;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pagination that = (Pagination) o;

        if (total != that.total) return false;
        if (perPage != that.perPage) return false;
        if (page != that.page) return false;
        if (hasNext != that.hasNext) return false;
        return pageCount == that.pageCount;

    }

    @Override
    public int hashCode() {
        int result = total;
        result = 31 * result + perPage;
        result = 31 * result + page;
        result = 31 * result + (hasNext ? 1 : 0);
        result = 31 * result + pageCount;
        return result;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "total=" + total +
                ", perPage=" + perPage +
                ", page=" + page +
                ", hasNext=" + hasNext +
                ", pageCount=" + pageCount +
                '}';
    }
}
