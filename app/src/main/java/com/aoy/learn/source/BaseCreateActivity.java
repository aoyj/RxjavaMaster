package com.aoy.learn.source;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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
                break;
            case R.id.empty_btn:
                break;
            case R.id.error_btn:
                break;
            case R.id.never_btn:
                break;
            case R.id.timer_btn:
                break;
            case R.id.interval_btn:
                break;
            case R.id.range_btn:
                break;
            case R.id.defer_btn:
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
}
