package br.com.fuerback.listacompras.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.fuerback.listacompras.R;
import br.com.fuerback.listacompras.models.Item;

public class EditActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private EditText editText;
    private ArrayList<Item> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Lista de compras");
        setSupportActionBar(toolbar);

        this.editText = findViewById(R.id.listaDeCompras);

        // receber a lista de objetos e salvar eles no editText entre \n
        Intent i = getIntent();
        itens = (ArrayList<Item>) i.getSerializableExtra("lista");
        populaCompras(itens);
    }

    private void populaCompras(ArrayList<Item> itens) {
        String compras = "";
        for(Item item: itens){
            compras += item.getNome()+"\n";
        }
        editText.setText(compras);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_confirma, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuConfirma) {

            List<Item> itens = new ArrayList<>();
            for (String i : Arrays.asList(editText.getText().toString().split("\n"))){
                itens.add( new Item( i,false) );
            }
            reference.child("comprasTeste").setValue(itens);

            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);

            finish();
        } else {
            editText.setText(null);
        }

        return super.onOptionsItemSelected(item);
    }
}
