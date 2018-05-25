package com.aoy.learn.source;

import android.os.Bundle;
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
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by drizzt on 2018/5/24.
 */

public class MergeActivity extends AppCompatActivity {

    @BindView(R.id.concat_btn)
    Button concatBtn;
    @BindView(R.id.startWith_btn)
    Button startWithBtn;
    @BindView(R.id.merge_btn)
    Button mergeBtn;
    @BindView(R.id.zip_btn)
    Button zipBtn;
    @BindView(R.id.combineLastest_btn)
    Button combineLastestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
        ButterKnife.bind(this);
    }


    final String TAG_CONTACT = "contact_tag";

    private void contactOperator() {
        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Thread.sleep(1000);
                emitter.onNext("1");
                Thread.sleep(1000);
                emitter.onNext("2");
                Thread.sleep(1000);
                emitter.onNext("3");
                emitter.onComplete();
                //emitter.onError(new IllegalAccessError());
            }
        }).
                observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread());
        Observable observable2 = Observable.fromArray(4, 5, 6, 7)
                .subscribeOn(Schedulers.newThread());
        Observable.concat(observable1, observable2)
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG_CONTACT, "concat: onSubscribe()");
                    }

                    @Override
                    public void onNext(Object o) {
                        String des = "";
                        if (o instanceof String) {
                            des = "String";
                        } else if (o instanceof Integer) {
                            des = "Integer";
                        }
                        Log.i(TAG_CONTACT, "concat:  onNext():数据类型:" + des + "值为：" + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG_CONTACT, "concat:  onError()");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG_CONTACT, "concat:  onComplete()");
                    }
                });
    }

    @OnClick({R.id.concat_btn, R.id.startWith_btn, R.id.merge_btn, R.id.zip_btn,R.id.combineLastest_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.concat_btn:
                contactOperator();
                break;
            case R.id.startWith_btn:
                startWithOperator();
                break;
            case R.id.merge_btn:
                mergeOperator();
                break;
            case R.id.zip_btn:
                zipOperator();
                break;
            case R.id.combineLastest_btn:
                combineLastest();
                break;
        }
    }

    final String TAG_START_WITH = "start_with_tag";

    public void startWithOperator() {
        Observable.just(1, 2, 3)
                .startWithArray(4, 5, 6)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_START_WITH, "startWith: onNext():" + integer);
                    }
                });
    }

    final String TAG_MERGE = "merge_with";

    private void mergeOperator() {
        Observable observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Thread.sleep(1000);
                emitter.onNext("1");
                Thread.sleep(1000);
                emitter.onNext("2");
                Thread.sleep(1000);
                emitter.onNext("3");
                emitter.onComplete();
                // emitter.onError(new IllegalAccessError());
            }
        }).subscribeOn(Schedulers.newThread());

        Observable observable2 = Observable.fromArray(4, 5, 6, 7)
                .subscribeOn(Schedulers.newThread());

        Observable.merge(observable1, observable2)
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG_MERGE, "merge: onSubscriber()");
                    }

                    @Override
                    public void onNext(Object o) {
                        String des = "";
                        if (o instanceof String) {
                            des = "String";
                        } else if (o instanceof Integer) {
                            des = "Integer";
                        }
                        Log.i(TAG_MERGE, "merge:  onNext():数据类型:" + des + "值为：" + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG_MERGE, "merge: onError()");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG_MERGE, "merge: onComplete()");
                    }
                });
    }


    final String TAG_ZIP = "zip_tag";

    private void zipOperator() {
        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Thread.sleep(1000);
                emitter.onNext(1);
                Thread.sleep(1000);
                emitter.onNext(2);
                // emitter.onError(null);
                //emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable observable2 = Observable.just("a", "b", "c", "d");
        Observable observable3 = Observable.fromArray("!", "@", "#");
        Observable.zip(observable1, observable2, observable3, new Function3() {
            @Override
            public Object apply(Object o, Object o2, Object o3) throws Exception {
                String str1 = String.valueOf((int) o);
                return str1 + o2 + o3;
            }
        }).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG_ZIP, "zip: onSubscribe()");
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG_ZIP, "zip: onNext()" + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG_ZIP, "zip: onError()");
            }

            @Override
            public void onComplete() {
                Log.i(TAG_ZIP, "zip: onComplete()");
            }
        });
    }

    final String TAG_COMBINE_LASTEST = "combine_lastest_tag";
    private void combineLastest(){
        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
               // Thread.sleep(1000);
                emitter.onNext(1);
              //  Thread.sleep(1000);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);

                // emitter.onError(null);
                emitter.onComplete();
            }
        });
        Observable observable2 = Observable.just("a", "b", "c", "d");
        Observable.combineLatest(observable1, observable2, new BiFunction() {
            @Override
            public Object apply(Object o, Object o2) throws Exception {
                return (int)o + (String)o2;
            }
        }).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG_COMBINE_LASTEST,"combineLastest: onSubscribe()");
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG_COMBINE_LASTEST,"combineLastest: onNext(): " + o);

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG_COMBINE_LASTEST,"combineLastest: onError()");

            }

            @Override
            public void onComplete() {
                Log.i(TAG_COMBINE_LASTEST,"combineLastest: onComplete");
            }
        });
    }

}
