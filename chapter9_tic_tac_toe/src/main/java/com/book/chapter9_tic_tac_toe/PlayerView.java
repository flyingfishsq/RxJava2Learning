package com.book.chapter9_tic_tac_toe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.book.chapter9_tic_tac_toe.pojo.GameSymbol;

public class PlayerView extends ImageView {
    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(GameSymbol gameSymbol) {
        switch(gameSymbol) {
            case BLACK:
                setImageResource(R.drawable.symbol_black_circle);
                break;
            case RED:
                setImageResource(R.drawable.symbol_red_circle);
                break;
            default:
                setImageResource(0);
        }
    }
}
