package com.book.chapter8_tic_tac_toe;

import com.book.chapter8_tic_tac_toe.pojo.GameGrid;
import com.book.chapter8_tic_tac_toe.pojo.GameState;
import com.book.chapter8_tic_tac_toe.pojo.GameStatus;
import com.book.chapter8_tic_tac_toe.pojo.GameSymbol;

public class GameUtils {
    public static GameStatus calculateGameStatus(GameState gameState) {
        GameSymbol winner = calculateWinnerForGrid(gameState.getGameGrid());
        if (winner != null) {
            return GameStatus.ended(winner);
        }
        return GameStatus.ongoing();
    }

    public static GameSymbol calculateWinnerForGrid(GameGrid gameGrid) {
        final int WIDTH = gameGrid.getWidth();
        final int HEIGHT = gameGrid.getHeight();
        for (int r = 0; r < WIDTH; r++) {
            for (int c = 0; c < HEIGHT; c++) {
                GameSymbol player = gameGrid.getSymbolAt(r, c);
                if (player == GameSymbol.EMPTY)
                    continue;

                if (c + 2 < WIDTH &&
                        player == gameGrid.getSymbolAt(r, c+1) &&
                        player == gameGrid.getSymbolAt(r, c+2))
                    return player;
                if (r + 2 < HEIGHT) {
                    if (player == gameGrid.getSymbolAt(r+1, c) &&
                            player == gameGrid.getSymbolAt(r+2, c))
                        return player;
                    if (c + 2 < WIDTH &&
                            player == gameGrid.getSymbolAt(r+1, c+1) &&
                            player == gameGrid.getSymbolAt(r+2, c+2))
                        return player;
                    if (c - 2 >= 0 &&
                            player == gameGrid.getSymbolAt(r+1, c-1) &&
                            player == gameGrid.getSymbolAt(r+2, c-2))
                        return player;
                }
            }
        }
        return null;
    }
}
