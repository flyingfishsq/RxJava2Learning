package com.example.chapter2_news_reader;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter2_news_reader.network.Entry;
import com.example.chapter2_news_reader.network.FeedObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tv_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_no_data = findViewById(R.id.tv_no_data);

        List<String> feedUrls = Arrays.asList(
                "https://news.google.com/?output=atom",
//                "http://www.theregister.co.uk/software/headlines.atom",
                "https://www.theregister.com/software/headlines.atom",
                "http://www.linux.com/news/soware?format=feed&type=atom"
        );

        List<Observable<List<Entry>>> observableList = new ArrayList<>();
        for (String feedUrl : feedUrls) {
            final Observable<List<Entry>> feedObservable =
                    FeedObservable.getFeed(feedUrl)
                            .onErrorReturn(
                                    e -> {
                                        tv_no_data.setText(e.getLocalizedMessage());
                                        return new ArrayList<Entry>();
                                    });
            observableList.add(feedObservable);
        }

        Observable<List<Entry>> combinedObservable =
                Observable.combineLatest(observableList,
                        (listOfLists) -> {
                            final List<Entry> combinedList = new ArrayList<>();
                            for (Object list : listOfLists) {
                                combinedList.addAll((List<Entry>) list);
                            }
                            return combinedList;
                        }
                );

        combinedObservable
                .map(list -> {
                    List<Entry> sortedList = new ArrayList<>();
                    sortedList.addAll(list);
                    Collections.sort(sortedList);
                    return sortedList;
                })
                .flatMap(Observable::<Entry>fromIterable)
                .take(20)
                .map(Entry::toString)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::drawList);
    }

    private void drawList(List<String> listItems) {
        if (listItems.size() > 0) {
            tv_no_data.setVisibility(View.GONE);
        }
        final ListView list = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(itemsAdapter);
    }
}
