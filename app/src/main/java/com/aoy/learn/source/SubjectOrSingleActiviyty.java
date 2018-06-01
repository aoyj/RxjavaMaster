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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by drizzt on 2018/5/30.
 */

public class SubjectOrSingleActiviyty extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.AsyncSubject, R.id.BehaviorSubject, R.id.PublishSubject, R.id.ReplaySubject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.AsyncSubject:
                AsyncSubject();
                break;
            case R.id.BehaviorSubject:
                behaviorSubject();
                break;
            case R.id.PublishSubject:
                publishSubject();
                break;
            case R.id.ReplaySubject:
                replaySubject();
                break;
        }
    }

    final String TAG_PUBLISH_SUBJECT = "PublishSubject_tag";
    private void publishSubject(){
        PublishSubject  subject = PublishSubject.create();
        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG_PUBLISH_SUBJECT,"PublishSubject: 第一个观察者接收到的数据为：" + o);
            }
        });
        subject.onNext(1);
        subject.onNext(2);

        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG_PUBLISH_SUBJECT,"PublishSubject: 第二个观察者接收到的数据为：" + o);
            }
        });
        subject.onNext(3);

    }

    final String TAG_BEHAVIOR_SUBJECT = "behavior_subject";
    private void behaviorSubject(){
        BehaviorSubject subject = BehaviorSubject.createDefault(93);

        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG_BEHAVIOR_SUBJECT,"BehaviorSubject: 第一个Observer接受到的数据为：" + o);
            }
        });

        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG_BEHAVIOR_SUBJECT,"BehaviorSubject: 第二额Observer接受到的数据为：" + o);
            }
        });
        subject.onNext(4);

    }

    final String TAG_REPLAY_SUBJECT = "replaySubject_tag";
    private void replaySubject(){
        //ReplaySubject subject = ReplaySubject.createWithSize(2);
        ReplaySubject subject = ReplaySubject.create();
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG_REPLAY_SUBJECT,"replaySubject: 第一个Observer收到的数据为：" + o);
            }
        });

        subject.onNext(4);
        subject.onNext(5);
        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG_REPLAY_SUBJECT,"replaySubject: 第二个Observer收到的数据为：" + o);
            }
        });
    }

    final String TAG_ASYNC_SUBJECT = "AsyncSubject_tag";
    private void AsyncSubject(){
        AsyncSubject subject = AsyncSubject.create();
        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG_ASYNC_SUBJECT,"AsyncSubject: 第一个Observer接受到的数据：" + o);
            }
        });
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG_ASYNC_SUBJECT,"AsyncSubject: 第二个Observer接受到的数据：" + o);
            }
        });
        subject.onNext(4);
        subject.onComplete();
    }
}
