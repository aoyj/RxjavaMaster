package com.aoy.learn.source;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by drizzt on 2018/5/25.
 */

public class ConditionOrBooleanActivity extends AppCompatActivity {
    @BindView(R.id.all_btn)
    Button allBtn;
    @BindView(R.id.contains_btn)
    Button containsBtn;
    @BindView(R.id.sequence_btn)
    Button sequenceBtn;
    @BindView(R.id.isEmpty_btn)
    Button isEmptyBtn;
    @BindView(R.id.amb_btn)
    Button ambBtn;
    @BindView(R.id.switchIfEmpty_btn)
    Button switchIfEmptyBtn;
    @BindView(R.id.takeUntil_btn)
    Button takeUntilBtn;
    @BindView(R.id.takeWhile_btn)
    Button takeWhileBtn;
    @BindView(R.id.defaultIfEmpty_btn)
    Button defaultIfEmptyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boolean);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.all_btn, R.id.contains_btn, R.id.sequence_btn, R.id.isEmpty_btn, R.id.amb_btn, R.id.switchIfEmpty_btn, R.id.takeUntil_btn, R.id.takeWhile_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_btn:
                allOperator();
                break;
            case R.id.contains_btn:
                containsOpretor();
                break;
            case R.id.sequence_btn:
                sequenceEqualOperator();
                break;
            case R.id.isEmpty_btn:
                isEmptyOperator();
                break;
            case R.id.amb_btn:
                ambOperation();
                break;
            case R.id.switchIfEmpty_btn:
                switchIfEmptyOperator();
                break;
            case R.id.takeUntil_btn:
                takeUntilOperator();
                break;
            case R.id.takeWhile_btn:
                takeWhileOperator();
                break;
        }
    }

    final String TAG_ALL = "all_tag";
    private void allOperator() {
        Observable.just(1, 2, 3, 4)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 3;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.i(TAG_ALL, "all: onNext()" + aBoolean);
            }
        });
    }

    final String TAG_CONTAINS = "contains_tag";

    private void containsOpretor() {
        Observable.just(1, 2, 3, 4)
                .contains(4)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.i(TAG_CONTAINS, "contains: onNext()" + aBoolean);
                    }
                });
    }

    final String TAG_SEQUENCE_EQUAL = "sequence_equal_tag";

    private void sequenceEqualOperator() {
        Observable observable1 = Observable.just(1, 2, 3, 4);
        Observable observable2 = Observable.just(3, 4, 5, 8);
        Observable.sequenceEqual(observable1, observable2, new BiPredicate() {
            @Override
            public boolean test(Object o, Object o2) throws Exception {
                int i1 = (int) o;
                int i2 = (int) o2;
                return i1 % 2 == i2 % 2;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean o) throws Exception {
                Log.i(TAG_SEQUENCE_EQUAL, "sequence: onNext()" + o);
            }
        });
    }

    final String TAG_IS_EMPTY = "tag_is_empty";

    private void isEmptyOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onComplete();
            }
        })
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.i(TAG_IS_EMPTY, "isEmpty: onNext()" + aBoolean);
                    }
                });
    }

    final String TAG_AMB = "amb_tag";

    private void ambOperation() {
        Observable observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Thread.sleep(100);
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).subscribeOn(Schedulers.newThread());
        Observable observable2 = Observable.<Integer>just(5, 6, 7);
        List<Observable<Integer>> mlist = new ArrayList<>();
        mlist.add(observable1);
        mlist.add(observable2);
        Observable.amb((mlist))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_AMB, "amb: onNext()" + integer);
                    }
                });
    }

    final String TAG_SWITCH_IF_EMPTY = "switch_if_emtpy_tag";

    public void switchIfEmptyOperator() {
        Observable<Integer> observable1 = Observable.<Integer>just(1, 2, 3, 4);
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onComplete();
                //emitter.onError(new IllegalAccessError());
                //emitter.onNext(52);
            }
        })
                .switchIfEmpty(observable1)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object integer) throws Exception {
                        Log.i(TAG_SWITCH_IF_EMPTY, "switchIfEmpty: onNext()" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG_SWITCH_IF_EMPTY, "switchIfEmpty: onError()" );

                    }
                });
    }

    final String TAG_DEFAULT_IF_EMPTY = "default_if_empty_tag";

    public void defaultIfEmptyOperator() {
        Observable.empty()
                .defaultIfEmpty(1)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object integer) throws Exception {
                        Log.i(TAG_DEFAULT_IF_EMPTY, "defaultIfEmpty: onNext():" + integer);
                    }
                });
    }

    final String TAG_TAKE_UNTIL = "take_until_tag";

    public void takeUntilOperator() {
        //takeUntil发送正好符合条件的数据
        Observable.just(1, 2, 3, 4)
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG_TAKE_UNTIL, "takeUntil: onNext():" + integer);
            }
        });

        //如果takeUntil的参数是另个一Observable，则含义是当第二个Observale开始发送数据是，原始的Observable才开始数据发送
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(11);
               // Thread.sleep(100);
                emitter.onNext(22);
            }
        })
                .takeUntil(Observable.just(5, 6, 7).subscribeOn(Schedulers.newThread()))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG_TAKE_UNTIL, "takeUntil: onNext():" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG_TAKE_UNTIL,"takeUntil : onError()");
                    }
                });

    }

    final String TAG_TAKE_WHILE = "take_while";

    private void takeWhileOperator() {
        //takeWhile不发送正好符合条件的数据
        Observable.just(1, 2, 3, 4)
                .takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer <= 3;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG_TAKE_WHILE, "takeWhile: onNext()" + integer);
            }
        });
    }

    @OnClick(R.id.defaultIfEmpty_btn)
    public void onViewClicked() {
        defaultIfEmptyOperator();
    }
}
