package br.com.fuerback.listacompras.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.fuerback.listacompras.R;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.MyViewHolder> {

    private List<String> listaCompras;

    public ListaAdapter(List<String> lista) {
        this.listaCompras = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLitsa = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_compras_adapter, viewGroup, false);

        return new MyViewHolder(itemLitsa);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        String item = listaCompras.get(position);
        myViewHolder.item.setText(item);
    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.textItem);
        }
    }
}
