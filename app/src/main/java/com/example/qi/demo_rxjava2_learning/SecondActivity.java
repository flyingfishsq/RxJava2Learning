package com.example.qi.demo_rxjava2_learning;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.qi.demo_rxjava2_learning.Bean.Address;
import com.example.qi.demo_rxjava2_learning.Bean.User;
import com.example.qi.demo_rxjava2_learning.Bean.UserPlus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SecondActivity extends Activity {

    private final String TAG = SecondActivity.class.getSimpleName();

    private Button btn_map;

    private Button btn_flatmap;

    private Button btn_flatmap_plus;

    private TextView tv_result;

    private TextView tv_base_data;

    private MyOnClickListener myOnClickListener;

    private List<User> users;

    private List<UserPlus> userPluses;

    private int[] data = {91, 92, 93, 94, 95, 96, 97};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(34000 + i);
            user.setName("张小" + i);

            users.add(user);
        }

        createUserPluses();

        myOnClickListener = new MyOnClickListener();

        tv_base_data = (TextView) findViewById(R.id.tv_base_data);
        tv_result = (TextView) findViewById(R.id.tv_result);

        btn_map = (Button) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(myOnClickListener);

        btn_flatmap = (Button) findViewById(R.id.btn_flatmap);
        btn_flatmap.setOnClickListener(myOnClickListener);

        btn_flatmap_plus = (Button) findViewById(R.id.btn_flatmap_plus);
        btn_flatmap_plus.setOnClickListener(myOnClickListener);

        tv_base_data.setText("");
    }

    private void createUserPluses() {
        userPluses = new ArrayList<UserPlus>();

        UserPlus up1 = new UserPlus();
        up1.useName = "张三";
        up1.addresses = new ArrayList<Address>();
        up1.addresses.add(new Address("人民路", "苏州"));
        up1.addresses.add(new Address("南山路", "杭州"));
        up1.addresses.add(new Address("长江路", "芜湖"));

        UserPlus up2 = new UserPlus();
        up2.useName = "李四";
        up2.addresses = new ArrayList<Address>();
        up2.addresses.add(new Address("外环路", "肥东"));
        up2.addresses.add(new Address("青年路", "肥西"));
        up2.addresses.add(new Address("深圳路", "合肥"));

        UserPlus up3 = new UserPlus();
        up3.addresses = new ArrayList<Address>();
        up3.useName = "王五";
        up3.addresses.add(new Address("天坛路", "北京"));
        up3.addresses.add(new Address("外滩路", "上海"));

        userPluses.add(up1);
        userPluses.add(up2);
        userPluses.add(up3);
    }

    private UserPlus createSingleUser(){
        UserPlus up2 = new UserPlus();
        up2.useName = "李四";
        up2.addresses.add(new Address("外环路", "肥东"));
        up2.addresses.add(new Address("青年路", "肥西"));
        up2.addresses.add(new Address("深圳路", "合肥"));

        return up2;
    }

    private String content;
    private StringBuilder sb;

    class MyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            content = "";
            sb = new StringBuilder();
            tv_result.setText(content);
            switch (v.getId()) {
                case R.id.btn_map:
                    tv_base_data.setText(users.toString());

                    convertObservableByMap().subscribe(new Observer<String>() {

                        @Override
                        public void onComplete() {
                            Log.e(TAG, "执行了...onComplete");
                            content = sb.toString();

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    tv_result.setText(content);
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable arg0) {
                            Log.e(TAG, arg0.getMessage());
                        }

                        @Override
                        public void onNext(String arg0) {
                            Log.e(TAG, "打印..." + arg0.toString());
                            sb.append(arg0 + "\n");
                        }

                        @Override
                        public void onSubscribe(Disposable arg0) {
                            Log.e(TAG, "执行了...onSubscribe");
                        }
                    });
                    break;

                case R.id.btn_flatmap:
                    StringBuilder sb2 = new StringBuilder();
                    for (int i = 0; i < data.length; i++) {
                        sb2.append(data[i]).append("\n");
                    }

                    tv_base_data.setText(sb2.toString());

                    convertObservableByFlatMap().subscribe(new Observer<String>() {

                        @Override
                        public void onComplete() {
                            Log.e(TAG, "执行了...onComplete");
                            content = sb.toString();

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    tv_result.setText(content);
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable arg0) {
                            Log.e(TAG, arg0.getMessage());
                        }

                        @Override
                        public void onNext(String arg0) {
                            Log.e(TAG, "打印..." + arg0);
                            sb.append(arg0 + "\n");
                        }

                        @Override
                        public void onSubscribe(Disposable arg0) {
                            Log.e(TAG, "执行了...onSubscribe");
                        }
                    });
                    break;
                case R.id.btn_flatmap_plus:
                    tv_base_data.setText(userPluses.toString());

                    /**
                     * 分为四个观察者分别处理对应的事件
                     */
                    convertObservableByFlatMapPlus().subscribe(new Consumer<Address>() {
                        @Override
                        public void accept(Address address) throws Exception {
                            Log.e(TAG, "打印..." + address);
                            sb.append(address + "\n");
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e(TAG, throwable.getMessage());
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            Log.e(TAG, "执行了...onComplete");
                            content = sb.toString();

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    tv_result.setText(content);
                                }
                            });
                        }
                    }, new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            Log.e(TAG, "执行了...onSubscribe");
                        }
                    });

/**
 * 一个观察者处理四种事件
 */
//                    convertObservableByFlatMapPlus().subscribe(new Observer<Address>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            Log.e(TAG, "执行了...onSubscribe");
//                        }
//
//                        @Override
//                        public void onNext(Address address) {
//                            Log.e(TAG, "打印..." + address);
//                            sb.append(address + "\n");
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e(TAG, e.getMessage());
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            Log.e(TAG, "执行了...onComplete");
//                            content = sb.toString();
//
//                            runOnUiThread(new Runnable() {
//
//                                @Override
//                                public void run() {
//                                    tv_result.setText(content);
//                                }
//                            });
//                        }
//                    });
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 用map方式转换Observable
     *
     * @return
     */
    private Observable<String> convertObservableByMap() {
        Observable<String> observable = Observable.fromIterable(
                (Iterable<User>) users).map(new Function<User, String>() {

            @Override
            public String apply(User user) throws Exception {
                return user.getName();
            }
        });
        return observable;
    }

    /**
     * 为事件序列中每个事件都创建一个 Observable 对象； 将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
     * 将新建的每个Observable 都合并到一个 新建的、总的Observable 对象； 新建的、总的Observable 对象 将
     * 新合并的事件序列 发送给观察者（Observer）
     * <p>
     * 无序的将被观察者发送的整个事件序列进行变换
     *
     * @return
     */
    private Observable<String> convertObservableByFlatMap() {
        Observable<String> observable = Observable.just(91, 92, 93, 94, 95, 96,
                97).flatMap(new Function<Integer, ObservableSource<String>>() {

            @Override
            public ObservableSource<String> apply(Integer integer)
                    throws Exception {
                List<String> list = new ArrayList<String>();

                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                }

                return Observable.fromIterable(list);
            }
        });

        return observable;
    }

    private Observable<Address> convertObservableByFlatMapPlus() {
        Observable<Address> observable = Observable.fromIterable((Iterable<UserPlus>)userPluses).flatMap(new Function<UserPlus, ObservableSource<Address>>() {
            @Override
            public ObservableSource<Address> apply(UserPlus userPlus) throws Exception {
                return Observable.fromIterable(userPlus.addresses);
            }
        });
        return observable;
    }

}
