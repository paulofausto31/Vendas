package vendas.ws;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vendas.telas.R;

import com.google.gson.JsonObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GetLGPS extends Activity{
	TextView txtLoClie;
	TextView txtLoData;
	TextView txtLoHora;
	TextView txtLoVend;
	TextView txtLoGPS1;
	TextView txtLoGPS2;
	
	Button BtnRecebeJson;
	
    //URL to get JSON Array
    private static String url = "http://http://pfsoft.esy.es/SitWS/ConsultaGPS.php";
 
    //JSON Node Names
    private static final String TAG_GPS = "GPS";
    private static final String TAG_LOCLIE = "loclie";
    private static final String TAG_LODATA = "lodata";
    private static final String TAG_LOHORA = "lohora";
    private static final String TAG_LOVEND = "lovend";
    private static final String TAG_SR_RECNO = "sr_recno";
    private static final String TAG_LOGPS1 = "logps1";
    private static final String TAG_LOGPS2 = "logps2";
 
    JSONArray user = null;
 
	

@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitlgps_lista);
		BtnRecebeJson = (Button)findViewById(R.id.btnRecebeJson);
		BtnRecebeJson.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                 new JSONParse().execute();
 
            }
        });
	}



private class JSONParse extends AsyncTask<String, String, JSONObject> {

	private ProgressDialog pDialog;

	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        txtLoClie = (TextView)findViewById(R.id.txtLoClie);
        txtLoData = (TextView)findViewById(R.id.txtLoData);
        txtLoHora = (TextView)findViewById(R.id.txtLoHora);
        txtLoVend = (TextView)findViewById(R.id.txtLoVend);
        txtLoGPS1 = (TextView)findViewById(R.id.txtLoGPS1);
        txtLoGPS2 = (TextView)findViewById(R.id.txtLoGPS2);
        pDialog = new ProgressDialog(GetLGPS.this);
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

    }
    
    @Override
	protected JSONObject doInBackground(String... params) {
    	JSONParser jParser = new JSONParser();
        
        // Getting JSON from URL
        JSONObject json = jParser.getJSONFromUrl(url);
        
        return json;	
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        pDialog.dismiss();
        try {
               // Getting JSON Array
               user = json.getJSONArray(TAG_GPS);
               JSONObject c = user.getJSONObject(0);

               // Storing  JSON item in a Variable
               String loClie = c.getString(TAG_LOCLIE);
               String loData = c.getString(TAG_LODATA);
               String loHora = c.getString(TAG_LOHORA);
               String loVend = c.getString(TAG_LOVEND);
               String loGps1 = c.getString(TAG_LOGPS1);
               String loGps2 = c.getString(TAG_LOGPS2);

               //Set JSON Data in TextView
               txtLoClie.setText(loClie);
               txtLoData.setText(loData);
               txtLoHora.setText(loHora);
               txtLoVend.setText(loVend);
               txtLoGPS1.setText(loGps1);
               txtLoGPS2.setText(loGps2);

       } catch (JSONException e) {
           e.printStackTrace();
       }

    }
}
}
