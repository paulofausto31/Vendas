package persistencia.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.text.DecimalFormat;
import java.util.List;

import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.PedidoDTO;
import persistencia.dto.ProdutoDTO;
import vendas.telas.Principal;
import vendas.telas.R;

public class RVUtilitarioPedidoAdapter extends RecyclerView.Adapter<RVUtilitarioPedidoAdapter.ViewHolder> {

    private List<PedidoDTO> lista;
    private Context ctx;

    public RVUtilitarioPedidoAdapter(Context ctx, List<PedidoDTO> lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.utilitario_pedido, parent, false);
        return new ViewHolder(view);
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_utilitario, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            PedidoDTO pedDTO = lista.get(position);
            switch (item.getItemId()) {
                case R.id.action_abre_pedido:
                    // Ação de editar
                    AbrePedido(pedDTO);
                    return true;
                default:
                    return false;
            }
        }
    }

    private void AbrePedido(PedidoDTO dto) {
        PedidoBRL pedBRL = new PedidoBRL(this.ctx);
        pedBRL.AbrePedidoBaixado(dto.getId());
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
            showPopupMenu(v, position);
            return true;
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
}
