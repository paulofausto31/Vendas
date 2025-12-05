package persistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PrecoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.PrecoDTO;
import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class RVPedidoProdutoAdapter extends RecyclerView.Adapter<RVPedidoProdutoAdapter.ViewHolder> {

    private Context context;
    private final List<ProdutoDTO> items;
    private final OnItemClickListener listener;
    ItenPedidoBRL itpBRL;
    ProdutoBRL proBRL;

    public interface OnItemClickListener {
        void onItemClick(ProdutoDTO item);
    }

    public RVPedidoProdutoAdapter(Context ctx, List<ProdutoDTO> items, OnItemClickListener listener) {
        this.context = ctx;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        itpBRL = new ItenPedidoBRL(context);
        proBRL = new ProdutoBRL(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdutoDTO item = items.get(position);
        PrecoBRL preBRL = new PrecoBRL(context);

        Double qtdTotal = itpBRL.getSumQtdAberto(item.getCodProduto());
        Double saldoEstoque = proBRL.getSaldoEstoque(item.getCodProduto().toString());
        Double saldo = saldoEstoque - qtdTotal;

        holder.txtCodigoProdutoPesquisa.setText(item.getCodProduto().toString());
        holder.txtDescricaoProduto.setText(item.getDescricao());
        holder.txtEstoqueProduto.setText(saldo.toString());//teste

        List<PrecoDTO> listPreco = preBRL.getByProduto(item.getCodProduto());
        if (listPreco.size() > 0) {
            PrecoDTO preDTO = listPreco.get(0);
            holder.txtPrecoProduto.setText(preDTO.getPreco().toString());
        }
        holder.itemView.setOnLongClickListener(v -> {
            showPopupMenu(holder.itemView, item);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtCodigoProdutoPesquisa;
        public final TextView txtDescricaoProduto;
        public final TextView txtEstoqueProduto;
        public final TextView txtPrecoProduto;

        public ViewHolder(View view) {
            super(view);
            txtCodigoProdutoPesquisa = view.findViewById(R.id.txtCodigoProdutoPesquisa);
            txtDescricaoProduto = view.findViewById(R.id.txtDescricaoProdutoPesquisa);
            txtEstoqueProduto = view.findViewById(R.id.txtEstoqueProdutoPesquisa);
            txtPrecoProduto = view.findViewById(R.id.txtPrecoProdutoPesquisa);
        }
    }

    private void showPopupMenu(View view, ProdutoDTO item) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.select_item) {
                listener.onItemClick(item);
                return true;
            }
            return false;
        });
        popup.show();
    }
}
