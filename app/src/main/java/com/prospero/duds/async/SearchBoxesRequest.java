package com.prospero.duds.async;

class SearchBoxesRequest {
    private String url;
    private String filepath;

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
