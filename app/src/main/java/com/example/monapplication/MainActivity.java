package com.example.monapplication;

import static com.example.monapplication.Joueur.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView tvPseudo,tvVie,tvPoint,tvToucheVide;
    Button btnQuitter,btnTir;
    private int tour=0;
    private int ordi=0;
    private int point=0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int VieJoueur;
    private int VieJoueurInt;
    private String NomJoueur;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnQuitter=(Button) findViewById(R.id.btnquitter);
        btnTir=(Button) findViewById(R.id.btnTir);
        tvPseudo=(TextView) findViewById(R.id.txtPseudoPlayer);
        tvVie=(TextView) findViewById(R.id.txtLife);
        tvPoint=(TextView) findViewById(R.id.txtPoint);
        tvToucheVide=(TextView) findViewById(R.id.txtToucheLoupe);

        Play();

        Intent intent = new Intent(this,Joueur.class);
        NomJoueur =getIntent().getStringExtra("NomJoueur");
        VieJoueur =getIntent().getIntExtra("VieJoueur",0);
        tvPseudo.setText(NomJoueur);
        tvVie.setText("Vie :"+Integer.toString(VieJoueur));
        VieJoueurInt=VieJoueur;




        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.buttun);
                mediaPlayer.start();
                saveData();

                notificationManager=NotificationManagerCompat.from(getApplicationContext());
                Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Récap de la partie :")
                        .setContentText(NomJoueur+" , "+
                                Integer.toString(VieJoueurInt-VieJoueur)+
                                " vies perdues")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(1,notification);

                intent.putExtra("RetourVie",VieJoueur);
                intent.putExtra("RetourNom",NomJoueur);
                startActivity(intent);
            }
        });

        btnTir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tir();
            }
        });

    }

    public void Play(){
        Random rand = new Random();
        ordi=rand.nextInt(7);
        if (ordi==0)ordi++;
    }

    public void Tir(){
        if(tour==ordi){
            final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.gunshot);
            mediaPlayer.start();
            tvToucheVide.setTextColor(Color.parseColor("#FF0000"));
            tvToucheVide.setText("Touché");
            VieJoueur--;
            btnTir.setText("Recommencer");
            tvVie.setText("Vie :"+Integer.toString(VieJoueur));
            tour =1;
            Play();
        }else {
            final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.revolvercock);
            mediaPlayer.start();
            btnTir.setText("Tir");
            tvToucheVide.setTextColor(Color.parseColor("#00FF00"));
            tvToucheVide.setText("Vide");
            tour++;
            point += 5;
        }
        tvPoint.setText("Point : "+Integer.toString(point));
        if(VieJoueur==0){
            btnTir.setEnabled(false);
            btnTir.setText("Plus de vie");
        }
    }

    public void saveData(){
        sharedPreferences=getSharedPreferences("Point",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        int p =sharedPreferences.getInt("Point",MODE_PRIVATE)+point;
        editor.putInt("Point",p);
        editor.commit();
    }
}

