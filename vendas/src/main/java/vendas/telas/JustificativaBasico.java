package vendas.telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import persistencia.brl.ClienteBRL;
import persistencia.brl.ClienteNaoPositivadoBRL;
import persistencia.brl.JustificativaPositivacaoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.ClienteNaoPositivadoDTO;
import venda.util.Global;
import venda.util.Util;

public class JustificativaBasico extends Activity {

	private static final int MENU_HORASAIDA = 1;

	String codJustificativa = ""; 
	ClienteDTO cliDTO = new ClienteDTO();
	ClienteNaoPositivadoBRL cnpBRL;
	List<String> listaDescricao;
	List<String> listaCodigo;
	//... Componentes
	EditText txtNome;
	Spinner cbxJustificativa;
	EditText txtObs;
	Button btnSalvar;
	
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.justificativa_basico);
		this.setTitle(Global.tituloAplicacao);

		JustificativaPositivacaoBRL jusBRL = new JustificativaPositivacaoBRL(getBaseContext());
		cnpBRL = new ClienteNaoPositivadoBRL(getBaseContext());			
		
		//... Recupera parametros
	    Intent it = getIntent();
	    ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
	    int idCliente = it.getIntExtra("idCliente", 1);
	    cliDTO = cliBRL.getById(idCliente);

	    listaDescricao = jusBRL.getCombo("descricao", "Selecione");
		listaCodigo = jusBRL.getCombo("codJustPos", "-1");

		//... Componentes
		txtNome = (EditText)findViewById(R.id.txtNome);
		cbxJustificativa = (Spinner)findViewById(R.id.cbxJustificativa);
		txtObs = (EditText)findViewById(R.id.txtObs);
		btnSalvar = (Button)findViewById(R.id.btnGravar);
		
	    txtNome.setText(cliDTO.getNome());
		
		CarregaComboJustificativa();
		btnSalvar.setOnClickListener(new Button.OnClickListener() {			
			@Override
			public void onClick(View v) { btnGravar_click(); }
		});			
	}
	
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu){
		// Menu
		MenuItem MenuHoraSaida = menu.add(0, MENU_HORASAIDA, 0, "Hora Saída");

		return true; 
	 }
	 
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		 switch (menu.getItemId()){
		 
		 case MENU_HORASAIDA:
     		 List<ClienteNaoPositivadoDTO> listCNP = cnpBRL.getByCodCliente(cliDTO.getCodCliente());
     		 for (ClienteNaoPositivadoDTO cnpDTO : listCNP) {
    			 cnpDTO.setDataFim(venda.util.Util.getDate());
    			 cnpDTO.setHoraFim(venda.util.Util.getTime());
    			 cnpBRL.Update(cnpDTO);
			}
			 
			 return true;
		 }
		 return false;
	 }
	
	private void CarregaComboJustificativa() {
		
		ArrayAdapter<String> arrayAdapterJustificativa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDescricao);
		cbxJustificativa.setAdapter(arrayAdapterJustificativa);	
		
		cbxJustificativa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int posicao, long id) {
				codJustificativa = listaCodigo.get(posicao);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void btnGravar_click(){
		if (ValidaCampos()){
			ClienteNaoPositivadoDTO cnpDTO = new ClienteNaoPositivadoDTO();
			cnpDTO.setCodCliente(cliDTO.getCodCliente());
			cnpDTO.setCodJustificativa(Integer.parseInt(codJustificativa));
			cnpDTO.setData(Util.getDate());
			cnpDTO.setHora(Util.getTime());
			cnpDTO.setDataFim(Util.getDate());
			cnpDTO.setHoraFim(Util.getTime());
			cnpDTO.setObs(txtObs.getText().toString());
			cnpDTO.setBaixado(0);
			cnpBRL.InsereClienteNaoPositivado(getBaseContext(), cnpDTO);
			Toast.makeText(getBaseContext(), "Justificativa incluida com sucesso", Toast.LENGTH_LONG).show();
		}
	}

	private boolean ValidaCampos() {
		if (codJustificativa.equals("-1")){
			Toast.makeText(getBaseContext(), "Selecione uma justificativa", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
}
