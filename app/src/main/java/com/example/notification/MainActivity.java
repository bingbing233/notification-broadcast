package com.example.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.example.notification.app.AppListActivity;

public class MainActivity extends AppCompatActivity {

    Button authorBtn;
    ToggleButton ttsToggle,comToggle;
    SharedPreferences.Editor editor = MyApplication.getEditor();
    ImageButton sayBtn;
    EditText inputTest;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.list_menu_item:
                startActivity(new Intent(MainActivity.this, AppListActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this,MyNotificationService.class));
        authorBtn = findViewById(R.id.authority_btn);
        ttsToggle = findViewById(R.id.tts_toggle);
        comToggle = findViewById(R.id.com_toggle);
        sayBtn = findViewById(R.id.play_btn);
        inputTest = findViewById(R.id.input_test);

        authorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        });

        ttsToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             editor.putBoolean("tts",b);
             editor.apply();
            }
        });

        sayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testContent = inputTest.getText().toString();
                new MyTextToSpeech(MainActivity.this,testContent);
            }
        });

        comToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("com",b);
                editor.apply();
            }
        });
    }
}