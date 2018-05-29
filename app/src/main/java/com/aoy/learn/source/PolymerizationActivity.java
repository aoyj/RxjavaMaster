package com.aoy.learn.source;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by drizzt on 2018/5/26.
 */

public class PolymerizationActivity extends AppCompatActivity {

    @BindView(R.id.reduce_btn)
    Button reduceBtn;
    @BindView(R.id.collect_btn)
    Button collectBtn;
    @BindView(R.id.count_btn)
    Button countBtn;
    @BindView(R.id.doOnNext_btn)
    Button doOnNextBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polymerization);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.reduce_btn, R.id.collect_btn, R.id.count_btn, R.id.doOnNext_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reduce_btn:
                reduceOperator();
                break;
            case R.id.collect_btn:
                collectOperator();
                break;
            case R.id.count_btn:
                countOperator();
                break;
            case R.id.doOnNext_btn:
                doOnNextTagOperator();
                break;
        }
    }

    final String TAG_REDUCE = "reduce_tag";
    private void reduceOperator(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onNext("a");
                emitter.onComplete();
            }
        })
                .reduce(new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        Log.i(TAG_REDUCE,"reduce: emitter: 第一个参数值：" + s + "  第二个参数值："+ s2);
                        return s+s2;
                    }
                }).subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    Log.i(TAG_REDUCE,"reduce: onNext():" + s);
                }
        });
    }

    final String TAG_COLLECT = "collect_tag";
    private void collectOperator(){
        Observable.just("1","2","3","4","4")
                .collect(new Callable<Set<String>>() {
                    @Override
                    public Set call() throws Exception {
                        return new HashSet();
                    }
                },new BiConsumer<Set<String>,String>(){
                    @Override
                    public void accept(Set<String> strings, String s) throws Exception {
                        strings.add(s);
                    }
                }).subscribe(new Consumer<Set<String>>() {
            @Override
            public void accept(Set<String> strings) throws Exception {
                Log.i(TAG_COLLECT,"collect onNext:" + strings.toString());
            }
        });
    }

    final static String TAG_COUNT = "count_tag";
    private void countOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onError(null);
            }
        })
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG_COUNT, "count： onNext():" + aLong);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG_COUNT, "count： onError():");
                    }
                });
    }

    final String TAG_DO_ON_ONNEXT = "doOnNext_tag";
    private void doOnNextTagOperator(){
        Observable.just(1,2,3,4)
               //doAfterNext()

                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_DO_ON_ONNEXT,"doOnNext: 接收到数据之前：" + integer);
                        integer = integer + integer;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG_DO_ON_ONNEXT,"doOnNext: onNext()：" + integer);
            }
        });
    }
}
