package br.com.fuerback.listacompras.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.fuerback.listacompras.R;
import br.com.fuerback.listacompras.adapters.ListaAdapter;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaAdapter listaAdapter;

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

        Bundle bundle = getIntent().getExtras();
        carregaListaCompras( bundle.getString("lista") );
    }

    private void carregaListaCompras(String lista) {
        List<String> listaCompras = Arrays.asList(lista.split("\n"));

        listaAdapter = new ListaAdapter(listaCompras);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(listaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuEdita){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

            //após tornar essa activity a padrão - remover o finish e carregar lista novamente a partir do onStart
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
