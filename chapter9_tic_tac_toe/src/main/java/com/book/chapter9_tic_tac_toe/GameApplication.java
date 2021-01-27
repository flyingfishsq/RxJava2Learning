package com.book.chapter9_tic_tac_toe;

import android.app.Application;

import com.book.chapter9_tic_tac_toe.data.GameModel;

public class GameApplication extends Application {
    private GameModel gameModel;

    @Override
    public void onCreate() {
        super.onCreate();
        gameModel = new GameModel(this);
    }

    public GameModel getGameModel() {
        return gameModel;
    }
}
