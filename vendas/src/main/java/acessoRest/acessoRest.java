package acessoRest;

import org.apache.http.impl.client.DefaultHttpClient;

public class acessoRest {
    private int TIMEOUT_MILLISEC = 3000;

    public String chamadaGet(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        return url;
    }
}
