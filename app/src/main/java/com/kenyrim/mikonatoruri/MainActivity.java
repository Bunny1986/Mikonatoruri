package com.kenyrim.mikonatoruri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.kenyrim.mikonatoruri.main.AdapterMain;
import com.kenyrim.mikonatoruri.main.Event;
import com.kenyrim.mikonatoruri.main.Model;
import com.kenyrim.mikonatoruri.tools.InternetConnection;
import com.kenyrim.mikonatoruri.tools.ItemClickSupport;
import com.kenyrim.mikonatoruri.tools.RetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    ArrayList<Event> items;
    AdapterMain adapter;
    Spinner spinner;
    String[] data = {
            "Футбол",
            "Хоккей",
            "Теннис",
            "Баскетбол",
            "Волейбол",
            "Киберспорт"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ApiService api = RetroClient.getApiService();

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this, R.layout.spinner, data);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);
        spinner.setPrompt("Футбол");
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0){
                    CallMyJSON(api.getFootball());
                }
                else if (position == 1){
                    CallMyJSON(api.getHockey());
                }
                else if (position == 2){
                    CallMyJSON(api.getTennis());
                }
                else if (position == 3){
                    CallMyJSON(api.getBasketball());
                }
                else if (position == 4){
                    CallMyJSON(api.getVolleyball());
                }
                else if (position == 5){
                    CallMyJSON(api.getCybersport());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        items = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                AdapterMain.ViewHolder holder =(AdapterMain.ViewHolder) recyclerView.getChildViewHolder(v);
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("URL",holder.url);
                intent.putExtra("TITLE",holder.title.getText().toString());
                startActivity(intent);
            }
        });


    }
    public void CallMyJSON(Call<Model> call){

        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Загрузка");
            dialog.show();
            call.enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    //Dismiss Dialog
                    dialog.dismiss();

                    if(response.isSuccessful()) {
                        assert response.body() != null;
                        items = (ArrayList<Event>) response.body().getEvents();
                        adapter = new AdapterMain(MainActivity.this, items);
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(getApplicationContext(), "Что-то пошло не так!", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Нет соединения", Toast.LENGTH_LONG).show();
        }
    }
}