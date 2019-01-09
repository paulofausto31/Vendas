package vendas.telas;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import persistencia.adapters.PedidoSelecaoAdapter;
import persistencia.brl.PedidoBRL;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class UtilitarioPedidoPendente extends ListActivity {

    private static final int MENU_ABREPEDIDO = 1;
    private static final int MENU_FECHAPEDIDO = 2;

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
        setListAdapter(new PedidoSelecaoAdapter(getBaseContext(), brl.getAllPedAberto()));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Sub Menu Utilitário");
        menu.add(0,MENU_ABREPEDIDO,0,"Abre Pedido");
        menu.add(0,MENU_FECHAPEDIDO,0,"Fecha Pedido");
    }

    @Override
    public boolean onMenuItemSelected(int featureID, MenuItem menu){

        ListView l = getListView();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuInfo();
        PedidoBRL pedBRL = new PedidoBRL(getBaseContext());
        if (info != null)
            pedDTO = (PedidoDTO)l.getAdapter().getItem(info.position);

        switch (menu.getItemId()){

            case MENU_ABREPEDIDO: // mensagem
                pedBRL.AbrePedidoPendente(pedDTO.getId());
                setListAdapter(new PedidoSelecaoAdapter(getBaseContext(), pedBRL.getAllPedAberto()));
                return true;
            case MENU_FECHAPEDIDO: // mensagem
                pedBRL.FechaPedidoPendente(pedDTO.getId());
                setListAdapter(new PedidoSelecaoAdapter(getBaseContext(), pedBRL.getAllPedAberto()));
                return true;
        }
        return false;
    }


}
