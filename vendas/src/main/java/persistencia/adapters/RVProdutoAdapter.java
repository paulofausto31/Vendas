package persistencia.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class RVProdutoAdapter extends RecyclerView.Adapter<RVProdutoAdapter.ViewHolder> {
    private Context context;
    private static List<ProdutoDTO> lista;
    private OnItemClickListener listener;
    ItenPedidoBRL itpBRL;
    ProdutoBRL proBRL;


    public RVProdutoAdapter(Context ctx, List<ProdutoDTO> lista, OnItemClickListener listener){
        this.context = ctx;
        this.lista = lista;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public RVProdutoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_produtos, parent, false);
        itpBRL = new ItenPedidoBRL(context);
        proBRL = new ProdutoBRL(context);
        return new RVProdutoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdutoDTO dto = lista.get(position);


        Double qtdTotal = itpBRL.getSumQtdAberto(dto.getCodProduto());
        Double saldoEstoque = proBRL.getSaldoEstoque(dto.getCodProduto().toString());
        Double saldo = saldoEstoque - qtdTotal;

        holder.txtRVCodigoProduto.setText(dto.getCodProduto().toString());
        holder.txtRVDescricaoProduto.setText(dto.getDescricao());
        holder.txtRVEstoqueProduto.setText(saldo.toString());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRVCodigoProduto;
        public TextView txtRVDescricaoProduto;
        public TextView txtRVEstoqueProduto;

        public ViewHolder(View view) {
            super(view);
            txtRVCodigoProduto = view.findViewById(R.id.txtRVCodigoProduto);
            txtRVDescricaoProduto = view.findViewById(R.id.txtRVDescricaoProduto);
            txtRVEstoqueProduto = view.findViewById(R.id.txtRVEstoqueProduto);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
