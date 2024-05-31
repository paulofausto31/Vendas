package persistencia.adapters;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class RVPedidoProdutoAdapter extends RecyclerView.Adapter<RVPedidoProdutoAdapter.ViewHolder> {

    private final List<ProdutoDTO> items;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ProdutoDTO item);
    }

    public RVPedidoProdutoAdapter(List<ProdutoDTO> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdutoDTO item = items.get(position);
        holder.textView.setText(item.getDescricao());
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
        public final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
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
