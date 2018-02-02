package com.example.devalfa.alfamines;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Random;

import static com.example.devalfa.alfamines.mineButton.clicks;

public class Game extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{
    static int diff;
    LinearLayout rootLayout;
    static mineButton barr[][];
    Instant start,end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        diff  = 1;
        Intent parent = getIntent();
        String level = parent.getStringExtra("level");
        stringToInt(level);
        rootLayout = findViewById(R.id.rootLayout);
        setBoard();


    }

    private void stringToInt(String level)
    {
        if(level.matches("Easy")) diff = 1;
        else if(level.matches("Medium")) diff = 2;
        else diff = 2;
    }

    private void setBoard()
    {
        rootLayout.removeAllViews();
        barr = new mineButton[8*diff][6*diff] ;
        clicks = 0;
        for(int i=0;i<8*diff;i++)
        {
            LinearLayout row = new LinearLayout(Game.this);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);

            row.setLayoutParams(rowParams);
            for(int j=0;j<6*diff;j++)
            {
                mineButton b = new mineButton(Game.this);
                b.setOnClickListener(Game.this);
                b.setOnLongClickListener(Game.this);
                barr[i][j] = b;
                b.setRow(i);
                b.setCol(j);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                b.setLayoutParams(buttonParams);
                row.addView(b);
            }
            rootLayout.addView(row);



        }
        setMines();
        setNumbers();
        Timestamp start = new Timestamp(System.currentTimeMillis());
    }

    private void setMines() {
        for(int i=0;i<10*diff;i++)
        {
            Random random = new Random();
            int r = random.nextInt(8*diff),c=random.nextInt(6*diff);
            mineButton b = barr[r][c];
            if(b!=null&&!b.isMine()) b.setMine(true);
            else i--;


        }
    }

    @Override
    public void onClick(View view)
    {

        mineButton curr = (mineButton)view;
        if(curr.isFlag()) return;

        if(clicks==0&&curr.isMine()) {
            curr.setMine(false);
            setNumbers();

        }
        if(clicks==0)
        {
            if(curr.getNorth()!=null&&!curr.getNorth().isMine()) curr.getNorth().blast();
            if(curr.getSouth()!=null&&!curr.getSouth().isMine()) curr.getSouth().blast();
            if(curr.getEast()!=null&&!curr.getEast().isMine()) curr.getEast().blast();
            if(curr.getWest()!=null&&!curr.getWest().isMine()) curr.getWest().blast();
            if(curr.getNorth()!=null&&curr.getNorth().getEast()!=null&&!curr.getNorth().getEast().isMine()) curr.getNorth().getEast().blast();
            if(curr.getNorth()!=null&&curr.getNorth().getWest()!=null&&!curr.getNorth().getWest().isMine()) curr.getNorth().getWest().blast();
            if(curr.getSouth()!=null&&curr.getSouth().getEast()!=null&&!curr.getSouth().getEast().isMine()) curr.getSouth().getEast().blast();
            if(curr.getSouth()!=null&&curr.getSouth().getWest()!=null&&!curr.getSouth().getWest().isMine()) curr.getSouth().getWest().blast();
        }
        if(checkOver()) gameOver();
        if(curr.blast())
        {
            gameOver();
        }


    }

    private boolean checkOver() {
        boolean over = true;
        for(int i=0;i<8*diff;i++) {

            for (int j = 0; j < 6 * diff; j++) {
                if(!barr[i][j].isMine()&&!barr[i][j].isBlast()) over=false;
            }
        }
        return over;
    }

    private void setNumbers() {
        for(int i=0;i<8*diff;i++) {

            for (int j = 0; j < 6 * diff; j++) {
                if(!barr[i][j].isMine()) barr[i][j].setSurround(0);
            }
        }
        for(int i=0;i<8*diff;i++) {

            for (int j = 0; j < 6 * diff; j++) {
                mineButton curr = barr[i][j];
                if(curr.isMine())
                {
                    if(curr.getNorth()!=null) curr.getNorth().incrementSurround();
                    if(curr.getSouth()!=null) curr.getSouth().incrementSurround();
                    if(curr.getEast()!=null) curr.getEast().incrementSurround();
                    if(curr.getWest()!=null) curr.getWest().incrementSurround();
                    if(curr.getNorth()!=null&&curr.getNorth().getEast()!=null) curr.getNorth().getEast().incrementSurround();
                    if(curr.getNorth()!=null&&curr.getNorth().getWest()!=null) curr.getNorth().getWest().incrementSurround();
                    if(curr.getSouth()!=null&&curr.getSouth().getEast()!=null) curr.getSouth().getEast().incrementSurround();
                    if(curr.getSouth()!=null&&curr.getSouth().getWest()!=null) curr.getSouth().getWest().incrementSurround();
                }
            }
        }

    }


    private void gameOver()
    {
        for(int i=0;i<8*diff;i++) {

            for (int j = 0; j < 6 * diff; j++) {
                barr[i][j].blast();
            }
        }
        Toast.makeText(this,"Game Over with score"+clicks,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongClick(View view) {
        mineButton m = (mineButton)view;
        m.setFlag(!m.isFlag());

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        if(item_id==R.id.e)
        {
            diff=1;
            setBoard();
        }
        else if(item_id==R.id.m)
        {
            diff = 2;
            setBoard();
        }
        else if(item_id==R.id.h)
        {
            diff=2;
            setBoard();
        }
        else if(item_id==R.id.reset)
        {
            setBoard();
        }
        return super.onOptionsItemSelected(item);
    }
}