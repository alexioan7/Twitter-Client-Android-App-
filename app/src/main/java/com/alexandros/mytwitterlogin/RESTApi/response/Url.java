package com.alexandros.mytwitterlogin.RESTApi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Url {


    @SerializedName("urls")
    @Expose
    private List<Url__1> urls = null;

    public List<Url__1> getUrls() {
        return urls;
    }

    public void setUrls(List<Url__1> urls) {
        this.urls = urls;
    }
}
