package vendas.telas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.adapters.ItensPedidoAdapter;
import persistencia.adapters.RVItensPedidoAdapter;
import persistencia.adapters.RVUtilitarioPedidoAdapter;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;

public class PedidoItens extends Fragment {

	private RecyclerView recyclerView;
	private RVItensPedidoAdapter adapter;
	List<ItenPedidoDTO> lista;
	PedidoDTO pedDTO = new PedidoDTO();
	PedidoBRL pedBRL;
	ItenPedidoDTO itpDTO = new ItenPedidoDTO();
	ItenPedidoBRL itpBRL;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pedido_itens, container, false);
		recyclerView = view.findViewById(R.id.recycler_view_pedido_itens);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		pedBRL = new PedidoBRL(getContext());
		itpBRL = new ItenPedidoBRL(getContext());

		return view;
	}
/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(Global.tituloAplicacao);

		pedBRL = new PedidoBRL(getBaseContext());
		itpBRL = new ItenPedidoBRL(getBaseContext());
		
		registerForContextMenu(getListView());		
	}
*/
	@Override
	public void onResume() {
		super.onResume();
		pedDTO = venda.util.Global.pedidoGlobalDTO;
		ListaItenPedido();
	}

	private void ListaItenPedido(){
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());
		lista = itpBRL.getByCodPedido(pedDTO.getId());
		adapter = new RVItensPedidoAdapter(getContext(), lista);
		recyclerView.setAdapter(adapter);
	}

	/*
	protected void onListItemClick(ListView l, View v, int position, long id){
		 ItenPedidoDTO itpDTO = (ItenPedidoDTO) l.getAdapter().getItem(position);
		 ProdutoBRL proBRL = new ProdutoBRL(getBaseContext());
		 ProdutoDTO proDTO = proBRL.getByCodProduto(itpDTO.getCodProduto());
		 
		 Double total = itpDTO.getQuantidade() * itpDTO.getPreco();
		 Toast.makeText(getBaseContext(), "Codigo: " + itpDTO.getCodProduto().toString() + "\n"
				 + "Descrição: " + proDTO.getDescricao() + "\n"
				 + "Quantidade: " + itpDTO.getQuantidade().toString() + "\n"
				 + "Preço: " + itpDTO.getPreco().toString() + "\n"
				 + "total: " + total.toString(), Toast.LENGTH_LONG).show();
	 }
*/
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		pedDTO.setHoraPedidoFim(venda.util.Util.getTime());
		pedBRL.Update(pedDTO);
		venda.util.Global.pedidoGlobalDTO = pedDTO;
	}

/*
	@Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		 AdapterContextMenuInfo info;
		 ListView l;

		 if (venda.util.Global.pedidoGlobalDTO.getBaixado() == 1){
			 Toast.makeText(getBaseContext(), "Este pedido ja foi baixado e não pode ser manipulado", Toast.LENGTH_SHORT).show();
			 return false;
		 }

		 switch (menu.getItemId()){
		 
		 case MENU_EDITAR:  
			 l = getListView();
			 info = (AdapterContextMenuInfo) menu.getMenuInfo();
			 itpDTO = (ItenPedidoDTO)l.getAdapter().getItem(info.position);
			 venda.util.Global.itemPedidoGlobalDTO = itpDTO;

			 startActivity(new Intent(getBaseContext(), PedidoItemNovo.class).putExtra("codProduto", itpDTO.getCodProduto()));
			 
			 return true;
		 case MENU_EXCLUIR:
			 l = getListView();
			 info = (AdapterContextMenuInfo) menu.getMenuInfo();
			 itpDTO = (ItenPedidoDTO)l.getAdapter().getItem(info.position);

			 itpBRL = new ItenPedidoBRL(getBaseContext());

			 AlertDialog.Builder confirmacao = new AlertDialog.Builder(this);
			 
			 confirmacao.setTitle("Excluir Item");
			 confirmacao.setMessage("Confirma Exclusão deste Item ?");
			 confirmacao.setCancelable(false);
			 
			 confirmacao.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 itpBRL.delete(itpDTO);
					 ListaItenPedido();
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
	*/
}
