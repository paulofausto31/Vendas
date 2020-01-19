package vendas.telas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import persistencia.brl.EmpresaBRL;
import persistencia.brl.VendedorBRL;
import persistencia.dto.VendedorDTO;
import venda.util.Global;
import venda.util.Util;

public class Entrada extends Activity {

	List<String> listaCodigo;
	List<String> listaDescricao;
	String codEmpresa = "";
	Button btnEntrar;
	TextView txtVendedor;
	TextView lblEmpresa;
	Spinner cbxEmpresa;
	EmpresaBRL empBRL;
	VendedorBRL venBRL;
	private static final int PERMISSAO_GRAVACAO = 828;


	private void AbrePrincipal() {
		Intent principal = new Intent(this, Principal.class);
    	startActivity(principal);
	}

	private void AbreComunicacao() {
    	Intent comunicacao = new Intent(this, Comunicacao.class);
    	startActivity(comunicacao);       	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada);

		empBRL = new EmpresaBRL(getBaseContext());
        btnEntrar = (Button)findViewById(R.id.btnEntrar);
        txtVendedor = (TextView)findViewById(R.id.txtVendedorEntrada);
		lblEmpresa = (TextView)findViewById(R.id.lblEmpresa);
		cbxEmpresa = (Spinner)findViewById(R.id.cbxEmpresa);
		venBRL = new VendedorBRL(getBaseContext());

        btnEntrar.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (txtVendedor.getText().toString().length() == 0)
					Toast.makeText(getBaseContext(), "Informe um codigo de vendedor válido", Toast.LENGTH_SHORT).show();
				else if (txtVendedor.getText().toString().length() > 4)
					Toast.makeText(getBaseContext(), "Informe um codigo de vendedor com no máximo 4 digitos", Toast.LENGTH_SHORT).show();
				else{
			        Global.codVendedor = Util.FormataZeros(txtVendedor.getText().toString(), 4);

					if (empBRL.getTotalEmpresas() > 1 && Global.codEmpresa.equals("-1")){
						Toast.makeText(getBaseContext(), "Selecione uma empresa", Toast.LENGTH_LONG).show();
					}
					else if (venBRL.getVendedorEmpresa(Global.codEmpresa, Global.codVendedor)){
						AbrePrincipal();
					}else{
						Toast.makeText(getBaseContext(), "Este aparelho não esta preparado para este vendedor", Toast.LENGTH_LONG).show();
						AbreComunicacao();
					}
				}
			}
		});
	}

	public void VerificaPermissaoGravacao(){
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(this,
				android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
					android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


			} else {


				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
						PERMISSAO_GRAVACAO);

			}
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	    String permissions[], int[] grantResults) {
		switch (requestCode) {
			case PERMISSAO_GRAVACAO: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					// permission was granted, yay! Do the
					// contacts-related task you need to do.

				} else {

					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request
		}
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		VerificaPermissaoGravacao();
		txtVendedor.setText("");
		Global.codEmpresa = "";
		Global.tituloAplicacao = "";
		int totalEmpresas = empBRL.getTotalEmpresas();

		if (totalEmpresas < 1) {
			lblEmpresa.setVisibility(View.INVISIBLE);
			cbxEmpresa.setVisibility(View.INVISIBLE);
		}
		else if (totalEmpresas >= 1)
		{
			lblEmpresa.setVisibility(View.VISIBLE);
			cbxEmpresa.setVisibility(View.VISIBLE);
			listaDescricao = empBRL.getCombo("cnpj", "Fantasia", "Selecione");
			listaCodigo = empBRL.getCombo("codEmpresa", "-1");
			CarregaComboEmpresa();
		}

	}

	private void CarregaComboEmpresa() {
		ArrayAdapter<String> arrayAdapterEmpresa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDescricao);
		cbxEmpresa.setAdapter(arrayAdapterEmpresa);
		if (empBRL.getTotalEmpresas() > 1)
			cbxEmpresa.setSelection(0);
		else
			cbxEmpresa.setSelection(1);
		cbxEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
									   int posicao, long id) {
				Global.codEmpresa = listaCodigo.get(posicao);
				if (posicao > 0)
					Global.tituloAplicacao = "PalmVenda ".concat(listaDescricao.get(posicao).substring(15));
				VendedorDTO venDTO = venBRL.getAll();
				if (venDTO != null)
					txtVendedor.setText(venDTO.getCodigo().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
