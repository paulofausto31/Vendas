package vendas.telas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

import venda.util.Global;

public class Principal extends Activity {

	public static final int Sincronizar = 1;
	public static final int Configuracoes = 2;
	private String chave;
	private static final String TAG = "Coordenadas";		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"Bora principal porra");
        setContentView(R.layout.main);
		this.setTitle(Global.tituloAplicacao);
        
        //... Localicação
        //Localizacao loc = new Localizacao(getBaseContext());
/*        Boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);
        if (alarmeAtivo)
        {
	        Intent intent = new Intent("ALARME_DISPARADO");
	        PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);
	        
	        Calendar c = Calendar.getInstance();
	        c.setTimeInMillis(System.currentTimeMillis());
	        c.add(Calendar.SECOND, 3);
	        
	        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
	        alarm.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),100000, p); //300000
        }else
        	Log.i("Logs", "Alarme j� ativo");
*/        //... btnCliente
        
        
        Button btnCliente = (Button)findViewById(R.id.btnCliente);
        btnCliente.setOnClickListener(new Button.OnClickListener() {			
			public void onClick(View v) { btnCliente_click();  }
		});
        //... btnConsulta
        Button btnConsulta = (Button)findViewById(R.id.btnConsulta);
        btnConsulta.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) { btnConsulta_click(); }
		});
        //... btnProduto
        Button btnProduto = (Button)findViewById(R.id.btnProduto);
        btnProduto.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) { btnProduto_click(); }
		});          
        //... btnComunicacao
        Button btnComunicacao = (Button)findViewById(R.id.btnComunicacao);
        btnComunicacao.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) { btnComunicacao_click(); }
		});          
        //... btnConfiguracao
        Button btnConfiguracao = (Button)findViewById(R.id.btnConfiguracao);
        btnConfiguracao.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) { try {
				btnConfiguracao_click();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
		});          
        //... btnUtilitario
        Button btnUtilitario = (Button)findViewById(R.id.btnUtilitario);
        btnUtilitario.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) { btnUtilitario_click(); }
		});          
    }    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);    	
    	//...
    	menu.add(0, Sincronizar, 0, "Sincronizar");
    	menu.add(0, Configuracoes, 0, "Configurações");

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
    	//...
    	return true;
    }    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch (item.getItemId()) {
			case Sincronizar:{
				
			}break;
			case Configuracoes:{
				Intent configuracao = new Intent(this, ConfiguracaoTabContainer.class);
				startActivity(configuracao);
			}break;
    	}
    	return false;
    }
    
    private void btnUtilitario_click(){
    	Intent utilitario = new Intent(this, UtilitarioTabContainer.class);
    	startActivity(utilitario);
    }
    private void btnCliente_click(){
    	Intent cliente = new Intent(this, ClienteLista.class);
    	startActivity(cliente);
    }
    private void btnConsulta_click(){
    	Intent consulta = new Intent(this, ConsultaTabContainer.class);
    	startActivity(consulta);
    }
    private void btnProduto_click(){
    	Intent produto = new Intent(this, ProdutoLista.class).putExtra("paramProduto", false);
    	startActivity(produto);       	
    }    
    private void btnComunicacao_click(){
    	Intent comunicacao = new Intent(this, Comunicacao.class);
    	startActivity(comunicacao);       	
    }    
    private void btnConfiguracao_click() throws ParseException{
//    	RecuperaChave();
//    	if (chave.length() > 0 && chave == Util.montaChave(dataHora.dataDia())){
        	Intent configuracao = new Intent(this, ConfiguracaoTabContainer.class);
        	startActivity(configuracao);       	    		
//    	}else{
//   		 	Toast.makeText(getBaseContext(), "Chave incorreta ", Toast.LENGTH_LONG).show();
//    	}    		
    }    

    private void RecuperaChave(){
		 AlertDialog.Builder pesquisa = new AlertDialog.Builder(this);
		 
		 pesquisa.setTitle("Recupera Chave");
		 pesquisa.setMessage("Informe a chave");
		 pesquisa.setCancelable(false);
		 
		 final EditText input = new EditText(this);
		 pesquisa.setView(input);
		 
		 pesquisa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String value = input.getText().toString();	
				if (value.length() < 1)
					Toast.makeText(getBaseContext(), "Informe a chave", Toast.LENGTH_LONG).show();
				else{
					chave = value;
				}
			}
		});
		 
		 pesquisa.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		 
		 AlertDialog alertDialog = pesquisa.create();

		 alertDialog.show();


    }

}