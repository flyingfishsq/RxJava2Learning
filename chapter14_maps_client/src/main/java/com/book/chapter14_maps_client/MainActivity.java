package com.book.chapter14_maps_client;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.book.chapter14_maps_client.network.MapNetworkAdapter;
import com.book.chapter14_maps_client.network.MapNetworkAdapterSimple;
import com.book.chapter14_maps_client.network.NetworkClient;
import com.book.chapter14_maps_client.network.NetworkClientOkHttp;
import com.book.chapter14_maps_client.network.TileBitmapLoader;
import com.book.chapter14_maps_client.utils.CoordinateProjection;
import com.book.chapter14_maps_client.utils.LatLng;
import com.book.chapter14_maps_client.utils.MapTileUtils;
import com.book.chapter14_maps_client.utils.PointD;
import com.book.chapter14_maps_client.utils.Triple;
import com.book.chapter14_maps_client.utils.ViewUtils;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkClient networkClient = new NetworkClientOkHttp();
        MapNetworkAdapter mapNetworkAdapter = new MapNetworkAdapterSimple(networkClient,
                "https://b.tile.openstreetmap.org/%d/%d/%d.png");
        TileBitmapLoader tileBitmapLoader = new TileBitmapLoader(mapNetworkAdapter);

        final double TILE_SIZE = 256;

        ViewUtils.TouchDelta touchDelta = new ViewUtils.TouchDelta();
        TilesView tilesView = (TilesView) findViewById(R.id.tiles_view);
        tilesView.setOnTouchListener(touchDelta);
        tilesView.setTileBitmapLoader(tileBitmapLoader);

        BehaviorSubject<Integer> zoom = BehaviorSubject.createDefault(10);
        RxView.clicks(findViewById(R.id.zoom_in_button))
                .subscribe(ignore -> zoom.onNext(zoom.getValue() + 1));
        RxView.clicks(findViewById(R.id.zoom_out_button))
                .subscribe(ignore -> zoom.onNext(zoom.getValue() - 1));

        Observable<PointD> tilesViewSize = tilesView.getViewSize();
        BehaviorSubject<LatLng> mapCenter =
                BehaviorSubject.createDefault(new LatLng(51.509865, -0.118092));
        CoordinateProjection coordinateProjection =
                new CoordinateProjection((int) TILE_SIZE);
        Observable<PointD> offset =
                Observable.combineLatest(
                        tilesViewSize, mapCenter, zoom,
                        (tilesViewSizeValue, mapCenterValue, zoomValue) ->
                                MapTileUtils.calculateOffset(coordinateProjection,
                                        zoomValue, tilesViewSizeValue, mapCenterValue));

        Observable<Triple<PointD, PointD, Integer>> mapState =
                Observable.combineLatest(
                        tilesViewSize, offset, zoom, Triple::new);
        touchDelta.getObservable()
                .withLatestFrom(mapState,
                        (pixelDelta, mapStateValue) -> {
                            Log.v(TAG, "pixelDelta(" + pixelDelta + ")");
                            final double cx = mapStateValue.first.x / 2.0 - mapStateValue.second.x;
                            final double cy = mapStateValue.first.y / 2.0 - mapStateValue.second.y;
                            final PointD newPoint = new PointD(cx - pixelDelta.x, cy - pixelDelta.y);
                            return coordinateProjection.fromPointToLatLng(newPoint, mapStateValue.third);
                        })
                .subscribe(mapCenter::onNext);


        Observable.combineLatest(
                tilesViewSize, offset, zoom,
                (tilesViewSizeValue, offsetValue, zoomValue) ->
                        MapTileUtils.calculateMapTiles(
                                TILE_SIZE, zoomValue, tilesViewSizeValue, offsetValue
                        ))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tilesView::setTiles);
    }
}
