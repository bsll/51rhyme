package com.rhyme.data.etl;

import org.json.JSONObject;


public class rhymeBean {
    public rhymeBean(String lastword,String body,String source,String author,String year, String title){
        this.lastword=lastword;
        this.body=body;
        this.source=source;
        this.author=author;
        this.year=year;
        this.title=title;
    }
    public  String lastword ;
    public  String body;

    public String getLastword() {
        return lastword;
    }

    public void setLastword(String lastword) {
        this.lastword = lastword;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public  String source;
    public  String author;
    public  String year;
    public String title;

    @Override
    public String toString() {
        return "rhymeBean{" +
                "lastword='" + lastword + '\'' +
                ", body='" + body + '\'' +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                '}';
    }


    public JSONObject getJSONObject() {

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("lastword", this.lastword);
        jsonObj.put("body", this.body);
        jsonObj.put("source", this.source);
        jsonObj.put("author", this.author);
        jsonObj.put("year", this.year);
        jsonObj.put("title", this.title);
        return jsonObj;
    }
}
