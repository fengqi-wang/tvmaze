package com.fengqi.tvmaze.model;

/**
 * Created by fengqi on 2017-08-16.
 */

import com.fengqi.tvmaze.model.util.DateFormatHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Episode {
    public String getIcon() {return (image==null)? null : image.medium;}
    public String getTime() {return DateFormatHelper.format(airstamp);}

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("season")
    @Expose
    public Integer season;
    @SerializedName("number")
    @Expose
    public Integer number;
    @SerializedName("airdate")
    @Expose
    public String airdate;
    @SerializedName("airtime")
    @Expose
    public String airtime;
    @SerializedName("airstamp")
    @Expose
    public Date airstamp;
    @SerializedName("runtime")
    @Expose
    public Integer runtime;
    @SerializedName("image")
    @Expose
    public Image image;
    @SerializedName("summary")
    @Expose
    public String summary;
    @SerializedName("_links")
    @Expose
    public Links links;

    private class Image {
        @SerializedName("medium")
        @Expose
        public String medium;
        @SerializedName("original")
        @Expose
        public String original;
    }

    private class Links {

        @SerializedName("self")
        @Expose
        public Self self;
    }

    private class Self {

        @SerializedName("href")
        @Expose
        public String href;

    }
}
