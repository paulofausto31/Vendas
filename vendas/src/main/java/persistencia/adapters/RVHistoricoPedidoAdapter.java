package persistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class RVHistoricoPedidoAdapter extends RecyclerView.Adapter<RVHistoricoPedidoAdapter.ViewHolder> {
    private List<PedidoDTO> lista;
    private Context ctx;
    private OnItemLongClickListener longClickListener;

    public RVHistoricoPedidoAdapter(Context ctx, List<PedidoDTO> lista, OnItemLongClickListener longClickListener) {
        this.ctx = ctx;
        this.lista = lista;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public RVHistoricoPedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedido_historico, parent, false);
        return new RVHistoricoPedidoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVHistoricoPedidoAdapter.ViewHolder holder, int position) {
        PedidoDTO pedDTO = lista.get(position);
        ItenPedidoBRL itpBRL = new ItenPedidoBRL(ctx);

        holder.txtDataHistorico.setText(pedDTO.getDataPedido());

        holder.txtPgto.setText(pedDTO.getFormaPgto());

        Double total = itpBRL.getTotalPedido(pedDTO.getId());
        DecimalFormat formatador = new DecimalFormat("##,##00.00");
        String totalFormatado = formatador.format(total);
        totalFormatado = totalFormatado.replace(',', '.');
        holder.txtTotalHistorico.setText(totalFormatado);
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

        TextView txtDataHistorico;
        TextView txtTotalHistorico;
        TextView txtPgto;

        ViewHolder(View itemView) {
            super(itemView);
            txtDataHistorico = itemView.findViewById(R.id.txtDataHistorico);
            txtTotalHistorico = itemView.findViewById(R.id.txtTotalHistorico);
            txtPgto = itemView.findViewById(R.id.txtPgto);
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
