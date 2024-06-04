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
import persistencia.dto.PedidoDTO;
import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class RVConsultaProdutoAdapter extends RecyclerView.Adapter<RVConsultaProdutoAdapter.ViewHolder> {

    private List<Long> lista;
    private Context ctx;

    public RVConsultaProdutoAdapter(Context ctx, List<Long> lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    @NonNull
    @Override
    public RVConsultaProdutoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consulta_vendasproduto, parent, false);
        return new RVConsultaProdutoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVConsultaProdutoAdapter.ViewHolder holder, int position) {
        Long codProduto = lista.get(position);
        ProdutoBRL proBRL = new ProdutoBRL(ctx);
        ProdutoDTO proDTO;
        ItenPedidoBRL itpBRL = new ItenPedidoBRL(ctx);

        proDTO = proBRL.getByCodProduto(codProduto);
        holder.txtCodProdutoConsulta.setText(proDTO.getCodProduto().toString());

        holder.txtDescricaoProdutoConsulta.setText(proDTO.getDescricao());

        Double total = itpBRL.getSumQtdAberto(proDTO.getCodProduto());
        DecimalFormat formatador = new DecimalFormat("##,###0.00");
        String totalFormatado = formatador.format(total);
        totalFormatado = totalFormatado.replace('.', ',');
        holder.txtQtdVendaConsulta.setText(totalFormatado);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCodProdutoConsulta;
        TextView txtDescricaoProdutoConsulta;
        TextView txtQtdVendaConsulta;

        ViewHolder(View itemView) {
            super(itemView);
            txtCodProdutoConsulta = itemView.findViewById(R.id.txtCodProdutoConsulta);
            txtDescricaoProdutoConsulta = itemView.findViewById(R.id.txtDescricaoProdutoConsulta);
            txtQtdVendaConsulta = itemView.findViewById(R.id.txtQtdVendaConsulta);
        }
    }

}

