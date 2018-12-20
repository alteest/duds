package com.prospero.duds.async;

public class SearchBoxesRequest {
    protected String url;
    protected String filepath;

    public SearchBoxesRequest(String url, String filepath) {
        this.url = url;
        this.filepath = filepath;
    }

    public String getUrl() {
        return url;
    }

    public String getFilepath() {
        return filepath;
    }
}
