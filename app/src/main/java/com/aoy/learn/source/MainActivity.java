package com.aoy.learn.source;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by drizzt on 2018/5/16.
 */

public class MainActivity extends AppCompatActivity {
    String TAG = "RxjavaLern";

    @BindView(R.id.base_btn)
    Button baseBtn;
    @BindView(R.id.merge_btn)
    Button mergeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void baseCreate() {
        Intent toCreateIntent = new Intent(this, BaseCreateActivity.class);
        startActivity(toCreateIntent);
    }

    private void flatMap() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("a");
                e.onNext("b");
                e.onNext("c");
                e.onComplete();
            }
        });
        observable.flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return Observable.just(s + "1", s + "2", s + "3");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    @OnClick({R.id.base_btn, R.id.merge_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_btn:
                baseCreate();
                break;
            case R.id.merge_btn:
                flatMap();
                break;
        }
    }
}
