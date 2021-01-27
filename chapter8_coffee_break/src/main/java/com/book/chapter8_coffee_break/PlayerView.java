package com.book.chapter8_coffee_break;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.book.chapter8_coffee_break.pojo.GameSymbol;

public class PlayerView extends AppCompatImageView {
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
            case CIRCLE:
                setImageResource(R.drawable.symbol_circle);
                break;
            case CROSS:
                setImageResource(R.drawable.symbol_cross);
                break;
            case TRIANGLE:
                setImageResource(R.drawable.symbol_triangle);
                break;
            default:
                setImageResource(0);
        }
    }
}
