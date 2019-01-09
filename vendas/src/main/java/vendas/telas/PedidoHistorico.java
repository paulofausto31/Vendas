package vendas.telas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import persistencia.adapters.HistoricoPedidoAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import venda.util.Util;

@SuppressLint("NewApi")
public class PedidoHistorico extends ListActivity {

	private static final int MENU_EDITAR = 1;
	private static final int MENU_EXCLUIR = 2;
	private static final int MENU_VISUALIZAR = 3;
	private static final int MENU_DESCONTO = 4;
	private static final int MENU_ACRESCIMO = 5;
	private static final int MENU_PDF = 6;
	PedidoDTO pedDTO;
	ItenPedidoBRL itpBRL;
	PedidoBRL pedBRL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pedBRL = new PedidoBRL(getBaseContext());
		itpBRL = new ItenPedidoBRL(getBaseContext());
		registerForContextMenu(getListView());		
	}

	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Sub Menu Historico");
        menu.add(0,MENU_EDITAR,0,"Editar Pedido"); 
        menu.add(0,MENU_EXCLUIR,0,"Excluir Pedido"); 
        menu.add(0,MENU_VISUALIZAR,0,"Visualizar Pedido"); 
        menu.add(0,MENU_DESCONTO,0,"Desconto Pedido"); 
        menu.add(0,MENU_ACRESCIMO,0,"Acrescimo Pedido"); 
        menu.add(0,MENU_PDF,0,"Gerar PDF");
    }  
	
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		 ListView l = getListView();
		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
		 pedDTO = (PedidoDTO)l.getAdapter().getItem(info.position);
		 if (pedDTO.getBaixado() == 1 && menu.getItemId() != 3){
			 Toast.makeText(getBaseContext(), "Este pedido ja foi baixado e não pode ser manipulado", Toast.LENGTH_SHORT).show();
			 return false;
		 }
		
		 switch (menu.getItemId()){
		 
		 case MENU_EDITAR:  
			 venda.util.Global.pedidoGlobalDTO = pedDTO;
			 RetornaTabPedidoBasico();
			 
			 return true;
		 case MENU_VISUALIZAR:  
			 venda.util.Global.pedidoGlobalDTO = pedDTO;
			 RetornaTabPedidoBasico();
			 
			 return true;
		 case MENU_DESCONTO:
			 if (!itpBRL.ExisteDA(pedDTO.getId()))
				 DescontoAcrescimo('D', "Desconto", "Informe o Desconto", pedDTO);
			 else
				 Toast.makeText(getBaseContext(), "Este pedido ja possui desconto ou acrescimo", Toast.LENGTH_SHORT).show();
			 
			 return true;
		 case MENU_PDF:
			 criandoPdf();
			 
			 return true;
		 case MENU_ACRESCIMO:  
			 if (!itpBRL.ExisteDA(pedDTO.getId()))
				 DescontoAcrescimo('A', "Acrescimo", "Informe o Acrescimo", pedDTO);
			 else
				 Toast.makeText(getBaseContext(), "Este pedido ja possui desconto ou acrescimo", Toast.LENGTH_SHORT).show();
			 
			 return true;
		 case MENU_EXCLUIR:
			 AlertDialog.Builder confirmacao = new AlertDialog.Builder(this);
			 
			 confirmacao.setTitle("Excluir Pedido");
			 confirmacao.setMessage("Confirma Exclusão deste Pedido ?");
			 confirmacao.setCancelable(false);
			 
			 confirmacao.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					PedidoBRL pedBRL = new PedidoBRL(getBaseContext());
					ItenPedidoBRL itpBRL = new ItenPedidoBRL(getBaseContext());
					itpBRL.deleteByCodPedido(pedDTO.getId());
					pedBRL.delete(pedDTO);
					venda.util.Global.pedidoGlobalDTO = new PedidoDTO();			
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
	 
	 @SuppressLint("NewApi")
	private void criandoPdf() {
         Document document = new Document();
		 try {
			 String filename = "teste.pdf";
			 String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/MeuPdf";
			 File dir = new File(path, filename); 
			 if (!dir.exists()) { 
				 dir.getParentFile().mkdirs(); 
				 } 
			 FileOutputStream fOut = new FileOutputStream(dir); 
			 fOut.flush(); 
			 PdfWriter.getInstance(document, fOut);
			 document.open();
			 document.add(new Paragraph(Util.FormataSpaces("Aqui esta meu pdf ",5,0)));
			 } 
		 catch (FileNotFoundException e) {
			 e.printStackTrace(); 
			 } 
		 catch (IOException e) { 
			 e.printStackTrace(); 
			 } 
		 catch (Exception e) {
			 Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			 e.printStackTrace(); 
			 } 
		 finally {
			 document.close(); 
		 }
	 }
	 
	 private void DescontoAcrescimo(final char tipo, final String titulo, final String mensagem, final PedidoDTO pedDTO){
		 AlertDialog.Builder pesquisa = new AlertDialog.Builder(this);
		 
		 pesquisa.setTitle(titulo);
		 pesquisa.setMessage(mensagem);
		 pesquisa.setCancelable(false);
		 
		 final EditText input = new EditText(this);
		 pesquisa.setView(input);
		 
		 pesquisa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Double value = Double.parseDouble(input.getText().toString());	
				ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
				if (tipo == 'D')
					SetDescontoPedido(pedDTO, value);
				else
					SetAcrescimoPedido(pedDTO, value);
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
	 
	private void SetDescontoPedido(PedidoDTO pedDTO, Double value) {
		Double preco;
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getBaseContext());
		List<ItenPedidoDTO> itens = itpBRL.getByCodPedido(pedDTO.getId());
		for (ItenPedidoDTO itenPedidoDTO : itens) {
			preco = itenPedidoDTO.getPreco();
			preco = preco - (preco * (value / 100));
			
			DecimalFormat formatador = new DecimalFormat("##,##00.00");  
		    String precoFormatado = formatador.format(preco);  
		    precoFormatado = precoFormatado.replace(',', '.'); 		

			itenPedidoDTO.setPreco(Double.parseDouble(precoFormatado));
			itenPedidoDTO.setDA("D");
			itenPedidoDTO.setDAValor(value);
			itpBRL.Update(itenPedidoDTO);
		}
	}

	private void SetAcrescimoPedido(PedidoDTO pedDTO, Double value) {
		Double preco;
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getBaseContext());
		List<ItenPedidoDTO> itens = itpBRL.getByCodPedido(pedDTO.getId());
		for (ItenPedidoDTO itenPedidoDTO : itens) {
			preco = itenPedidoDTO.getPreco();
			preco = preco + (preco * (value / 100));
			itenPedidoDTO.setPreco(preco);
			itenPedidoDTO.setDA("A");
			itenPedidoDTO.setDAValor(value);
			itpBRL.Update(itenPedidoDTO);
		}
	}

	private void RetornaTabPedidoBasico() {
    TabActivity tabs = (TabActivity) getParent();
    tabs.getTabHost().setCurrentTab(0); 			 
	}

	@Override
	protected void onResume() {
		super.onResume();
		PedidoBRL pedBRL = new PedidoBRL(getBaseContext());
		setListAdapter(new HistoricoPedidoAdapter(getBaseContext(), pedBRL.getByCodCliente(venda.util.Global.pedidoGlobalDTO.getCodCliente())));				
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		pedDTO.setHoraPedidoFim(venda.util.Util.getTime());
		pedBRL.Update(pedDTO);
		venda.util.Global.pedidoGlobalDTO = pedDTO;
	}


}
