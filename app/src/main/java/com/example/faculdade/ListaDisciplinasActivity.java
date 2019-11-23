package com.example.faculdade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaDisciplinasActivity extends AppCompatActivity {
    protected ListView lista;
    protected DisciplinaValue disciplinaValue;
    protected ArrayAdapter<DisciplinaValue>	adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_disciplinas_layout);
        DisciplinaDAO dao = new DisciplinaDAO(this);

        int layout = android.R.layout.simple_list_item_1;

        ArrayList<DisciplinaValue> disciplinas = (ArrayList<DisciplinaValue>) new ArrayList(dao.getLista());
        dao.close();
        adapter = new ArrayAdapter<DisciplinaValue>(this,layout,disciplinas);
        lista =  findViewById(R.id.listView);
        lista.setAdapter(adapter);
        lista.setOnCreateContextMenuListener(this);
        registerForContextMenu(lista);
        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Toast.makeText(ListaDisciplinasActivity.this, adapterView.getItemAtPosition(posicao).toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        DisciplinaDAO dao = new DisciplinaDAO(this);
        ArrayList<DisciplinaValue> disciplinas = new ArrayList(dao.getLista());
        dao.close();
        int	layout = android.R.layout.simple_list_item_1;

        adapter = new ArrayAdapter<DisciplinaValue>(this,layout,disciplinas);

        lista.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }
        if(id == R.id.action_new){
            Intent intent = new Intent(this, DisciplinaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_lista_disciplinas,menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_disciplina,menu);
        return true;
    }

    public boolean onContextItemSelected(final MenuItem item){
        disciplinaValue = (DisciplinaValue) this.adapter.getItem(((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position);
        int id = item.getItemId();
        if(id == R.id.action_new) {
            Intent intent = new Intent(this, DisciplinaActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_update) {
            Intent intent = new Intent(this, DisciplinaActivity.class);
            intent.putExtra("disciplinaSelecionada",disciplinaValue);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_delete) {
            DisciplinaDAO dao = new DisciplinaDAO(ListaDisciplinasActivity.this);
            dao.deletar(disciplinaValue);
            dao.close();
            this.onResume();
            return true;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int codigo, int resultado, @Nullable Intent it) {
        this.adapter.notifyDataSetChanged();
    }
}
