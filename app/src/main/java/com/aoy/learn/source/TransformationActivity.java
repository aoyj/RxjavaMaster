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
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by drizzt on 2018/5/28.
 */

public class TransformationActivity extends AppCompatActivity {

    @BindView(R.id.map_btn)
    Button mapBtn;
    @BindView(R.id.flatMap_btn)
    Button flatMapBtn;
    @BindView(R.id.flatMapIterable_btn)
    Button flatMapIterableBtn;
    @BindView(R.id.contactMap_btn)
    Button contactMapBtn;
    @BindView(R.id.switchMap_btn)
    Button switchMapBtn;
    @BindView(R.id.scan_btn)
    Button scanBtn;
    @BindView(R.id.groupBy_btn)
    Button groupByBtn;
    @BindView(R.id.buffer_btn)
    Button bufferBtn;
    @BindView(R.id.windown_btn)
    Button windownBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_transformation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.map_btn, R.id.flatMap_btn, R.id.flatMapIterable_btn, R.id.contactMap_btn, R.id.switchMap_btn, R.id.scan_btn, R.id.groupBy_btn, R.id.buffer_btn, R.id.windown_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.map_btn:
                mapOperator();
                break;
            case R.id.flatMap_btn:
                flatMap();
                break;
            case R.id.flatMapIterable_btn:
                flatMapIterableOperator();
                break;
            case R.id.contactMap_btn:
                contactMapOperator();
                break;
            case R.id.switchMap_btn:
                switchMapOperator();
                break;
            case R.id.scan_btn:
                scanOperator();
                break;
            case R.id.groupBy_btn:
                groupByOperator();
                break;
            case R.id.buffer_btn:
                bufferOperator();
                break;
            case R.id.windown_btn:
                windowOperator();
                break;
        }
    }
    
    final String TAG_MAP = "map_tag";
    private void mapOperator(){
        Observable.just(1,2,3,4,5)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "a";
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG_MAP,"map : onNext():" + s);
            }
        });
    }

    final String TAG_FLAT_MAP = "flat_map_tag";
    private void flatMap(){
        Observable.just(1,2,3,4)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final Integer integer) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                Thread.sleep(20000);
                                emitter.onNext(integer + "a");
                                emitter.onNext(integer + "b");
                                emitter.onNext(integer + "c");
                                emitter.onNext(integer + "d");
                            }
                        })
                                .subscribeOn(Schedulers.newThread());
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG_FLAT_MAP,"flatMap： OnNext():" +s);
                    }
                });
    }

    final String TAG_FLAT_MAP_ITERABLE = "flat_map_iterable_tag";
    private void flatMapIterableOperator(){
        Observable.just(1,2,3,4,5)
                .flatMapIterable(new Function<Integer, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(Integer integer) throws Exception {
                        List mList = new ArrayList();
                        mList.add(integer + "a");
                        mList.add(integer + "b");
                        mList.add(integer + "c");
                        mList.add(integer + "d");
                        return mList;
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG_FLAT_MAP_ITERABLE,"flatMapIterable: onNext();" + s);
            }
        });
    }

    final String TAG_CONCAT_MAP = "concat_map_tag";
    private void contactMapOperator(){
        Observable.just(1,2,3,4)
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final Integer integer) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                Thread.sleep(500);
                                emitter.onNext(integer + "a");
                                emitter.onNext(integer + "b");
                                emitter.onComplete();
                            }
                        }).subscribeOn(Schedulers.newThread());
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG_CONCAT_MAP,"concat: onNext():" + s);
            }
        });
    }

    final String TAG_SWITCH_MAP = "switch_map";
    private void switchMapOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                try {
                    Thread.sleep(1500);
                }catch (InterruptedException e){
                    Log.i(TAG_SWITCH_MAP,"原始的emitter发送数据失败");
                }
                emitter.onNext(5);
            }
        })
                .switchMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final Integer integer) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                emitter.onNext(integer+"a");
                                emitter.onNext(integer+"b");
                                emitter.onNext(integer+"c");
                            //    emitter.onComplete();
                            }
                        }).subscribeOn(Schedulers.newThread());
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG_SWITCH_MAP, "switchMap: onNext()" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG_SWITCH_MAP,"switchMap: onError:");
            }
        });
    }

    final String TAG_SCAN = "scan_tag";
    private void scanOperator(){
        Observable.just(1,2,3,4)
                .scan(8,new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.i(TAG_SCAN,"emitter: 第一个数据为：" + integer + "  第二个数据为:" + integer2);
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG_SCAN,"scan: onNext():" + integer);
            }
        });
    }

    final String TAG_GROUP_BY = "GroupBy_tag";
    private void groupByOperator(){
        Observable.just(1,2,3,4,5,6)
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer % 2 == 0 ? "偶数：": "奇数：";
                    }
                }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {
                if("偶数：".equals(stringIntegerGroupedObservable.getKey())) {
                    Log.i(TAG_GROUP_BY,"groupBy onNext(): 获取分组的GroupObservable,key: "+ stringIntegerGroupedObservable.getKey());
                    stringIntegerGroupedObservable.subscribeOn(Schedulers.newThread())
                            .observeOn(Schedulers.newThread())
                            .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            Log.i(TAG_GROUP_BY, "groupBy : GroupedObservable发送数据 onNext():" + integer);
                        }
                    });
                }else{
                    Thread.sleep(1000);
                    Log.i(TAG_GROUP_BY,"groupBy onNext(): 获取分组的GroupObservable,key: "+ stringIntegerGroupedObservable.getKey());
                    stringIntegerGroupedObservable
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    Log.i(TAG_GROUP_BY, "groupBy : GroupedObservable发送数据 onNext():" + integer);
                                }
                            });
                }
            }
        });
    }

    final String TAG_BUFFER = "buffer_tag";
    private void bufferOperator(){
        Observable.just(1,2,3,4,5)
                .buffer(3)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.i(TAG_BUFFER,"buffer : onNext():" + integers);
                    }
                });
    }

    final String TAG_WINDOW = "window_tag";
    private void windowOperator(){
        Observable.just(1,2,3,4)
                .window(3)
                .subscribe(new Consumer<Observable<Integer>>() {
                    @Override
                    public void accept(Observable<Integer> integerObservable) throws Exception {
                        Log.i(TAG_WINDOW,"window 将原始发送数据打包成Observable onNext():");
                        integerObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.i(TAG_WINDOW,"window  新生成的Observable发送的数据 onNext():" + integer);
                            }
                        });
                    }
                });
    }
}
