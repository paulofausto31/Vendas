package vendas.telas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import persistencia.brl.ClienteBRL;
import persistencia.dto.ClienteDTO;
import venda.util.Global;

public class ClienteComplemento extends Activity {
	
	private static final int MENU_LIGAR = 1;
	private static final int MENU_MAPA = 2;

    EditText txtNome;
    EditText txtEndereco;
    EditText txtUltimaCompra;
    EditText txtCPFCNPJ;
    EditText txtAvencer;
    EditText txtVencido;
    EditText txtTelefone;
    EditText txtFormaPgto;
    EditText txtBairro;
    EditText txtCidade;

    @Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.cliente_complemento);
		this.setTitle(Global.tituloAplicacao);
		//... recupera componentes de tela
	    txtNome = (EditText) findViewById(R.id.txtNome);
	    txtEndereco = (EditText) findViewById(R.id.txtEndereco);
	    txtUltimaCompra = (EditText) findViewById(R.id.txtUltimaCompra);
	    txtCPFCNPJ = (EditText) findViewById(R.id.txtCPFCNPJ);
	    txtAvencer = (EditText) findViewById(R.id.txtAvencer);
	    txtVencido = (EditText) findViewById(R.id.txtVencido);
	    txtTelefone = (EditText) findViewById(R.id.txtTelefone);
	    txtFormaPgto = (EditText) findViewById(R.id.txtFormaPgto);
	    txtBairro = (EditText) findViewById(R.id.txtBairro);
	    txtCidade = (EditText) findViewById(R.id.txtCidade);
	    
	    Intent it = getIntent();
	    ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
	    int extra = it.getIntExtra("idCliente", 1);
	    ClienteDTO cliDTO = cliBRL.getById(extra);
	    
	    txtNome.setText(cliDTO.getNome());
	    txtEndereco.setText(cliDTO.getEndereco());
	    txtUltimaCompra.setText(cliDTO.getDataUltimaCompra());
	    txtCPFCNPJ.setText(cliDTO.getCpfCnpj());
	    txtAvencer.setText(cliDTO.getValorVencer().toString());
	    txtVencido.setText(cliDTO.getValorAtraso().toString());
	    txtTelefone.setText(cliDTO.getTelefone());
	    txtFormaPgto.setText(cliDTO.getFormaPgto());
	    txtBairro.setText(cliDTO.getBairro());
	    txtCidade.setText(cliDTO.getCidade());
	}

	@Override
	 public boolean onCreateOptionsMenu(Menu menu){
		// Menu
		MenuItem MenuLigar = menu.add(0, MENU_LIGAR, 0, "Ligar");
		MenuItem MenuMapa = menu.add(0, MENU_MAPA, 0, "Localizar no Mapa");

		return true; 
	 }
	 
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		 switch (menu.getItemId()){
		 
		 case MENU_LIGAR:
			 if (txtTelefone.getText().toString().trim().length() < 8)
				 Toast.makeText(getBaseContext(), "Número de telefone incompleto", Toast.LENGTH_SHORT).show();
			 else{
				 Uri u = Uri.parse ("tel: ".concat(txtTelefone.getText().toString().trim()));
				 
				 Intent chamada = new Intent(Intent.ACTION_CALL, u);
				 startActivity(chamada);			 }
	    	return true;
		 case MENU_MAPA:
			 
	    	return true;
		 }
		 return false;
	 }

}
