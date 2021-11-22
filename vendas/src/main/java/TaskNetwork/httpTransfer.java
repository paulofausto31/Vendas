package TaskNetwork;


import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by droidr2d2 on 23/01/2017.
 */
public class httpTransfer {

    private HttpURLConnection connection;

    private ArrayList<String> params;

    private BufferedReader bfrImport;
    private URL uImport = null;
    private InputStream inputStream =null;

    private String URL;

    public httpTransfer(String URL) throws IOException{
        setURL(URL);
        openConnect();
    }

    public void openConnect() throws IOException {

        //URL = "http://sitsys.com.br/webservice/w1.php?tab="+t+"&cnpj="+CurrentInfo.cnpjEmpresa;
        Log.i("URI CLIENTE", URL);


        uImport = new URL(getURL());
        connection = (HttpURLConnection) uImport.openConnection();
        connection.setRequestMethod("GET");
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
        connection.connect();

        inputStream = connection.getInputStream();

        bfrImport = new BufferedReader(new InputStreamReader(inputStream));
        while(bfrImport == null){
            connection.disconnect();
            connection.connect();
            bfrImport = new BufferedReader(new InputStreamReader(inputStream));
        }
        //   connect();


        //  String paramsImport = "";
         /*
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);

           connection.connect();

            inputStream = connection.getInputStream();
            bfrImport = new BufferedReader(new InputStreamReader(inputStream));*/

        // connect();
    }

    public void connect() throws IOException{
        try {
            /*connection = (HttpURLConnection) uImport.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);*/
            //connection.disconnect();
            connection.connect();
            // inputStream = connection.getInputStream();
            //bfrImport = new BufferedReader(new InputStreamReader(inputStream));
        }catch(UnknownHostException ex){
            // connection,disconnect();
            connect();
        }
    }

    public void connect2(URL uImport) throws IOException {
        int response = 0;
        //URL = "http://sitsys.com.br/webservice/w1.php?tab="+t+"&cnpj="+CurrentInfo.cnpjEmpresa;
        Log.i("URI CLIENTE", URL);
        //uImport = new URL(getURL());
        //  String paramsImport = "";
        connection = (HttpURLConnection) uImport.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();
        //response = connection.getResponseCode();

        inputStream = connection.getInputStream();
        bfrImport = new BufferedReader(new InputStreamReader(inputStream), 1204);

        //return response;
    }


    /*
        public BufferedReader getBufReader(){
            return bfrImport;
        }
    */
    public BufferedReader getBufReader(){
        return bfrImport;
    }
    public String send() throws IOException {
        /*URL u = new URL("http://sitsys.com.br/webservice/w4.php?query=insert%20into%20sitvirp(vicnpj,%20vixml,%20sr_deleted)%20values(%22"+ CurrentInfo.cnpjEmpresa+"%22,"+xml+",%20false)");
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.openConnect();*/

        //inputStream = connection.getInputStream();
        //openConnect();
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] by = new byte[1024];
        while((len = getInputStream().read(by)) != -1){
            bos.write(by, 0, len);
        }
        byte[] responseWebService = bos.toByteArray();
        bos.close();

        return new String(responseWebService);
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public BufferedReader getBfrImport() {
        return bfrImport;
    }

    public void setBfrImport(BufferedReader bfrImport) {
        this.bfrImport = bfrImport;
    }

    public java.net.URL getuImport() {
        return uImport;
    }

    public void setuImport(java.net.URL uImport) {
        this.uImport = uImport;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    public void disconnect() throws IOException {
        this.bfrImport.close();
        this.inputStream.close();
        this.connection.disconnect();
        this.uImport = null;
    }
}

