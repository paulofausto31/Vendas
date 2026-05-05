package persistencia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import persistencia.brl.ProdutoBRL;
import persistencia.dto.ItemAvariaDTO;
import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class AvariaAdapter extends RecyclerView.Adapter<AvariaAdapter.AvariaViewHolder> {

    private List<ItemAvariaDTO> listaItens;
    private OnItemClickListener listener;

    // Interface para lidar com os cliques nos botões de Editar e Excluir
    public interface OnItemClickListener {
        void onEditarClick(int position, ItemAvariaDTO item);
        void onExcluirClick(int position, ItemAvariaDTO item);
    }

    // Construtor do Adapter
    public AvariaAdapter(List<ItemAvariaDTO> listaItens, OnItemClickListener listener) {
        this.listaItens = listaItens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AvariaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // "Infla" o layout XML de cada linha (item_avaria.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avaria, parent, false);
        return new AvariaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvariaViewHolder holder, int position) {
        ItemAvariaDTO item = listaItens.get(position);
        ProdutoBRL proBRL = new ProdutoBRL(holder.itemView.getContext());
        ProdutoDTO proDTO = proBRL.getByCodProduto(Long.parseLong(item.getCodProduto().toString()));

        // Trava de segurança: só tenta pegar a descrição se realmente encontrou o produto no banco
        if (proDTO != null) {
            holder.tvItemDescricao.setText(proDTO.getDescricao());
        } else {
            // Se por algum motivo não achar, ele não quebra o app, apenas mostra um aviso na linha
            holder.tvItemDescricao.setText("Produto não encontrado (Cód: " + item.getCodProduto() + ")");
        }

        holder.tvItemQtdUnidade.setText(String.format("%s %s", item.getQuantidade(), item.getUnidade()));
        holder.tvItemPreco.setText(String.format("%.2f", item.getPreco()));

        // Configura os cliques dos botões
        holder.btnEditarItem.setOnClickListener(v -> {
            if (listener != null) listener.onEditarClick(position, item);
        });

        holder.btnExcluirItem.setOnClickListener(v -> {
            if (listener != null) listener.onExcluirClick(position, item);
        });
    }

    @Override
    public int getItemCount() {
        return listaItens != null ? listaItens.size() : 0;
    }

    // Classe ViewHolder para mapear os componentes do item_avaria.xml
    public static class AvariaViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemDescricao, tvItemQtdUnidade, tvItemPreco;
        ImageButton btnEditarItem, btnExcluirItem;

        public AvariaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemDescricao = itemView.findViewById(R.id.tvItemDescricao);
            tvItemQtdUnidade = itemView.findViewById(R.id.tvItemQtdUnidade);
            tvItemPreco = itemView.findViewById(R.id.tvItemPreco);
            btnEditarItem = itemView.findViewById(R.id.btnEditarItem);
            btnExcluirItem = itemView.findViewById(R.id.btnExcluirItem);
        }
    }
}