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

/**
 * Created by drizzt on 2018/5/16.
 */

public class MainActivity extends AppCompatActivity {
    String TAG = "RxjavaLern";

    @BindView(R.id.base_btn)
    Button baseBtn;
    @BindView(R.id.merge_btn)
    Button mergeBtn;
    @BindView(R.id.filter_btn)
    Button filterBtn;
    @BindView(R.id.boolean_btn)
    Button booleanBtn;
    @BindView(R.id.polymerization_btn)
    Button polymerizationBtn;
    @BindView(R.id.convert_btn)
    Button convertBtn;
    @BindView(R.id.transformation_btn)
    Button transformationBtn;
    @BindView(R.id.retry_btn)
    Button retryBtn;
    @BindView(R.id.subject_btn)
    Button subjectBtn;

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

    private void toMergeOperator() {
        Intent toCreateIntent = new Intent(this, MergeActivity.class);
        startActivity(toCreateIntent);
    }

    private void toFilterOperator() {
        Intent toCreateIntent = new Intent(this, FilterActivity.class);
        startActivity(toCreateIntent);
    }

    @OnClick({R.id.base_btn, R.id.merge_btn, R.id.filter_btn, R.id.boolean_btn, R.id.polymerization_btn, R.id.convert_btn, R.id.transformation_btn, R.id.retry_btn,R.id.subject_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_btn:
                baseCreate();
                break;
            case R.id.merge_btn:
                toMergeOperator();
                break;
            case R.id.filter_btn:
                toFilterOperator();
                break;
            case R.id.boolean_btn:
                toBooleanOperator();
                break;
            case R.id.polymerization_btn:
                toPolymerizationOperator();
                break;
            case R.id.convert_btn:
                toConvertOperator();
                break;
            case R.id.transformation_btn:
                toTransformationOperator();
                break;
            case R.id.retry_btn:
                toRetryOperator();
                break;
            case R.id.subject_btn:
                toSubject();
                break;
        }
    }

    private void toBooleanOperator() {
        Intent toCreateIntent = new Intent(this, ConditionOrBooleanActivity.class);
        startActivity(toCreateIntent);
    }

    public void toPolymerizationOperator() {
        Intent toCreateIntent = new Intent(this, PolymerizationActivity.class);
        startActivity(toCreateIntent);
    }

    public void toConvertOperator() {
        Intent toConvertIntent = new Intent(this, ConverActivity.class);
        startActivity(toConvertIntent);
    }

    public void toTransformationOperator() {
        Intent toConvertIntent = new Intent(this, TransformationActivity.class);
        startActivity(toConvertIntent);
    }

    public void toRetryOperator() {
        Intent toConvertIntent = new Intent(this, RetryActivity.class);
        startActivity(toConvertIntent);
    }

    public void toSubject(){
        Intent toConvertIntent = new Intent(this, SubjectOrSingleActiviyty.class);
        startActivity(toConvertIntent);
    }
}
