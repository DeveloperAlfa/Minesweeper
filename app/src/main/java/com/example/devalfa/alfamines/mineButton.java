package com.example.devalfa.alfamines;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by devalfa on 1/2/18.
 */

public class mineButton extends AppCompatButton
{
    boolean mine=false;
    boolean flag = false;
    boolean blast = false;
    int surround=0;
    int row,col;
    static int clicks;
    public mineButton(Context context) {
        super(context);
        setBackgroundResource(R.drawable.button);
        setTextSize(1,10);
    }

    public void setSurround(int surround) {
        this.surround = surround;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
        surround = -1;
    }

    public boolean isMine()
    {
        return mine;
    }

    public void incrementSurround()
    {
        if(!mine) this.surround++;
    }
    public boolean blast()
    {
        if(mine)
        {
            setBackgroundResource(R.drawable.bomb);
        }
        else
        {
            blast = true;
            setTextColor(getResources().getColor(R.color.blue));

            if(surround!=0) setText(surround+"");

            else
            {
                if(hasNorth()) Game.barr[getRow()-1][getCol()].blast(!Game.barr[getRow()-1][getCol()].isBlast()&&!Game.barr[getRow()-1][getCol()].isMine());
                if(hasSouth()) Game.barr[getRow()+1][getCol()].blast(!Game.barr[getRow()+1][getCol()].isBlast()&&!Game.barr[getRow()+1][getCol()].isMine());
                if(hasEast()) Game.barr[getRow()][getCol()+1].blast(!Game.barr[getRow()][getCol()+1].isBlast()&&!Game.barr[getRow()][getCol()+1].isMine());
                if(hasWest()) Game.barr[getRow()][getCol()-1].blast(!Game.barr[getRow()][getCol()-1].isBlast()&&!Game.barr[getRow()][getCol()-1].isMine());
                if(hasNorth()&&hasWest()) Game.barr[getRow()-1][getCol()-1].blast(!Game.barr[getRow()-1][getCol()-1].isBlast()&&!Game.barr[getRow()-1][getCol()-1].isMine());
                if(hasNorth()&&hasEast()) Game.barr[getRow()-1][getCol()+1].blast(!Game.barr[getRow()-1][getCol()+1].isBlast()&&!Game.barr[getRow()-1][getCol()+1].isMine());
                if(hasSouth()&&hasWest()) Game.barr[getRow()+1][getCol()-1].blast(!Game.barr[getRow()+1][getCol()-1].isBlast()&&!Game.barr[getRow()+1][getCol()-1].isMine());
                if(hasSouth()&&hasEast()) Game.barr[getRow()+1][getCol()+1].blast(!Game.barr[getRow()+1][getCol()+1].isBlast()&&!Game.barr[getRow()+1][getCol()+1].isMine());

            }
            setEnabled(false);
            setBackgroundResource(R.drawable.selected);


        }
        clicks++;
        return isMine();
    }

    public boolean blast(boolean b)
    {
        if(!b) return isMine();
        return blast();
    }

    public boolean hasSouth() {
        if(getRow()<8*Game.diff-1) return true;
        return false;
    }
    public boolean hasEast() {
        if(getCol()<6*Game.diff-1) return true;
        return false;
    }

    public boolean hasNorth() {
        if(getRow()!=0) return true;
        return false;
    }
    public boolean hasWest() {
        if(getCol()!=0) return true;
        return false;
    }

    public static int getClicks() {
        return clicks;
    }

    public void setFlag(boolean flag) {

        this.flag = flag;
        if(isFlag())
        {
            setBackgroundResource(R.drawable.flag);
            setText("Flagged");

        }
        else {
            setBackgroundResource(R.drawable.button);
            setText("");

        }
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isBlast()
    {
        return blast;
    }

    public mineButton getNorth()
    {
        if(!hasNorth()) return null;
        return Game.barr[getRow()-1][getCol()];
    }
    public mineButton getEast()
    {
        if(!hasEast()) return null;
        return Game.barr[getRow()][getCol()+1];
    }
    public mineButton getSouth()
    {
        if(!hasSouth()) return null;
        return Game.barr[getRow()+1][getCol()];

    }
    public mineButton getWest()
    {
        if(!hasWest()) return null;
        return Game.barr[getRow()][getCol()-1];
    }
}
