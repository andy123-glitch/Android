package com.example.monapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Joueur extends AppCompatActivity {

    public  static final  String CHANNEL_ID="channel1";
    private ArrayList<JoueurModel> listJoueur =new ArrayList<JoueurModel>();
    private SharedPreferences sharedPreferences;
    private TextView tvPoint;
    private int num;

    public String ListeJoueur() {
        String s="";
        for (int i = 0; i < listJoueur.size() ; i++) {
            if (listJoueur.get(i).getVie()!=0) s+=i+" : "+listJoueur.get(i).toString()+"\n";
        }
        return s;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        listJoueur.add(new JoueurModel("test",10));
        listJoueur.add(new JoueurModel("Mario",10));
        listJoueur.add(new JoueurModel("John Cena",8));
        listJoueur.add(new JoueurModel("Kazuya",9));
        listJoueur.add(new JoueurModel("Hagrid",7));
        listJoueur.add(new JoueurModel("Thor",5));
        listJoueur.add(new JoueurModel("Luigi",11));
        listJoueur.add(new JoueurModel("Cloud",6));
        listJoueur.add(new JoueurModel("Pikachu",4));
        listJoueur.add(new JoueurModel("Joker",15));
        listJoueur.add(new JoueurModel("Tweek",22));
        updateList();
        TextView txtAfficheJoueur=(TextView)findViewById(R.id.txtListePlayer);
        tvPoint=(TextView)findViewById(R.id.tvPointpJoueur);
        txtAfficheJoueur.setText(ListeJoueur());
        updateData();
        createNotificationChanels();

    }

    public  void OnClickBtnOk(View view){
        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.buttun);
        mediaPlayer.start();
        EditText number=(EditText)findViewById(R.id.edtxtNumber);
        if (number.getText().toString().isEmpty()==false){
            num=Integer.parseInt(number.getText().toString());
            if (num> listJoueur.size()-1 ){
                Toast.makeText(this,"Numero Invalide",Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("NomJoueur",listJoueur.get(num).getPseudo());
                intent.putExtra("VieJoueur",listJoueur.get(num).getVie());
                startActivity(intent);
                }
        }else Toast.makeText(this, "Veuillez ecrire un chiffre", Toast.LENGTH_SHORT).show();
    }


    public void updateData(){
        sharedPreferences=getSharedPreferences("Point",MODE_PRIVATE);
        int p =sharedPreferences.getInt("Point",MODE_PRIVATE);
        tvPoint.setText("Point : "+Integer.toString(p));
    }

    public void updateList(){
        int n =getIntent().getIntExtra("RetourVie",0);
        listJoueur.get(num).setVie(n);

    }

    public void createNotificationChanels(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel(
                    CHANNEL_ID,
                    "Channel",
                    NotificationManager.IMPORTANCE_HIGH

            );
            channel.setDescription("This is channel");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }



}