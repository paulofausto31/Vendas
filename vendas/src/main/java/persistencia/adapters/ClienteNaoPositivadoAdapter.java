package persistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import persistencia.brl.JustificativaPositivacaoBRL;
import persistencia.dto.ClienteNaoPositivadoDTO;
import persistencia.dto.JustificativaPositivacaoDTO;

public class ClienteNaoPositivadoAdapter extends BaseAdapter {

	private Context ctx;
	private List<ClienteNaoPositivadoDTO> lista;
	
	public ClienteNaoPositivadoAdapter(Context ctx, List<ClienteNaoPositivadoDTO> lista){
		this.ctx = ctx;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ClienteNaoPositivadoDTO dto = (ClienteNaoPositivadoDTO)getItem(position);
		JustificativaPositivacaoBRL jusBRL = new JustificativaPositivacaoBRL(ctx);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.justificativa_historico, null);
		
		TextView txtData = (TextView)v.findViewById(vendas.telas.R.id.txtData);
		txtData.setText(dto.getData());

		TextView txtJustificativa = (TextView)v.findViewById(vendas.telas.R.id.txtJustificativa);
		JustificativaPositivacaoDTO jusDTO = new JustificativaPositivacaoDTO();
		jusDTO = jusBRL.getByCodJustificativa(dto.getCodJustificativa());
		txtJustificativa.setText(jusDTO.getDescricao());

		return v;
	}

}
