package vendas.telas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import persistencia.adapters.produtoAdapter;
import persistencia.brl.PrecoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.PrecoDTO;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;

public class ProdutoLista extends ListActivity {
	
	private static int MENU_FORNECEDOR = 1;
	private static int MENU_GRUPO = 2;
	private static int MENU_TODOS = 3;
	private static int MENU_PESQUISA = 4;
	private static int MENU_SELECIONAR = 5;
	private static int RETORNO_FORNECEDOR = 1;
	private static int RETORNO_GRUPO = 2;
	PrecoBRL preBRL;
	
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		this.setTitle(Global.tituloAplicacao);


		Intent it = getIntent();
	    preBRL = new PrecoBRL(getBaseContext());
	    Boolean param = it.getBooleanExtra("paramProduto", true);

	    if (param){
			registerForContextMenu(getListView());
			ProdutoBRL brl = new ProdutoBRL(getBaseContext());
			setListAdapter(new produtoAdapter(getBaseContext(), brl.getByDescricao(Global.prodPesquisa)));
	    }

	}
	
	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Sub Menu Produto");
        menu.add(0,MENU_SELECIONAR,0,"Selecionar"); 
    }  
	
	 protected void onListItemClick(ListView l, View v, int position, long id){
		 ProdutoDTO dto = (ProdutoDTO) l.getAdapter().getItem(position);
		 List<PrecoDTO> listaPreco = preBRL.getByProduto(dto.getCodProduto());

		 String longMessage = "Codigo: " + dto.getCodProduto().toString() + "\n"
				 + "Descrição: " + dto.getDescricao() + "\n"
				 + "Unidade: " + dto.getUnidade() + "\n"
				 + "Estoque: " + dto.getEstoque().toString() + "\n"
				 + "Preço: " + listaPreco.get(0).getPreco().toString();

		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setMessage(longMessage)
				 .setTitle("Mensagem")
				 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int id) {
						 // Ação ao clicar no botão OK
					 }
				 });
		 AlertDialog dialog = builder.create();
		 dialog.show();
	 }
	 
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu){
		MenuItem MenuFiltroFabricante = menu.add(0, MENU_FORNECEDOR, 0, "Filtro Fornecedor");
		MenuItem MenuFiltroLinha = menu.add(0, MENU_GRUPO, 1, "Filtro Grupo");
		MenuItem MenuFiltroTodos = menu.add(0, MENU_TODOS, 2, "Todos");
		MenuItem MenuPesquisa = menu.add(0, MENU_PESQUISA, 3, "Pesquisa");
		return true; 
	 }
	 
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){

		 if (menu.getItemId() == MENU_SELECIONAR){
			 	ListView l = getListView();
			 	AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
			 	ProdutoDTO proDTO = (ProdutoDTO) l.getAdapter().getItem(info.position);
			 	setResult(RESULT_OK, new Intent().putExtra("codProduto", proDTO.getCodProduto().toString()));
			 	finish();
		 }
		 else if (menu.getItemId() == MENU_FORNECEDOR){
		    	startActivityForResult(new Intent(getBaseContext(), FiltroFornecedor.class), RETORNO_FORNECEDOR);
		 }
		 else if (menu.getItemId() == MENU_GRUPO){
		    	startActivityForResult(new Intent(getBaseContext(), FiltroGrupo.class), RETORNO_GRUPO);
		 }
		 else if (menu.getItemId() == MENU_TODOS){
			 	venda.util.Global.prodPesquisa = null;
				ProdutoBRL brl = new ProdutoBRL(getBaseContext());
				setListAdapter(new produtoAdapter(getBaseContext(), brl.getAllOrdenado()));	
		 }
		 else if (menu.getItemId() == MENU_PESQUISA){
	 
			 AlertDialog.Builder pesquisa = new AlertDialog.Builder(this);
			 
			 pesquisa.setTitle("Pesquisa produto");
			 pesquisa.setMessage("Informe o nome do produto");
			 pesquisa.setCancelable(false);
			 
			 final EditText input = new EditText(this);
			 pesquisa.setView(input);
			 
			 pesquisa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String value = input.getText().toString();	
					if (value.length() < 3)
						Toast.makeText(getBaseContext(), "A pesquisa deve conter no mínimo 3 caracteres", Toast.LENGTH_LONG).show();
					else{
						venda.util.Global.prodPesquisa = value;
						ProdutoBRL brl = new ProdutoBRL(getBaseContext());
						setListAdapter(new produtoAdapter(getBaseContext(), brl.getByDescricao(value)));
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
			return true;
	 }
	 
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		 if (RETORNO_FORNECEDOR == requestCode){
			 if (resultCode == RESULT_OK){
					venda.util.Global.prodPesquisa = "filtroFornecedore";
					venda.util.Global.filtroFornecedor = data.getIntExtra("codFornecedor",1);
			 }
		 }
		 else if (RETORNO_GRUPO == requestCode){
			 if (resultCode == RESULT_OK){
					venda.util.Global.prodPesquisa = "filtroLinha";	
					venda.util.Global.filtroLinha = data.getStringExtra("codGrupo");
			 }			 
		 }
	 }
	 
	@Override 
	 public void onResume()
	 {
		super.onResume();
	    ProdutoBRL brl = new ProdutoBRL(getBaseContext());
		if (venda.util.Global.prodPesquisa == null){
			setListAdapter(new produtoAdapter(getBaseContext(), brl.getAllOrdenado()));				
		}else if(venda.util.Global.prodPesquisa == "filtroLinha"){
			setListAdapter(new produtoAdapter(getBaseContext(), brl.getByGrupo(venda.util.Global.filtroLinha)));
		}else if(venda.util.Global.prodPesquisa == "filtroFornecedor"){
			setListAdapter(new produtoAdapter(getBaseContext(), brl.getByFornecedor(venda.util.Global.filtroFornecedor)));
		}
		else{
			setListAdapter(new produtoAdapter(getBaseContext(), brl.getByDescricao(venda.util.Global.prodPesquisa)));							
		}		
	 }
	 
}
