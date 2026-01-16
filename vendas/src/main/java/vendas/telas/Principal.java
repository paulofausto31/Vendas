package vendas.telas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.ParseException;

import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import venda.util.Global;

public class Principal extends AppCompatActivity {

	private String chave;
    private TextView txtTotalVendas, txtQtdPedidos, txtPercentual;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // Certifique-se que o XML novo está com este nome
        this.setTitle(Global.tituloAplicacao);

        // Ligando os cliques dos novos Cards às funções que você já criou

        findViewById(R.id.cardCliente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnCliente_click(); }
        });

        findViewById(R.id.cardProduto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnProduto_click(); }
        });

        findViewById(R.id.cardConsulta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnConsulta_click(); }
        });

        findViewById(R.id.cardComunicacao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnComunicacao_click(); }
        });

        findViewById(R.id.cardUtilitario).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnUtilitario_click(); }
        });

        findViewById(R.id.cardConfiguracao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btnConfiguracao_click();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        txtTotalVendas = findViewById(R.id.txtTotalVendas);
        txtQtdPedidos = findViewById(R.id.txtQtdPedidos);
        txtPercentual = findViewById(R.id.txtPercentual);

        atualizarDashboard();
    }

    private void atualizarDashboard() {
        // Exemplo de dados reais vindo do banco

        DecimalFormat formatador = new DecimalFormat("##,###0.00");
        ItenPedidoBRL itpBRL = new ItenPedidoBRL(this);
        Double totalVendas = itpBRL.getSumTotalAberto();

        ClienteBRL cliBRL = new ClienteBRL(this);
        Double totalClientes = Double.parseDouble(cliBRL.getTotalClientes().toString());
        PedidoBRL pedBRL = new PedidoBRL(this);
        Double totalPositivacao = Double.parseDouble(pedBRL.getTotalPositivacao().toString());
        Double percentualPositivacao = ((totalPositivacao / totalClientes) * 100);

        txtTotalVendas.setText(String.format("R$ %.2f", totalVendas));
        txtQtdPedidos.setText(String.valueOf(totalPositivacao));
        txtPercentual.setText(String.format("%.1f%%", percentualPositivacao));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);

		return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_clientes:
				Intent cliente = new Intent(this, RVClienteLista.class);
				startActivity(cliente);
				return true;
			case R.id.menu_produtos:
				Intent produto = new Intent(this, RVProdutoLista.class).putExtra("paramProduto", false);
				startActivity(produto);
				return true;
			case R.id.menu_consultas:
				Intent consulta = new Intent(this, ConsultaTabContainer.class);
				startActivity(consulta);
				return true;
			case R.id.menu_comunicacao:
				Intent comunicacao = new Intent(this, Comunicacao.class);
				startActivity(comunicacao);
				return true;
			case R.id.menu_configuracao:
				Intent configuracao = new Intent(this, ConfiguracaoTabContainer.class);
				startActivity(configuracao);
				return true;
			case R.id.menu_utilitario:
				Intent utilitario = new Intent(this, UtilitarioTabContainer.class);
				startActivity(utilitario);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

    private void btnUtilitario_click(){
    	Intent utilitario = new Intent(this, UtilitarioTabContainer.class);
    	startActivity(utilitario);
    }
    private void btnCliente_click(){
    	Intent cliente = new Intent(this, RVClienteLista.class);
    	startActivity(cliente);
    }
    private void btnConsulta_click(){
    	Intent consulta = new Intent(this, ConsultaTabContainer.class);
    	startActivity(consulta);
    }
    private void btnProduto_click(){
    	Intent produto = new Intent(this, RVProdutoLista.class).putExtra("paramProduto", false);
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