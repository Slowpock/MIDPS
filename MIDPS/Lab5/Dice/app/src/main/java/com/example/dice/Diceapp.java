package com.example.dice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Diceapp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle Hinstance){
        super.onCreate(Hinstance);
        setContentView(R.layout.activity_dice);
        Button Start=(Button)findViewById(R.id.Startbutton);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Diceapp.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
