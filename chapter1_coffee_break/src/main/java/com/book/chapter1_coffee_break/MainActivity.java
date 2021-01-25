package com.book.chapter1_coffee_break;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make the textView1 change text according to whether switchButton is "checked" or not
        Switch switchButton = (Switch) findViewById(R.id.switch_button);
        TextView textView1 = (TextView) findViewById(R.id.text_view_1);

        //textView1监听switchButton的开关操作
        RxCompoundButton.checkedChanges(switchButton)
                .subscribe(checked -> textView1.setText("状态: " + (checked ? "打开" : "关闭")));


        // Set the textView2 to say the text is too long if editText is more than 7 characters
        EditText editText = (EditText) findViewById(R.id.edit_text);
        editText.setHint("输入文字");
        TextView textView2 = (TextView) findViewById(R.id.text_view_2);

        RxTextView.textChanges(editText)
                //实时重置标题文字
                .doOnNext(text -> textView2.setText(""))
                //联动switchButton，仅当switchButton是打开状态时，才去检测字符串长度
                .filter(text -> switchButton.isChecked())
                .subscribe(text ->
                        textView2.setText(text.length() > 7 ? "您输入的字符应在七个以内" : ""));
    }
}
