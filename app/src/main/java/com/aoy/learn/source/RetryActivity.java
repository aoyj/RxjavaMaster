package com.aoy.learn.source;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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
                break;
            case R.id.onExceptionResumeNext_btn:
                break;
            case R.id.onErrorReturn_btn:
                break;
            case R.id.retry_btn:
                break;
            case R.id.retryWhen_btn:
                break;
        }
    }

    final String TAG_ONERRO_RESUME_NEXT = "onErrorResumeNext_tag";
    private void onErrorResumeNextOperator(){
        Observable.just(1,"2",3,"4")
                .cast(Integer.class)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                        return null;
                    }
                });
    }
}
