package com.book.chapter10_tic_tac_toe;

import com.book.chapter10_tic_tac_toe.pojo.FullGameState;
import com.book.chapter10_tic_tac_toe.pojo.GameState;
import com.book.chapter10_tic_tac_toe.pojo.GameStatus;
import com.book.chapter10_tic_tac_toe.pojo.GameSymbol;
import com.book.chapter10_tic_tac_toe.pojo.GridPosition;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public class GameViewModel {
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final Observable<GameState> activeGameStateObservable;
    private final Consumer<GameState> putActiveGameState;
    private final Observable<GridPosition> touchEventObservable;

    private final BehaviorSubject<GameState> gameStateSubject = BehaviorSubject.create();

    private final Observable<GameSymbol> playerInTurnObservable;
    private final Observable<GameStatus> gameStatusObservable;

    public GameViewModel(Observable<GameState> activeGameStateObservable,
                         Consumer<GameState> putActiveGameState,
                         Observable<GridPosition> touchEventObservable) {
        this.activeGameStateObservable = activeGameStateObservable;
        this.putActiveGameState = putActiveGameState;
        this.touchEventObservable = touchEventObservable;

        playerInTurnObservable = activeGameStateObservable
                .map(GameState::getLastPlayedSymbol)
                .map(symbol -> {
                    if (symbol == GameSymbol.BLACK) {
                        return GameSymbol.RED;
                    } else {
                        return GameSymbol.BLACK;
                    }
                });

        gameStatusObservable = activeGameStateObservable
                .map(GameState::getGameGrid)
                .map(GameUtils::calculateGameStatus);
    }

    public Observable<GameStatus> getGameStatus() {
        return gameStatusObservable;
    }

    public Observable<FullGameState> getFullGameState() {
        return Observable.combineLatest(gameStateSubject, gameStatusObservable,
                FullGameState::new);
    }

    public Observable<GameSymbol> getPlayerInTurn() {
        return playerInTurnObservable;
    }

    public void subscribe() {
        subscriptions.add(activeGameStateObservable
                .subscribe(gameStateSubject::onNext)
        );

        subscriptions.add(GameUtils.processGamesMoves(
                activeGameStateObservable,
                gameStatusObservable,
                playerInTurnObservable,
                touchEventObservable
        ).subscribe(putActiveGameState));
    }

    public void unsubscribe() {
        subscriptions.clear();
    }
}
