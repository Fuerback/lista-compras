package br.com.fuerback.listacompras.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.fuerback.listacompras.R;
import br.com.fuerback.listacompras.adapters.ListaAdapter;
import br.com.fuerback.listacompras.models.Item;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaAdapter listaAdapter;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private ValueEventListener valueEventListener;
    private DatabaseReference comprasRef;
    private ArrayList<Item> itens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerListaCompras);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Lista de compras");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        comprasRef = reference.child("compras");

        valueEventListener = comprasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itens.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Item item = dados.getValue(Item.class);
                    item.setKey(dados.getKey());
                    itens.add(item);
                }
                carregaListaCompras(itens);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void carregaListaCompras(List<Item> itens) {
        listaAdapter = new ListaAdapter(itens);

        Parcelable recyclerViewState = getRecyclerViewState();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(listaAdapter);

        restoreRecyclerViewState(recyclerViewState);
    }

    private void restoreRecyclerViewState(Parcelable recyclerViewState) {
        if (recyclerViewState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    private Parcelable getRecyclerViewState() {
        Parcelable recyclerViewState = null;
        if (recyclerView.getLayoutManager() != null) {
            recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        }
        return recyclerViewState;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuEdita) {
            startEditActivity();
        } else {
            removeCheckedItens();
            carregaListaCompras(itens);
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeCheckedItens() {
        List<Item> itensToRemove = new ArrayList<>();

        for (Item i : itens) {
            if (i.isChecked()) {
                itensToRemove.add(i);
                comprasRef.child(i.getKey()).removeValue();
            }
        }
        itens.removeAll(itensToRemove);
    }

    private void startEditActivity() {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("lista", itens);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        comprasRef.removeEventListener(valueEventListener);
    }
}
