package vendas.telas;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import persistencia.dao.ClienteDAO;
import persistencia.dto.PedidoDTO;
import persistencia.dto.SitLGPSDTO;

import vendas.telas.R;
import vendas.telas.R.id;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import persistencia.constantes.*;

//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Request.Method;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import venda.util.VolleySingleton;

public class Localizacao implements GoogleApiClient.ConnectionCallbacks, 
	GoogleApiClient.OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener {

	private static final String TAG = "Coordenadas";
	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;
	private Context ctx;
	private Handler handler = new Handler();


    public Localizacao(){}
    
    public Localizacao(Context ctx)
    {
    	this.ctx = ctx;
		callConection();		
    }

	public String getLatitude(){
		Log.i(TAG, "Dentro do getLatitude");

		Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		
		if (l != null)
			return Double.toString(l.getLatitude());
		else
			return "";
		
	}
	
	public String getLongitude(){

		Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		
		if (l != null)
			return Double.toString(l.getLongitude());
		else
			return "";
		
	}

	private synchronized void callConection(){
		mGoogleApiClient = new GoogleApiClient.Builder(this.ctx)
			.addOnConnectionFailedListener(this)
			.addConnectionCallbacks(this)
			.addApi(LocationServices.API)
			.build();
		mGoogleApiClient.connect();
	}
	
	private void iniLocationRequest(){
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(300000);
		mLocationRequest.setFastestInterval(300000);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}
	
	private void startLocationUpdate(){
		iniLocationRequest();
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, Localizacao.this);
	}
	
	private void stopLocationUpdate(){
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, Localizacao.this);
	}

	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onConnectionFailed(" + arg0 + ")");		
}

	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		//startLocationUpdate();
	}

	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onConnectionSuspended(" + arg0 + ")");
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
/*		new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					Gson gson = new Gson();
					
					*//** Web Service **//*
					SoapObject request = new SoapObject(Constants.NAMESPACE, Constants.INSERIR_LOCALIZACAO_METHOD);
					
					*//** Criando objeto String da classe Pessoa *//*
					SitLGPSDTO sitlgps = new SitLGPSDTO((long) 1, "1", "1");
					*//**
					 * Cada property deve ser adicionada com identificação seguindo o padrão
					 * de nome "arg0", "arg1", "arg2"...  
					 *//*
					request.addProperty("arg0", gson.toJson(sitlgps));
					
					*//** Instruções responsáveis por fazer a requisito ao Web Service via SOAP *//*
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(Constants.WSDL_URL);

					try {
						androidHttpTransport.call(Constants.INSERIR_LOCALIZACAO_ACTION, envelope);

						SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

						SitLGPSDTO sitRecebida = gson.fromJson(resultsRequestSOAP.toString(), SitLGPSDTO.class);
						
						if(sitRecebida != null)
							Toast.makeText(ctx, "ok", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						Toast.makeText(ctx, "Erro de conexao com Web Service", Toast.LENGTH_LONG).show();
						for (StackTraceElement ste : e.getStackTrace()) {
							Log.e("ERRO", ste.toString());
						}
					}
				}
			});
		}
		}).start();
*/
		final Gson gson = new Gson();
		
//		RequestQueue queue = VolleySingleton.getInstance(ctx).getRequestQueue();
//
//		String URL = "http://guidogabriel.16mb.com/HistoricoTituloJson.php/";//Constants.WSDL_URL;
//		final SitLGPSDTO sitlgps = new SitLGPSDTO((long) 1, Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
//		Log.i(TAG, "Antes da StringRequest porra");
//        StringRequest request = new StringRequest(
//                Method.POST,
//                URL,
//                new Response.Listener<String>() {
//                	@Override
//                	public void onResponse(String response){
//                		Log.i(TAG, "Localizacao inserida com sucesso porra");
//
//                	}
//				},
//				new Response.ErrorListener() {
//					@Override
//					public void onErrorResponse(VolleyError error){
//						Log.i(TAG, "Localizacao não inserida porra");
//
//					}
//				}){
//        			@Override
//                    public Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("localizacao", gson.toJson(sitlgps));
//                        params.put("inserirLocalizacao", gson.toJson(sitlgps));
//                        return params;
//                    }
//        	      };
//
//        	      queue.add(request);


		Toast.makeText(this.ctx, "Latitude: " + location.getLatitude() + "\n"
				 + "Longitude: " + location.getLongitude() + "\n"
				 + "Bearing: " + location.getBearing() + "\n"
				 + "Altitude: " + location.getAltitude() + "\n"
				 + "Speed: " + location.getSpeed() + "\n"
				 + "Provider: " + location.getProvider() + "\n"
				 + "Accuracy: " + location.getAccuracy() + "\n"
				 + "DataHora: " + java.text.DateFormat.getDateTimeInstance().format(new Date()), Toast.LENGTH_LONG).show();
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}	
	
}
