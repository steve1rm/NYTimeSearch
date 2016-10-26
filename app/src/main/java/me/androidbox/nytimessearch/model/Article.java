package me.androidbox.nytimessearch.model;

/**
 * Created by steve on 10/25/16.
 */

public class Article {
    String webUrl;
    String Headline;
    String thumbNail;

    public Article(String headline, String thumbNail, String webUrl) {
        Headline = headline;
        this.thumbNail = thumbNail;
        this.webUrl = webUrl;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
