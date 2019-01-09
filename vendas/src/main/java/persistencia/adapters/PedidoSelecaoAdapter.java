package persistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.PedidoDTO;
import vendas.telas.R;

public class PedidoSelecaoAdapter extends BaseAdapter {
    private Context ctx;
    private List<PedidoDTO> lista;

    public PedidoSelecaoAdapter(Context ctx, List<PedidoDTO> lista){
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
        PedidoDTO pedDTO = (PedidoDTO)getItem(position);
        ClienteBRL cliBRL = new ClienteBRL(ctx);
        ClienteDTO cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
        ItenPedidoBRL itpBRL = new ItenPedidoBRL(ctx);

        LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.pedido_selecao_exportacao, null);

        TextView txtStatusPed = (TextView)v.findViewById(R.id.txtStatusPedSel);
        if (pedDTO.getFechado().equals("0"))
            txtStatusPed.setText("A");
        else
            txtStatusPed.setText("F");

        TextView txtNomeCliente = (TextView)v.findViewById(R.id.txtNomeClientePedSel);
        txtNomeCliente.setText(cliDTO.getNome());

        TextView txtCodCliente = (TextView)v.findViewById(R.id.txtCodClientePedSel);
        txtCodCliente.setText(pedDTO.getCodCliente().toString());

        TextView txtDataPedido = (TextView)v.findViewById(R.id.txtDataPedSel);
        txtDataPedido.setText(pedDTO.getDataPedido());

        TextView txtValorPedido = (TextView)v.findViewById(R.id.txtValorPedSel);
        Double totalPedido = itpBRL.getTotalPedido(pedDTO.getId());
        DecimalFormat formatador = new DecimalFormat("##,##00.00");
        String totalFormatado = formatador.format(totalPedido);
        totalFormatado = totalFormatado.replace(',', '.');
        txtValorPedido.setText(totalFormatado);

        return v;
    }

}
