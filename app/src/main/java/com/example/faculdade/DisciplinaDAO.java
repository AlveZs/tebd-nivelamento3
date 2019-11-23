package com.example.faculdade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class DisciplinaDAO extends SQLiteOpenHelper {
    private static final String DATABASE = "BancoDisciplinas";
    private static final int VERSAO = 1;

    public DisciplinaDAO(Context context){
        super(context, DATABASE, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE Disciplina (id INTEGER PRIMARY KEY,"+" disciplina TEXT UNIQUE NOT NULL);";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int velha, int nova) {
        String ddl = "DROP TABLE IF EXISTS Disciplina";
        db.execSQL(ddl);
        onCreate(db);
    }

    public void dropAll(){
        String ddl ="DROP TABLE IF EXISTS Disciplina";
        getWritableDatabase().execSQL(ddl);
        onCreate( getWritableDatabase());
    }

    public void salvar(DisciplinaValue disciplinaValue){
        ContentValues values = new ContentValues();
        values.put("disciplina",disciplinaValue.getDisciplina());

        getWritableDatabase().insert("Disciplina",null,values);
    }

    public List getLista(){
        List<DisciplinaValue> disciplinas = new LinkedList<DisciplinaValue>();

        String query = "SELECT * FROM Disciplina order by disciplina";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        DisciplinaValue disciplina = null;
        if(cursor.moveToFirst()){
            do{
                disciplina = new DisciplinaValue();
                disciplina.setId(Long.parseLong(cursor.getString(0)));
                disciplina.setDisciplina(cursor.getString(1));
                disciplinas.add(disciplina);
            } while(cursor.moveToNext());
        }
        return disciplinas;
    }

    public void	deletar(DisciplinaValue disciplinaValue) {
        String[] args = { disciplinaValue.getId().toString() };
        getWritableDatabase().delete("Disciplina","id=?",	args);
    }

    public void	alterar(DisciplinaValue	disciplina)	{
        ContentValues values = new ContentValues();
        values.put("disciplina", disciplina.getDisciplina());
        getWritableDatabase().update("Disciplina", values,"id=?",	new	String[]{disciplina.getId().toString()});
    }

}
