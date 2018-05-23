package com.aoy.learn.source;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by drizzt on 2018/5/21.
 */

public class BaseCreateActivity extends AppCompatActivity {

    @BindView(R.id.create_btn)
    Button createBtn;
    @BindView(R.id.just_btn)
    Button justBtn;
    @BindView(R.id.from_btn)
    Button fromBtn;
    @BindView(R.id.empty_btn)
    Button emptyBtn;
    @BindView(R.id.error_btn)
    Button errorBtn;
    @BindView(R.id.never_btn)
    Button neverBtn;
    @BindView(R.id.timer_btn)
    Button timerBtn;
    @BindView(R.id.interval_btn)
    Button intervalBtn;
    @BindView(R.id.range_btn)
    Button rangeBtn;
    @BindView(R.id.defer_btn)
    Button deferBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.create_btn, R.id.just_btn, R.id.from_btn, R.id.empty_btn, R.id.error_btn, R.id.never_btn, R.id.timer_btn, R.id.interval_btn, R.id.range_btn, R.id.defer_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_btn:
                createOperator();
                break;
            case R.id.just_btn:
                justOperator();
                break;
            case R.id.from_btn:
               // fromCallableOperator();
               // fromIteratorOperator();
                fromFutureOperator();
                break;
            case R.id.empty_btn:
                emptyOperator();
                break;
            case R.id.error_btn:
                errorOperator();
                break;
            case R.id.never_btn:
                neverOperator();
                break;
            case R.id.timer_btn:
                timerOperator();
                break;
            case R.id.interval_btn:
                intervalOperator();
                break;
            case R.id.range_btn:
                rangeOperator();
                break;
            case R.id.defer_btn:
                deferOperator();
                break;
        }
    }

    final String CREATE_TAG = "createOprator";
    /**
     * create操作符
     */
    private void createOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(CREATE_TAG,"onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(CREATE_TAG,"onNext:"+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(CREATE_TAG,"OnError");
            }

            @Override
            public void onComplete() {
                Log.i(CREATE_TAG,"onComplete");
            }
        });
    }

    final String JUST_TAG = "just_operation";
    private void justOperator(){
        Observable.just(1,2,3,4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(JUST_TAG, "Consumer#accept(Integer)(相当于Observer.onNext()):" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(JUST_TAG, "Consumer#accept(Throwable)(相当于Observer.onError()):" + "发生错误");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(JUST_TAG, "Action#accept(相当于Observer.onComplete()):" + "完成数据发送");
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.i(JUST_TAG,"Consumer#accept(disposable)(相当于Observer.onSubscribe())："+"数据发送之前");
                    }
                });
    }

    final String FROM_TAG = "from_tag";
    private void fromCallableOperator(){
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(FROM_TAG, "fromCallable发送的数据，接收值为：" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(FROM_TAG, "fromCallable发送的数据，发生错误");
            }
        },new Action() {
            @Override
            public void run() throws Exception {
                Log.i(FROM_TAG, "fromCallable发送的数据，完成");
            }
        });
    }

    public void fromIteratorOperator(){
        List<Integer> mList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            mList.add(i);
        }
        Observable.fromIterable(mList)
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(FROM_TAG,"onSubscribe: fromIterable发送数据之前");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(FROM_TAG, "onNext: fromIterable发送的数据，接收值为：" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(FROM_TAG, "onError fromCallable发送的数据，发生错误");
            }

            @Override
            public void onComplete() {
                Log.i(FROM_TAG, "onComplete: fromCallable发送的数据，完成");

            }
        });
    }

    public void fromFutureOperator(){

        FutureTask f = new FutureTask(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(200);
                return 2;
            }
        });
        new Thread(f).start();
        Observable.fromFuture(f,100, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(FROM_TAG,"fromFuture 接受到的数据为：" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //由于设置获取数据的事件为100ms 在call方法执行是让线程sleep(200)会抛出TimeOutException
                        Log.i(FROM_TAG,"fromFuture 发送数据发生错误：");
                    }
                });
    }

    String TAG_EMPTY ="empty_tag";
    private void emptyOperator(){
        Observable.<Integer>empty().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG_EMPTY,"empty：onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG_EMPTY,"empty：onNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG_EMPTY,"empty：onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG_EMPTY,"empty：onComplete");
            }
        });
    }

    final String TAR_ERROR = "error_tag";
    private void errorOperator(){
        Observable.<Integer>error(new IllegalArgumentException())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAR_ERROR,"error: 接受到错误信息");

                    }
                });
    }

    String TAG_NEVER = "never_tag";
    private void neverOperator(){
        Observable.never();
    }

    String TAG_TIMER = "timer_tag";
    private void timerOperator(){
        Observable.timer(10,TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG_TIMER,"timer: onSubscriber");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i(TAG_TIMER,"timer: onNext: "+ aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG_TIMER,"timer: onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG_TIMER,"timer: onComplete:");
                    }
                });
    }

    final String TAG_INTERVAL = "interval_tag";
    private void intervalOperator(){
        final Disposable[] intervalDisposable = new Disposable[1];
        Observable.interval(1000,500,TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG_INTERVAL,"interval: onNext():  " + aLong);
                        if(aLong >= 10l && intervalDisposable[0] != null){
                            intervalDisposable[0].dispose();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG_INTERVAL,"interval:onError()");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG_INTERVAL,"interval: onComplete()");
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.i(TAG_INTERVAL,"interval: onSubscribe");
                        intervalDisposable[0] = disposable;
                    }
                });

    }

    final String TAG_RANGE = "range_tag";
    private void rangeOperator(){
        Observable.range(1,5)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG_RANGE,"range: onSubscribe()");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG_RANGE,"range: onNext()  "+ integer);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG_RANGE,"range: onError()");

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG_RANGE,"range: onComplete()");

                    }
                });
    }

    final String TAG_DEFER = "defer_tag";
    private void deferOperator(){
        Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return new ObservableSource<Integer>() {
                    @Override
                    public void subscribe(Observer<? super Integer> observer) {
                        observer.onNext(1);
                        observer.onNext(2);
                        observer.onComplete();
                        observer.onNext(3);
                    }
                };
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG_DEFER,"defer: onSubscribe()");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG_DEFER,"defer: onNext():  " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG_DEFER,"defer: onError():  ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG_DEFER,"defer: onComplete():  ");
            }
        });
    }
}
