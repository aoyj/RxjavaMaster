package com.aoy.learn.source;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.Subject;

/**
 * Created by drizzt on 2018/5/29.
 */

public class RetryActivity extends AppCompatActivity {

    @BindView(R.id.onErrorResumeNext_btn)
    Button onErrorResumeNextBtn;
    @BindView(R.id.onExceptionResumeNext_btn)
    Button onExceptionResumeNextBtn;
    @BindView(R.id.onErrorReturn_btn)
    Button onErrorReturnBtn;
    @BindView(R.id.retry_btn)
    Button retryBtn;
    @BindView(R.id.retryWhen_btn)
    Button retryWhenBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retry);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.onErrorResumeNext_btn, R.id.onExceptionResumeNext_btn, R.id.onErrorReturn_btn, R.id.retry_btn, R.id.retryWhen_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.onErrorResumeNext_btn:
                onErrorResumeNextOperator();
                break;
            case R.id.onExceptionResumeNext_btn:
                onExceptionResumeNextOperator();
                break;
            case R.id.onErrorReturn_btn:
                onErrorReturnOperator();
                break;
            case R.id.retry_btn:
                retryOperator();
                break;
            case R.id.retryWhen_btn:
                retryWhenOperator();
                break;
        }
    }

    final String TAG_ONERRO_RESUME_NEXT = "onErrorResumeNext_tag";
    private void onErrorResumeNextOperator(){
        Observable.just(1,"2",3,"4")
                .cast(Integer.class)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Throwable throwable) throws Exception {
                        return Observable.just(5,6,7);
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG_ONERRO_RESUME_NEXT, "onErrorResumeNext:  onNext()" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG_ONERRO_RESUME_NEXT, "onErrorResumeNext:  onError()");
            }
        });
    }

    final String TAG_ON_EXCEPTION_RESUME_NEXT = "onExceptionResumeNext_tag";
    private void onExceptionResumeNextOperator(){
        Observable.just(1,"2",3,4)
                .cast(Integer.class)
                .onExceptionResumeNext(Observable.just(5,6,7))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_ON_EXCEPTION_RESUME_NEXT,"onExceptionResumeNext: onNext()" + integer);
                    }
                });
    }

    final String TAG_ON_ERROR_RETURN = "onErrorReturn_tag";
    private void onErrorReturnOperator(){
        Observable.just(1,"2",3,4)
                .cast(Integer.class)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        return 93;
                    }
            }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer serializable) throws Exception {
                Log.i(TAG_ON_ERROR_RETURN,"onErrorReturn: onNext" + serializable);
            }
        });
    }

    final String TAG_RETRY = "retry_tag";
    private void retryOperator(){
        Observable.just(1,"2",3,4)
                .cast(Integer.class)
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(Throwable throwable) throws Exception {
                        Log.i(TAG_RETRY, "retry: 遇到错误的重试条件");
                        if(throwable instanceof ClassCastException){
                            return true;
                        }
                        return false;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_RETRY, "retry: onNext();" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG_RETRY,"retry: onError()");
                    }
                });
    }

    final String TAG_RETRY_WHEN = "retryWhen";
    private void retryWhenOperator(){
        Observable.just(1,"2",3,4)
                .cast(Integer.class)
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Observable<Throwable> throwableObservable) throws Exception {
                        // return Observable.error(new IllegalArgumentException());
                        return Observable.just("a","b");
                        //return Observable.empty();
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG_RETRY_WHEN, "retryWhen: onNext():" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG_RETRY_WHEN, "retryWhen: onError():");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.i(TAG_RETRY_WHEN, "retryWhen: onComplete():");
            }
        });
    }

}
