package com.aoy.learn.source;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by drizzt on 2018/5/26.
 */

public class ConverActivity extends AppCompatActivity {

    @BindView(R.id.toList_btn)
    Button toListBtn;
    @BindView(R.id.toSortedList_btn)
    Button toSortedListBtn;
    @BindView(R.id.toMap_btn)
    Button toMapBtn;
    @BindView(R.id.toMultiMap_btn)
    Button toMultiMapBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toList_btn, R.id.toSortedList_btn, R.id.toMap_btn, R.id.toMultiMap_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toList_btn:
                toListOperator();
                break;
            case R.id.toSortedList_btn:
                toSortedListOperator();
                break;
            case R.id.toMap_btn:
                toMapOperator();
                break;
        }
    }

    final String TAG_TO_LIST = "to_list_tag";
    private void toListOperator(){
        Observable.just(1,2,3,4)
                .toList()
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.i(TAG_TO_LIST, "toList: oNext：" + integers);
                    }
                });
    }

    final String TAG_TO_SORTED_LIST = "tag_to_sorted_list";
    private void toSortedListOperator(){
        Observable.just(1,3,2,6,5,4)
                .toSortedList(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o1.compareTo(o2);
                    }
                }).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Log.i(TAG_TO_SORTED_LIST,"toSortedList: onNext():" + integers);
            }
        });
    }

    final String TAG_TO_MAP = "to_map_tag";
    private void toMapOperator(){
        Observable.just(1,2,3,4,5)
                .toMap(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "a";
                    }
                }).subscribe(new Consumer<Map<String, Integer>>() {
            @Override
            public void accept(Map<String, Integer> stringIntegerMap) throws Exception {
                Log.i(TAG_TO_MAP,"toMap: onNext():" + stringIntegerMap);
            }
        });
    }

}
