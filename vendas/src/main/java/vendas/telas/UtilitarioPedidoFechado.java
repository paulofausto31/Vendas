package vendas.telas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;
import android.widget.PopupMenu;
import android.widget.Toast;

import persistencia.adapters.RVUtilitarioPedidoAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import venda.util.PDFGenerator;

public class UtilitarioPedidoFechado extends Fragment implements RVUtilitarioPedidoAdapter.OnItemLongClickListener {

	private RecyclerView recyclerView;
	private RVUtilitarioPedidoAdapter adapter;
	List<PedidoDTO> lista;
	PedidoBRL brl;
	Intent intent;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fechados, container, false);
		recyclerView = view.findViewById(R.id.recycler_view_fechados);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		brl = new PedidoBRL(getContext());

		return view;
	}

	@Override
	public void onItemLongClick(View view, int position) {
		PedidoDTO pedDTO = lista.get(position);
		PopupMenu popup = new PopupMenu(getContext(), view);
		popup.getMenuInflater().inflate(R.menu.popup_utilitario_enviado, popup.getMenu());
		popup.setOnMenuItemClickListener(item -> {
			if (item.getItemId() == R.id.action_abre_pedido) {
				atualizarLista(pedDTO);
				return true;
			}
			if (item.getItemId() == R.id.action_visualizar_pedido) {
				venda.util.Global.pedidoGlobalDTO = pedDTO;
				intent = new Intent(getContext(), PedidoTabContainer.class);
				intent.putExtra("idCliente", 0);
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return true;
			}
			if (item.getItemId() == R.id.action_gerar_pdf) {
				ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());
				ClienteBRL cliBRL = new ClienteBRL(getContext());
				ClienteDTO cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
				List<ItenPedidoDTO> list = itpBRL.getByCodPedido(pedDTO.getId());
				PDFGenerator pdf = new PDFGenerator();
				pdf.createPDF(getContext(),"arquivoPDF.pdf", list, cliDTO);
				sharePDF("arquivoPDF.pdf");

				return true;
			}
			return false;
		});
		popup.show();
	}

	public void sharePDF(String fileName) {
		Context context = getContext();
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
		File file = new File(path, fileName);

		Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.setType("application/pdf");
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		// Verifique se o WhatsApp está instalado
		if (isAppInstalled(context, "com.whatsapp")) {
			shareIntent.setPackage("com.whatsapp");
		} else {
			// O WhatsApp não está instalado, mostre um aviso ou use outro aplicativo de compartilhamento
			shareIntent = Intent.createChooser(shareIntent, "Escolha um aplicativo para compartilhar o PDF");
		}

		try {
			startActivity(shareIntent);
		} catch (android.content.ActivityNotFoundException ex) {
			// Mostrar uma mensagem de erro se não houver aplicativos que possam lidar com o Intent
			Toast.makeText(context, "Nenhum aplicativo encontrado para compartilhar o PDF", Toast.LENGTH_SHORT).show();
		}
	}

	// Método para verificar se um aplicativo está instalado
	private boolean isAppInstalled(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

	private void atualizarLista(PedidoDTO dto) {
		// Adicione lógica para atualizar a lista
		brl.AbrePedidoBaixado(dto.getId());
		lista = brl.getAllPedEnviado();
		adapter = new RVUtilitarioPedidoAdapter(getContext(), lista, this);
		recyclerView.setAdapter(adapter);
		//Toast.makeText(getContext(), "Lista Atualizada", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResume() {
		super.onResume();
		lista = brl.getAllPedEnviado();
		adapter = new RVUtilitarioPedidoAdapter(getContext(), lista, this);
		recyclerView.setAdapter(adapter);
	}
}
