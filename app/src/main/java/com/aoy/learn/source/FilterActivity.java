package com.aoy.learn.source;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by drizzt on 2018/5/24.
 */

public class FilterActivity extends AppCompatActivity {

    @BindView(R.id.filter_btn)
    Button filterBtn;
    @BindView(R.id.ofType_btn)
    Button ofTypeBtn;
    @BindView(R.id.take_btn)
    Button takeBtn;
    @BindView(R.id.takeLastest_btn)
    Button takeLastestBtn;
    @BindView(R.id.first_btn)
    Button firstBtn;
    @BindView(R.id.last_btn)
    Button lastBtn;
    @BindView(R.id.skip_btn)
    Button skipBtn;
    @BindView(R.id.skipLast_btn)
    Button skipLastBtn;
    @BindView(R.id.distinct_btn)
    Button distinctBtn;
    @BindView(R.id.distinctUntilChanged_btn)
    Button distinctUntilChangedBtn;
    @BindView(R.id.throttleFirst_btn)
    Button throttleFirstBtn;
    @BindView(R.id.throttleWithTimeOut_btn)
    Button throttleWithTimeOutBtn;
    @BindView(R.id.throttleLast_btn)
    Button throttleLastBtn;
    @BindView(R.id.timeout_btn)
    Button timeoutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_filter);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.filter_btn, R.id.ofType_btn, R.id.take_btn, R.id.takeLastest_btn, R.id.first_btn, R.id.last_btn, R.id.skip_btn, R.id.skipLast_btn, R.id.distinct_btn, R.id.distinctUntilChanged_btn, R.id.throttleFirst_btn,R.id.throttleWithTimeOut_btn, R.id.throttleLast_btn, R.id.timeout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.filter_btn:
                filterOperator();
                break;
            case R.id.ofType_btn:
                ofTypeOperator();
                break;
            case R.id.take_btn:
                takeOperator();
                break;
            case R.id.takeLastest_btn:
                takeLastestOperator();
                break;
            case R.id.first_btn:
                firstOperation();
                break;
            case R.id.last_btn:
                lastOperator();
                break;
            case R.id.skip_btn:
                skipOperator();
                break;
            case R.id.skipLast_btn:
                skipLastOpretion();
                break;
            case R.id.distinct_btn:
                distinctOperator();
                break;
            case R.id.distinctUntilChanged_btn:
                distinctUntilChangeOperator();
                break;
            case R.id.throttleFirst_btn:
                throtlleFirstOperator();
                break;
            case R.id.throttleWithTimeOut_btn:
                throttleTimeOutOperator();
                break;
            case R.id.throttleLast_btn:
                throttleLastOperator();
                break;
            case R.id.timeout_btn:
                timeoutOperator();
                break;
        }
    }

    final String TAG_FILTER = "filter_tag";

    private void filterOperator() {
        Observable.just(1, 2, 3, 4)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        if (integer > 2) {
                            return true;
                        }
                        return false;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG_FILTER, "filter: onNext():" + integer);
            }
        });
    }

    final String TAG_OFTYPE = "ofType_tag";

    private void ofTypeOperator() {
        Observable.just("1", 2, "3", 4, 5)
                .ofType(Integer.class)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_OFTYPE, "ofType: onNext():" + integer);
                    }
                });
    }

    final String TAG_TAKE = "take_tag";

    private void takeOperator() {
       /* Observable.just(1,2,3,4)
                .take(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_TAKE,"take: onNext():" + integer);
                    }
                });*/
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(500);
                emitter.onNext(2);
                //  emitter.onComplete();
            }
        }).take(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG_TAKE, "take: onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG_TAKE, "take: onNext():" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG_TAKE, "take: onError():");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG_TAKE, "take: onComplete():");
                    }
                });
    }

    final String TAG_TAKE_LASTEST = "take_lastest_tag";

    private void takeLastestOperator() {
      /*  Observable.just(1,"2","3",4,5)
                .takeLast(3)
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Exception {
                        String s = "";
                        if(serializable instanceof Integer){
                            s = (int)serializable + "";
                        }else if(serializable instanceof String){
                            s = (String) serializable;
                        }
                        Log.i(TAG_TAKE_LASTEST,"takeLastest: onNext():" + s);
                    }
                });*/
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(500);
                emitter.onNext(2);
                Thread.sleep(500);
                emitter.onNext(3);
                emitter.onComplete();
                //emitter.onError(null);
            }
        }).subscribeOn(Schedulers.newThread())
                .takeLast(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG_TAKE_LASTEST, "take: onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG_TAKE_LASTEST, "take: onNext():" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG_TAKE_LASTEST, "take: onError():");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG_TAKE_LASTEST, "take: onComplete():");
                    }
                });
    }

    final String TAG_FIRST = "first_tag";

    private void firstOperation() {
        Observable.just(1, 2, 3)
                .first(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_FIRST, "first: onNext():" + integer);
                    }
                });
    }

    final String TAG_LAST = "last_tag";

    private void lastOperator() {
        Observable.just(1, 2, 3, 4)
                .last(5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_LAST, "last: onNext():" + integer);
                    }
                });
    }

    final String TAG_SKIP = "skip_tag";

    private void skipOperator() {
        Observable.just(1, 2, 3, 4)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_SKIP, "skip: onNext():" + integer);
                    }
                });
    }

    final String TAG_SKIP_LAST = "skip_last_tag";

    private void skipLastOpretion() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Thread.sleep(100);
                emitter.onNext(1);
                Thread.sleep(100);
                emitter.onNext(2);
                Thread.sleep(100);
                emitter.onNext(3);
                Thread.sleep(100);
                emitter.onNext(4);
                Thread.sleep(100);
                emitter.onNext(5);
                emitter.onError(null);
            }
        }).subscribeOn(Schedulers.newThread())
                .skipLast(300, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG_SKIP_LAST, "skipLast: onSubscribe()");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG_SKIP_LAST, "skipLast: onNext():" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG_SKIP_LAST, "skipLast: onError():");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG_SKIP_LAST, "skipLast: onComplete()");
                    }
                });
    }

    final String TAG_DISTINCT = "distinct_tag";

    /**
     * dinstinct操作符用HashSet判断元素是否重复
     * distinct中的function是最终保存Hashset中的值用以判断发送至是否重复
     * 如果不传function,则Hashset中默认会保存发送的值
     */
    private void distinctOperator() {
        Observable.just("1", "2", "3", "3", "4", "5")
                .distinct(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return Integer.valueOf(s) % 2;
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG_DISTINCT, "distinct: onNext():" + s);
            }
        });
    }

    final String TAG_DISTINCT_UNTIL_CHANGED = "dinstct_until_change_tag";

    private void distinctUntilChangeOperator() {
        Observable.just("1", "2", "3", "3", "4", "5")
                .distinctUntilChanged()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG_DISTINCT_UNTIL_CHANGED, "distinctUntilChanged: onNext()" + s);
                    }
                });
    }

    final String TAG_THROTLLE_FIRST = "throttle_first_tag";

    private void throtlleFirstOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Thread.sleep(100);
                emitter.onNext(1);
                Thread.sleep(100);
                emitter.onNext(2);
                Thread.sleep(100);
                emitter.onNext(3);
                Thread.sleep(100);
                emitter.onNext(5);
                emitter.onNext(6);
            }
        }).throttleFirst(201, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_THROTLLE_FIRST, "throttle: onNext()" + integer);
                    }
                });
    }

    final String TAG_THROTTEL_TIME_OUT = "throttle_time_out";

    private void throttleTimeOutOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(100);
                emitter.onNext(2);
                Thread.sleep(100);
                emitter.onNext(3);
                Thread.sleep(500);
                emitter.onNext(4);
                Thread.sleep(300);
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onNext(7);
            }
        }).throttleWithTimeout(499, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_THROTTEL_TIME_OUT, "throttleWithTimeOut: onNext()：" + integer);
                    }
                });
    }

    final String TAG_THROTTLE_LAST = "throttle_last";
    private void throttleLastOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(100);
                emitter.onNext(2);
                Thread.sleep(100);
                emitter.onNext(3);
                Thread.sleep(500);
                emitter.onNext(4);
                Thread.sleep(300);
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onNext(7);
                emitter.onComplete();
            }
        }).throttleLast(200,TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_THROTTLE_LAST,"throttleLast: onNext():" + integer);
                    }
                });
    }

    final String TAG_TIMEOUT = "timeout_tag";
    private void timeoutOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(100);
                emitter.onNext(2);
                Thread.sleep(300);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onComplete();
            }
        }).timeout(200,TimeUnit.MILLISECONDS,Observable.just(4,5,6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_TIMEOUT, "timeout: onNext()" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG_TIMEOUT, "timeout: onError()");
                    }
                });
    }
}
