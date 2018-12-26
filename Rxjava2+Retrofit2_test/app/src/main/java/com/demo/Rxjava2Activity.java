package com.demo;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.bean.User;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Rxjava2Activity extends AppCompatActivity {
    private static final String TAG = "RxJava___";
    private android.widget.Button button;
    private android.widget.TextView text;
    private android.widget.ScrollView mScrollView;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button1 = (Button) findViewById(R.id.button1);
        this.mScrollView = (ScrollView) findViewById(R.id.mScrollView);
        this.text = (TextView) findViewById(R.id.text);
        this.button = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCodeClick();
            }
        });
//        test();
        // test2();

        /*readAllRecords().subscribe(new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) throws Exception {

            }
        });*/

        //  change();
        // zip();
      //  flowable();
        readTxt();
        onCodeClick();
    }

    //基本使用
    public void test() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });
        Observer<Integer> observer = new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
                this.mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "value :" + value);
                if (value == 3) {
                    mDisposable.dispose();//dispose()切断,不在接收
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
        observable.subscribe(observer);

        //如果只需要onNext事件可以这么写:
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);

            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "onNext------" + integer);
            }
        });
    }

    //线程调度（子线程中做耗时的操作, 主线程中来操作UI）等同handler功能
    public void test2() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "111111");
                e.onNext(1);
                /*Log.d(TAG, "222222");
                e.onNext(2);*/
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "accept" + integer);
            }
        };
        //subscribeOn() 指定的是上游发送事件的线程, observeOn() 指定的是下游接收事件的线程
       /* observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);*/


        //多次指定上游的线程只有第一次指定的有效, 多次指定下游的线程 即每调用一次observeOn() , 下游的线程就会切换一次.
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())//(无效的)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
                    }
                })
                .subscribe(consumer);

    }


    //举例 rxjava去读取sqlite
    public Observable<List<User>> readAllRecords() {
        return Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> emitter) throws Exception {
                Cursor cursor = null;
                try {
                    // cursor = getReadableDatabase().rawQuery("select * from " + "TABLE_NAME", new String[]{});
                    List<User> result = new ArrayList<>();
                    while (cursor.moveToNext()) {
                        // result.add(Db.Record.read(cursor));
                    }
                    emitter.onNext(result);
                    emitter.onComplete();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //map是RxJava中最简单的一个变换操作符
    public void change() {

        //在上游我们发送的是数字类型, 而在下游我们接收的是String类型, 中间起转换作用的就是map操作符
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
            }
        })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "integer:" + integer;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept " + s);
                    }
                });


//我们在flatMap中将上游发来的每个事件转换为一个新的发送三个List事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                list.add("呵呵哒" + integer);
                list.add("咯咯哒" + integer);
                //ObservableSource source =Observable.fromIterable(list);
                return Observable.fromArray(list);
            }
        }).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                for (int i = 0; i < strings.size(); i++) {
                    Log.i(TAG, strings.get(i));
                }
            }
        });
    }

    //Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件
    //下游收到的事件数量 是和上游中发送事件最少的那一根水管的事件数量 相同
    public void zip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "observable1--1");
                e.onNext(1);
                Log.d(TAG, "observable1--2");
                e.onNext(2);

                //下游接受并不会执行了
                Log.d(TAG, "observable1--3");
                e.onNext(3);

                Log.i(TAG, "observable onComplete");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "observable2--aa");
                e.onNext("aa");
                Log.d(TAG, "observable2--bb");
                e.onNext("bb");
                Log.i(TAG, "observable1 onComplete");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, User>() {
            @Override
            public User apply(Integer integer, String s) throws Exception {
                return new User(integer + s);
            }
        }).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                Log.i(TAG, "user=" + user.getId());
            }
        });

        /*Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .sample(2, TimeUnit.SECONDS)  //sample取样
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "" + integer);
                    }
                });*/

    }


    Subscription mSubscription;


    //之前我们所的上游和下游分别是Observable和Observer, 这次不一样的是上游变成了Flowable, 下游变成了Subscriber,
// 但是水管之间的连接还是通过subscribe()
    public void flowable() {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubscription.request(1); //下游每request一个, 就接收一个进行处理.
            }
        });
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        //之前我们说调用Disposable.dispose()方法可以切断水管, 同样的调用Subscription.cancel()也可以切断水管
                        // 不同的地方在于Subscription增加了一个void request(long n)
                        // s.request(Long.MAX_VALUE);  //告诉上游它的处理能力 为多少

                        //下游直接调用request(Long.MAX_VALUE), 或者根据上游发送事件的数量来request就行了
                        // , 比如这里request(3)就可以了.

                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;  //把Subscription保存起来
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                        Toast.makeText(Rxjava2Activity.this, "onNext: " + integer, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });


    }

    //如何正确的去实现一个完整的响应式拉取了，在某一些场景下，
    // 可以在发送事件前先判断当前的requested的值是否大于0，若等于0则说明下游处理不过来了
    // 则需要等待，例如下面这个例子。

    //读取一个文本文件，需要一行一行读取，然后处理并输出，如果文本文件很大的时候，
    // 比如几十M的时候，全部先读入内存肯定不是明智的做法，因此我们可以一边读取一边处理，实现的代码如下：
    Subscription subscription;
    private long calcTime;
    public void readTxt() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                try {
                    AssetManager am = getAssets();
                    InputStream is = am.open("double12.txt");

                    String code = getCode(is);
                    is = am.open("double12.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, code));

                    String line ;

                    while ((line = reader.readLine()) != null && !e.isCancelled()) {
                        while (e.requested() == 0) {
                            if (e.isCancelled()) {
                                break;
                            }
                        }
                        try {
                            Thread.sleep(50);//让上游50发一次消息给下游
                            e.onNext(line);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }


                    reader.close();
                    e.onComplete();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        s.request(1);
                        calcTime=System.currentTimeMillis();
                    }

                    @Override
                    public void onNext(String s) {


                        System.out.println(s);
                        text.append(s + "\n");
//                        try {
//                            Thread.sleep(1000);
                            subscription.request(1);//这个request是累加的，每执行一次onNext，告诉上游可在执行一次
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t);
                    }

                    @Override
                    public void onComplete() {
                        long time=System.currentTimeMillis()-calcTime;
                        text.append( "------------------------耗时："+time/1000+"s\n");
                        text.append( "------------------------onComplete-------------------------");
                        //延迟1s后执行事件
                        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });

                    }
                });
    }


    public String getCode(InputStream is) {
        try {
            BufferedInputStream bin = new BufferedInputStream(is);
            int p;

            p = (bin.read() << 8) + bin.read();
            String code = null;

            switch (p) {
                case 0xefbb:
                    code = "UTF-8";
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                default:
                    code = "GBK";
            }
            is.close();
            return code;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //RxJava2 模拟发送验证码倒计时功能
    public void onCodeClick() {

        final long count = 60; // 设置60秒
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        Observable.interval(0, 1, TimeUnit.SECONDS)//interval	延时几秒，每隔几秒开始执行
                .take(count + 1)//超过多少秒停止执行
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong; // 由于是倒计时，需要将倒计时的数字反过来
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        button1.setEnabled(false);
                        button1.setTextColor(Color.GRAY);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Long aLong) {
                        button1.setText(aLong + "秒后重发");
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                        button1.setEnabled(true);
                        button1.setTextColor(Color.RED);
                        button1.setText("发送验证码");
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.cancel();// 关闭 避免内存泄漏
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Intent intent=new Intent(Rxjava2Activity.this,Retrofit2Activity.class);
                startActivity(intent);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                Intent intent2=new Intent(Rxjava2Activity.this,Retrofit2Rxjava2Activity.class);
                startActivity(intent2);
                return true;
            case KeyEvent.KEYCODE_MENU:
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_BACK:
                return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }
}
