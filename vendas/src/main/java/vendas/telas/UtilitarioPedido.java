package vendas.telas;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import persistencia.adapters.UtilitarioPedidoAdapter;
import persistencia.brl.PedidoBRL;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class UtilitarioPedido extends ListActivity {

	private static final int MENU_ABREPEDIDO = 1;
	PedidoDTO pedDTO = new PedidoDTO();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(Global.tituloAplicacao);

		registerForContextMenu(getListView());
	}


	@Override
	protected void onResume() {
		super.onResume();
		PedidoBRL brl = new PedidoBRL(getBaseContext());
		setListAdapter(new UtilitarioPedidoAdapter(getBaseContext(), brl.getAllPedEnviado()));
	}

	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Sub Menu Utilitário");
        menu.add(0,MENU_ABREPEDIDO,0,"Abre Pedido"); 
    }  
	 
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		 ListView l = getListView();
		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
		 if (info != null)
			 pedDTO = (PedidoDTO)l.getAdapter().getItem(info.position);

		 switch (menu.getItemId()){
		 
		 case MENU_ABREPEDIDO: // mensagem
			 PedidoBRL pedBRL = new PedidoBRL(getBaseContext());
			 pedBRL.AbrePedidoBaixado(pedDTO.getId());
 			 setListAdapter(new UtilitarioPedidoAdapter(getBaseContext(), pedBRL.getAllPedEnviado()));
			 return true;
		 }
		 return false;
	 }
	 

}
