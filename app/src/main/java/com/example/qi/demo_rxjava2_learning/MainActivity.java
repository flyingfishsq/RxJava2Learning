package com.example.qi.demo_rxjava2_learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qi.demo_rxjava2_learning.Bean.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.ConsumerSingleObserver;

public class MainActivity extends Activity {

    private final String TAG = MainActivity.class.getSimpleName();

    private Button btn_dosomething;

    private Button btn_createByJust;

    private Button btn_createByFrom;

    private Button btn_jump;

    private Button btn_jump2;

    private Button btn_jump3;

    private Button btn_jump4;

    private TextView tv_result;

    private MyOnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myOnClickListener = new MyOnClickListener();

        btn_dosomething = (Button) findViewById(R.id.btn_dosomething);
        btn_dosomething.setOnClickListener(myOnClickListener);

        btn_createByJust = (Button) findViewById(R.id.btn_createByJust);
        btn_createByJust.setOnClickListener(myOnClickListener);

        btn_createByFrom = (Button) findViewById(R.id.btn_createByFrom);
        btn_createByFrom.setOnClickListener(myOnClickListener);

        btn_jump = (Button) findViewById(R.id.btn_jump);
        btn_jump.setOnClickListener(myOnClickListener);

        btn_jump2 = (Button) findViewById(R.id.btn_jump2);
        btn_jump2.setOnClickListener(myOnClickListener);

        btn_jump3 = (Button) findViewById(R.id.btn_jump3);
        btn_jump3.setOnClickListener(myOnClickListener);


        btn_jump4 = (Button) findViewById(R.id.btn_jump4);
        btn_jump4.setOnClickListener(myOnClickListener);

        tv_result = (TextView) findViewById(R.id.tv_result);
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_dosomething:
                    getObservable().subscribe(new Observer<Integer>() {

                        @Override
                        public void onComplete() {
                            mDisposable.dispose();
                            Log.e(TAG, "执行了...onComplete");
                        }

                        @Override
                        public void onError(Throwable arg0) {
                            Log.e(TAG, arg0.getMessage());
                        }

                        @Override
                        public void onSubscribe(Disposable arg0) {
                            mDisposable = arg0;
                            Log.e(TAG, "执行了...onSubscribe");
                        }

                        @Override
                        public void onNext(Integer arg0) {
                            if (!mDisposable.isDisposed()) {
                                Log.e(TAG, "打印..." + arg0);
                            }

                            if (arg0 == 2) {
                                mDisposable.dispose();
                                Log.e(TAG, "已经切断了连接..." + mDisposable.isDisposed());
                            }
                        }

                    });
                    break;
                case R.id.btn_createByJust:
                    getObservableByJust().subscribe(new Observer<String>() {
                        @Override
                        public void onComplete() {
                            mDisposable.dispose();
                            Log.e(TAG, "执行了...onComplete");
                        }

                        @Override
                        public void onError(Throwable arg0) {
                            Log.e(TAG, arg0.getMessage());
                        }

                        @Override
                        public void onSubscribe(Disposable arg0) {
                            mDisposable = arg0;
                            Log.e(TAG, "执行了...onSubscribe");
                        }

                        @Override
                        public void onNext(String arg0) {
                            if (!mDisposable.isDisposed()) {
                                Log.e(TAG, "打印..." + arg0);
                            }
                        }
                    });
                case R.id.btn_createByFrom:
                    getObservableByFrom().subscribe(new Observer<User>() {

                        @Override
                        public void onComplete() {
                            mDisposable.dispose();
                            Log.e(TAG, "执行了...onComplete");
                        }

                        @Override
                        public void onError(Throwable arg0) {
                            Log.e(TAG, arg0.getMessage());
                        }

                        @Override
                        public void onNext(User arg0) {
                            if (!mDisposable.isDisposed()) {
                                Log.e(TAG, "打印..." + arg0.toString());
                            }
                        }

                        @Override
                        public void onSubscribe(Disposable arg0) {
                            mDisposable = arg0;
                            Log.e(TAG, "执行了...onSubscribe");
                        }
                    });
                    break;
                case R.id.btn_jump:
                    startActivity(new Intent(MainActivity.this,
                            SecondActivity.class));
                    break;
                case R.id.btn_jump2:
                    startActivity(new Intent(MainActivity.this, ThirdActivity.class));
                case R.id.btn_jump3:
                    startActivity(new Intent(MainActivity.this, FourthActivity.class));
                case R.id.btn_jump4:
                    createSingle().subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            tv_result.setText(s);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            tv_result.setText(throwable.getMessage());
                        }
                    });

                    //或写成下面这种方式，但是不知道这个的意思
//                    createSingle().subscribe(new BiConsumer<String, Throwable>() {
//                        @Override
//                        public void accept(String s, Throwable throwable) throws Exception {
//                            tv_result.setText(s);
//                        }
//                    });
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 有关Rxjava的测试代码
     *
     * @return
     */
    private Disposable mDisposable;

    private Observable<Integer> getObservable() {
        Observable<Integer> observable = Observable
                .create(new ObservableOnSubscribe<Integer>() {

                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter)
                            throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onComplete();
                    }
                });

        return observable;
    }

    private Observable<String> getObservableByJust() {

        /**
         * just方式最多只能发送10个事件
         */
        Observable<String> observable = Observable.just("A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J");
        return observable;
    }

    private Observable<User> getObservableByFrom() {
        // 会将容器中的数据转换为Observable对象
        // 发送10个以上事件

        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(3400 + i);
            user.setName("王小" + i);
            users.add(user);
        }

        Observable<User> observable = Observable
                .fromIterable((Iterable<User>) users);
        return observable;
    }

    private Single<String> createSingle() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                e.onSuccess("我开始上课了");
            }
        });
    }

}

