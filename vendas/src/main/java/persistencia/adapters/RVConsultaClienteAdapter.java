package persistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.PedidoDTO;
import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class RVConsultaClienteAdapter extends RecyclerView.Adapter<RVConsultaClienteAdapter.ViewHolder> {

    private List<PedidoDTO> lista;
    private Context ctx;
    private OnItemLongClickListener longClickListener;

    public RVConsultaClienteAdapter(Context ctx, List<PedidoDTO> lista, OnItemLongClickListener longClickListener) {
        this.ctx = ctx;
        this.lista = lista;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public RVConsultaClienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consulta_vendascliente, parent, false);
        return new RVConsultaClienteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVConsultaClienteAdapter.ViewHolder holder, int position) {
        PedidoDTO pedDTO = lista.get(position);
        ClienteBRL cliBRL = new ClienteBRL(ctx);
        ClienteDTO cliDTO;
        ItenPedidoBRL itpBRL = new ItenPedidoBRL(ctx);



        holder.txtCodClienteConsulta.setText(pedDTO.getCodCliente().toString());

        holder.txtDataConsulta.setText(pedDTO.getDataPedido());

        cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
        holder.txtNomeClienteConsulta.setText(cliDTO.getNome());

        Double totalPedido = itpBRL.getTotalPedido(pedDTO.getId());
        DecimalFormat formatador = new DecimalFormat("##,##00.00");
        String totalFormatado = formatador.format(totalPedido);
        totalFormatado = totalFormatado.replace(',', '.');
        holder.txtValorConsulta.setText(totalFormatado);
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(v, position);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCodClienteConsulta;
        TextView txtDataConsulta;
        TextView txtValorConsulta;
        TextView txtNomeClienteConsulta;

        ViewHolder(View itemView) {
            super(itemView);
            txtCodClienteConsulta = itemView.findViewById(R.id.txtCodClienteConsulta);
            txtDataConsulta = itemView.findViewById(R.id.txtDataConsulta);
            txtValorConsulta = itemView.findViewById(R.id.txtValorConsulta);
            txtNomeClienteConsulta = itemView.findViewById(R.id.txtNomeClienteConsulta);
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
