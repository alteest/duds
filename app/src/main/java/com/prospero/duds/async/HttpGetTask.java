package com.prospero.duds.async;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class HttpGetTask extends HttpTask {
    protected HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //con.setUseCaches(false);
        //con.setDoOutput(true);
        con.setRequestProperty("User-Agent", "DUDS");
        con.setRequestProperty("Cache-Control", "no-cache");
        return con;
    }

    protected void sendRequest(HttpURLConnection con) {}
}
