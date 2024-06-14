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
import persistencia.brl.ProdutoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class RVItensPedidoAdapter extends RecyclerView.Adapter<RVItensPedidoAdapter.ViewHolder> {

    private List<ItenPedidoDTO> lista;
    private Context ctx;
    private RVItensPedidoAdapter.OnItemLongClickListener longClickListener;

    public RVItensPedidoAdapter(Context ctx, List<ItenPedidoDTO> lista, RVItensPedidoAdapter.OnItemLongClickListener longClickListener) {
        this.ctx = ctx;
        this.lista = lista;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public RVItensPedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedido_itens, parent, false);
        return new RVItensPedidoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVItensPedidoAdapter.ViewHolder holder, int position) {
        ItenPedidoDTO dto = lista.get(position);
        ProdutoBRL proBRL = new ProdutoBRL(ctx);
        ProdutoDTO proDTO = new ProdutoDTO();

        proDTO = proBRL.getByCodProduto(dto.getCodProduto());
        holder.txtProdutoItens.setText(proDTO.getDescricao());

        holder.txtQuantidade.setText(dto.getQuantidade().toString());

        holder.txtPrecoUnitario.setText(dto.getPreco().toString());

        if (dto.getDAValor() > 0.00)
            holder.txtDAList.setText(dto.getDA().concat(":").concat(dto.getDAValor().toString()).concat("%"));
        else
            holder.txtDAList.setText(null);

        Double total = dto.getQuantidade() * dto.getPreco();
        holder.txtTotal.setText(total.toString());
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

        TextView txtProdutoItens;
        TextView txtQuantidade;
        TextView txtPrecoUnitario;
        TextView txtTotal;
        TextView txtDAList;

        ViewHolder(View itemView) {
            super(itemView);
            txtProdutoItens = itemView.findViewById(R.id.txtProdutoItens);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidade);
            txtPrecoUnitario = itemView.findViewById(R.id.txtPrecoUnitario);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtDAList = itemView.findViewById(R.id.txtDAList);
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
