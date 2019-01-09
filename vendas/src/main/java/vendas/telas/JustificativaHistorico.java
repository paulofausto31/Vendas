package vendas.telas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import persistencia.adapters.ClienteNaoPositivadoAdapter;
import persistencia.brl.ClienteNaoPositivadoBRL;
import persistencia.brl.JustificativaPositivacaoBRL;
import persistencia.dto.ClienteNaoPositivadoDTO;
import persistencia.dto.JustificativaPositivacaoDTO;
import venda.util.Global;

public class JustificativaHistorico extends ListActivity {
	private static final int MENU_EXCLUIR = 1;

	ClienteNaoPositivadoBRL cnpBRL;
	ClienteNaoPositivadoDTO cnpDTO;
	int codCliente;
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		this.setTitle(Global.tituloAplicacao);

		cnpBRL = new ClienteNaoPositivadoBRL(getBaseContext());

		Intent it = getIntent();
	    codCliente = it.getIntExtra("codCliente", 1);
		registerForContextMenu(getListView());			    
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
		ClienteNaoPositivadoDTO cnpdto = (ClienteNaoPositivadoDTO) l.getAdapter().getItem(position);
		JustificativaPositivacaoDTO jusDTO = new JustificativaPositivacaoDTO();
		JustificativaPositivacaoBRL jusBRL = new JustificativaPositivacaoBRL(getBaseContext());
		jusDTO = jusBRL.getByCodJustificativa(cnpdto.getCodJustificativa()) ;
		 
		 Toast.makeText(getBaseContext(), "Data: " + cnpdto.getData() + "\n"
				 + "Hora: " + cnpdto.getHora() + "\n"
				 + "Justificativa: " + jusDTO.getDescricao() + "\n"
				 + "Obs: " + cnpdto.getObs() + "\n", Toast.LENGTH_LONG).show();
	 }

	@Override
	protected void onResume() {
		super.onResume();
		setListAdapter(new ClienteNaoPositivadoAdapter(getBaseContext(), cnpBRL.getByCodCliente(codCliente)));
	}
	
	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Sub Menu Historico");
        menu.add(0,MENU_EXCLUIR,0,"Excluir Justificativa"); 
    }  
	
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		 ListView l = getListView();
		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
		 cnpDTO = (ClienteNaoPositivadoDTO)l.getAdapter().getItem(info.position);
		 if (cnpDTO.getBaixado() == 1){
			 Toast.makeText(getBaseContext(), "Esta justificativa ja foi baixada e não pode ser excluida", Toast.LENGTH_SHORT).show();
			 return false;
		 }
		
		 switch (menu.getItemId()){
		 
		 case MENU_EXCLUIR:
			 AlertDialog.Builder confirmacao = new AlertDialog.Builder(this);
			 
			 confirmacao.setTitle("Excluir Justificativa");
			 confirmacao.setMessage("Confirma Exclusão desta Justificativa ?");
			 confirmacao.setCancelable(false);
			 
			 confirmacao.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ClienteNaoPositivadoBRL cnpBRL = new ClienteNaoPositivadoBRL(getBaseContext());
					cnpBRL.delete(cnpDTO);
					RetornaTabPedidoBasico();
				}
			});
			 
			 confirmacao.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			 
			 AlertDialog alertDialog = confirmacao.create();

			 alertDialog.show();
			 
			 
			 return true;
		 }
		 return false;
	 }

	private void RetornaTabPedidoBasico() {
	    TabActivity tabs = (TabActivity) getParent();
	    tabs.getTabHost().setCurrentTab(0); 			 
	}

	
}
