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

import persistencia.brl.ItenPedidoBRL;
import persistencia.dto.PedidoDTO;
import vendas.telas.R;

public class RVUtilitarioPedidoAdapter extends RecyclerView.Adapter<RVUtilitarioPedidoAdapter.ViewHolder> {

    private List<PedidoDTO> lista;
    private Context ctx;
    private OnItemLongClickListener longClickListener;

    public RVUtilitarioPedidoAdapter(Context ctx, List<PedidoDTO> lista, OnItemLongClickListener longClickListener) {
        this.ctx = ctx;
        this.lista = lista;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.utilitario_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PedidoDTO pedDTO = lista.get(position);
        ItenPedidoBRL itpBRL = new ItenPedidoBRL(ctx);

        Double totalPedido = itpBRL.getTotalPedido(pedDTO.getId());
        DecimalFormat formatador = new DecimalFormat("##,##00.00");
        String totalFormatado = formatador.format(totalPedido);
        totalFormatado = totalFormatado.replace(',', '.');

        holder.txtDataPedidoUtil.setText(pedDTO.getDataPedido());
        holder.txtCodClienteUtil.setText(pedDTO.getCodCliente().toString());
        holder.txtValorPedidoUtil.setText(totalFormatado);
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

        TextView txtValorPedidoUtil;
        TextView txtDataPedidoUtil;
        TextView txtCodClienteUtil;

        ViewHolder(View itemView) {
            super(itemView);
            txtValorPedidoUtil = itemView.findViewById(R.id.txtValorPedidoUtil);
            txtDataPedidoUtil = itemView.findViewById(R.id.txtDataPedidoUtil);
            txtCodClienteUtil = itemView.findViewById(R.id.txtCodClienteUtil);
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}