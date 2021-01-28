package com.book.chapter14_maps_client.network;

import android.util.Log;

import com.book.chapter14_maps_client.Tile;
import com.book.chapter14_maps_client.TileBitmap;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MapTileNetworkUtils {
    private static final String TAG = MapTileNetworkUtils.class.getSimpleName();

    static public Function<Tile, Observable<TileBitmap>> loadMapTile(
            final MapNetworkAdapter mapNetworkAdapter) {
        return mapTile -> mapNetworkAdapter.getMapTile(
                mapTile.getZoom(), mapTile.getX(), mapTile.getY())
                .map(bitmap -> new TileBitmap(mapTile, bitmap))
                .onErrorResumeNext(throwable -> {
                    Log.e(TAG, "Error loading tile (" + mapTile + ")", throwable);
                    throwable.printStackTrace();
                    return Observable.just(new TileBitmap(mapTile, null));
                });
    }
}
