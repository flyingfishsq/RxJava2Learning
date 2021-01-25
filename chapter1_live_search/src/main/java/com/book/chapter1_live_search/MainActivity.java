package com.book.chapter1_live_search;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.search_results);
        listView.setAdapter(arrayAdapter);

        EditText editText = (EditText) findViewById(R.id.edit_text);
        RxTextView.textChanges(editText)
                //每次数据改变之前清空结果
                .doOnNext(text -> this.clearSearchResults())
                //一个字符以上才响应
                .filter(text -> text.length() >= 1)
                //设定事件间隔时间
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateSearchResults);
    }

    private void clearSearchResults() {
        //直接使用adapter的clear方法，而不是将list先clear再调用adater的notifyDataChanged
        arrayAdapter.clear();
    }

    private void updateSearchResults(CharSequence text) {
        // Create some random results
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add("" + text + Math.random());
        }

        arrayAdapter.clear();
        //直接使用adapter的addAll方法，而不是改变adapter的成员变量list，再调用adater的notifyDataChanged
        arrayAdapter.addAll(list);
    }
}
