package com.example.faculdade;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private TextView textView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView = (TextView) this.findViewById(R.id.textView);
        textView.setText("Aguarde Carregando.......");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new JSONParse().execute();
            }
        }, SPLASH_TIME_OUT);
    }

    public class JSONParse extends AsyncTask<String,String, JSONObject>{
        private	ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(SplashScreen.this);

            pDialog.setMessage("Obtendo Dados");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject json	= null;
            DisciplinaDAO disciplinaDAO	= new DisciplinaDAO(SplashScreen.this);
            disciplinaDAO.dropAll();
            JSONArray link = null;
            json = Json();
            int count=0;
            try	{
                //	Getting	JSON	Array
                link = json.getJSONArray("Lista");
                for	(int i = 0; i < link.length(); i++)	{
                    JSONObject c = link.getJSONObject(i);
                    DisciplinaValue disciplinaValue	= new DisciplinaValue();
                    disciplinaValue.setDisciplina(c.getString("disciplina"));
                    disciplinaDAO.salvar(disciplinaValue);
                    disciplinaDAO.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try{
                pDialog.dismiss();
                Intent i = new Intent(SplashScreen.this, ListaDisciplinasActivity.class);
                startActivity(i);
                finish();
            } catch (Exception e) {

            }
        }
    }

    public	static	JSONObject Json(){
        JSONObject json	= null;
        String resp=null;
        try	{
            URL	url1 = new URL("http://www.ictios.com.br/emjorge/appfaculdade/"+	"index1.php");
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestProperty("Content-Type",	"application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            InputStream inputStream	= conn.getInputStream();
            resp = IOUtils.toString(inputStream);
            json = new JSONObject(resp);

            Log.i("Teste", json.toString());
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return	null;
    }

}
