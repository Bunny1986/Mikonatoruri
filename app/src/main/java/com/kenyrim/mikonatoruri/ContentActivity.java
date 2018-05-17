package com.kenyrim.mikonatoruri;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kenyrim.mikonatoruri.content.ApiContent;
import com.kenyrim.mikonatoruri.content.Article;
import com.kenyrim.mikonatoruri.content.Content;
import com.kenyrim.mikonatoruri.tools.InternetConnection;
import com.kenyrim.mikonatoruri.tools.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentActivity extends AppCompatActivity{

    TextView
    _title2,
    _team1,
    _team2,
    _time,
    _tournament,
    _place,
    _article,
    _prediction;

    String url, title;

    ArrayList<Article> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        _title2 = findViewById(R.id._title2);
        _team1 = findViewById(R.id._team1);
        _team2 = findViewById(R.id._team2);
        _time = findViewById(R.id._time);
        _tournament = findViewById(R.id._tournament);
        _place = findViewById(R.id._place);
        _article = findViewById(R.id._article);
        _prediction = findViewById(R.id._prediction);

        url = getIntent().getStringExtra("URL");
        title = getIntent().getStringExtra("TITLE");
        _title2.setText(title);
        arrayList = new ArrayList<>();

        CallMyJSON();
    }

    public void CallMyJSON(){
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(ContentActivity.this);
            dialog.setMessage("Загрузка");
            dialog.show();
            ApiContent api = RetroClient.getApiContent();
            Call<Content> call = api.getArt(url);
            call.enqueue(new Callback<Content>() {
                @Override
                public void onResponse(Call<Content> call, Response<Content> response) {
                    dialog.dismiss();

                    if(response.isSuccessful()) {

                            _team1.setText("Команда 1: " + response.body().getTeam1());
                            _team2.setText("Команда 2: " + response.body().getTeam2());
                            _time.setText("Время: " + response.body().getTime());
                            _tournament.setText("Турнир: " + response.body().getTournament());
                            _place.setText("Место: " + response.body().getPlace());


                            List<Article> iList = response.body().getArticle();

                            String[] links = arrayList.toArray(new String[iList.size()]);
                            for (int i = 0; i < iList.size(); i++) {
                                links [i] = iList.get(i).getHeader() + "\n" + iList.get(i).getText();
                                if (_article.getText() != null) {
                                    _article.setText(_article.getText() + "\n" + String.valueOf(links[i]) + "\n");
                                }
                                else {
                                    _article.setText(String.valueOf(links[i]));
                                }


                            Log.e("AAAAA", String.valueOf(links[i]));
                        }


                            _prediction.setText(response.body().getPrediction());

                    } else {
                        Toast.makeText(getApplicationContext(), "Что-то пошло не так!", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Content> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Описание отсутствует!", Toast.LENGTH_LONG).show();
                    _team1.setText("Описание отсутствует!");

                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Нет соединения", Toast.LENGTH_LONG).show();
        }
    }
}
