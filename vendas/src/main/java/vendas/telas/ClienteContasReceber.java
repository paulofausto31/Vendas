package vendas.telas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;

import persistencia.adapters.ContasReceberAdapter;
import persistencia.brl.ContaReceberBRL;
import persistencia.brl.FormaPgtoBRL;
import persistencia.dto.ContaReceberDTO;
import venda.util.Global;

public class ClienteContasReceber extends ListActivity {

	FormaPgtoBRL PgtoBRL;

	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		this.setTitle(Global.tituloAplicacao);
		PgtoBRL = new FormaPgtoBRL(getBaseContext());
		ContaReceberBRL brl = new ContaReceberBRL(getBaseContext());

		Intent it = getIntent();
	    int codCliente = it.getIntExtra("codCliente", 1);
		setListAdapter(new ContasReceberAdapter(getBaseContext(), brl.getByCliente(codCliente)));
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
		 ContaReceberDTO dto = (ContaReceberDTO) l.getAdapter().getItem(position);
		 
		 try {
			Toast.makeText(getBaseContext(), "Documento: " + dto.getDocumento() + "\n"
					 + "Data Vencimento: " + dto.getDataVencimento() + "\n"
					 + "Data Promessa: " + dto.getDataPromessa() + "\n"
					 + "Forma Pgto: " + dto.getFormaPgto() + "\n"
					 + "Valor: " + dto.getValor().toString() + "\n"
					 + "Juros:" + PgtoBRL.getJurosCalculado(dto.getFormaPgto(), dto.getValor(), dto.getDataVencimento()) + "\n"
					 + "Multa:" + PgtoBRL.getMultaCalculado(dto.getFormaPgto(), dto.getValor()) + "\n"
			         + "Vl. Corrigido " + PgtoBRL.getValorAtualizado(dto.getFormaPgto(), dto.getValor(), dto.getDataVencimento()).toString(), Toast.LENGTH_LONG).show();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
