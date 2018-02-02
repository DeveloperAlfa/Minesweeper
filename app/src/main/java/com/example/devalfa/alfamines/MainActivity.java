package com.example.devalfa.alfamines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    RadioGroup g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         g = findViewById(R.id.group);


    }
    public void start(View view)
    {
        RadioButton checked = findViewById(g.getCheckedRadioButtonId());
        String level = checked.getText().toString();
        Intent intent = new Intent(this,Game.class);
        intent.putExtra("level",level);
        startActivity(intent);

    }



}
