package com.example.dice;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView dice_picture;
    Random rng=new Random();
    SoundPool dice_sound;
    int sound_id;
    Handler handler;
    Timer timer=new Timer();
    boolean rolling=false;
    int check=0,value;
    String res;
    Button odd,even;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitSound();
        even=(Button) findViewById(R.id.Even);
        odd=(Button) findViewById(R.id.odd);
        even.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=2;
            }
        });
        odd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=1;
            }
        });
        dice_picture = (ImageView) findViewById(R.id.imageView);
        dice_picture.setOnClickListener(new HandleClick());

        handler=new Handler(callback);
    }


    private class HandleClick implements View.OnClickListener {
        public void onClick(View arg0) {
            if (!rolling) {
                rolling = true;

                dice_picture.setImageResource(R.drawable.dice3droll);

                dice_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);

                timer.schedule(new Roll(), 400);
            }
        }
    }

    void InitSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AudioAttributes aa = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            dice_sound= new SoundPool.Builder().setAudioAttributes(aa).build();

        } else {

            dice_sound=PreLollipopSoundPool.NewSoundPool();
        }

        sound_id=dice_sound.load(this,R.raw.shake_dice,1);
    }


    class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }


    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            value=rng.nextInt(6)+1;
            switch(value) {
                case 1:
                    dice_picture.setImageResource(R.drawable.one);
                    break;
                case 2:
                    dice_picture.setImageResource(R.drawable.two);
                    break;
                case 3:
                    dice_picture.setImageResource(R.drawable.three);
                    break;
                case 4:
                    dice_picture.setImageResource(R.drawable.four);
                    break;
                case 5:
                    dice_picture.setImageResource(R.drawable.five);
                    break;
                case 6:
                    dice_picture.setImageResource(R.drawable.six);
                    break;
                default:
            }
            if(check==0)res="You need to choose";
            if(value%2!=0){
                if(check==1)res="You've won";
                if(check==2)res="You loose";
            }
            else{
                if(check==1)res="You loose";
                if(check==2)res="You won";
            }
            Toast toast=Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG);
            toast.show();

            rolling=false;
            return true;
        }
    };

    protected void onPause() {
        super.onPause();
        dice_sound.pause(sound_id);
    }
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
