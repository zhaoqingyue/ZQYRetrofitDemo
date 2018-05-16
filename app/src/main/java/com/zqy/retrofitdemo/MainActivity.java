package com.zqy.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_post)
    EditText et_post;

    @BindView(R.id.btn_post)
    Button btn_post;

    @BindView(R.id.tv_post_text)
    TextView tv_post_text;

    @BindView(R.id.et_get)
    EditText et_get;

    @BindView(R.id.btn_get)
    Button btn_get;

    @BindView(R.id.tv_get_text)
    TextView tv_get_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_post, R.id.btn_get})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post: {
                Toast.makeText(this, "post请求", Toast.LENGTH_SHORT).show();
                String input = et_post.getText().toString();
                if (!TextUtils.isEmpty(input)) {
                    requestPost(input);
                }
                break;
            }
            case R.id.btn_get: {
                Toast.makeText(this, "get请求", Toast.LENGTH_SHORT).show();
                requestGet();
                break;
            }
        }
    }

    private void requestPost(String input) {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        PostRequestInterface request = retrofit.create(PostRequestInterface.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<PostTranslation> call = request.getCall(input);

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<PostTranslation>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<PostTranslation> call, Response<PostTranslation> response) {
                // 请求处理,输出结果
                // 输出翻译的内容
                final String target = response.body().getTranslateResult().get(0).get(0).getTgt();
                System.out.println("翻译是："+ target);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        tv_post_text.setText(target);
                    }
                });
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<PostTranslation> call, Throwable throwable) {
                System.out.println("请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestGet() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);

        //对 发送请求 进行封装
        Call<GetTranslation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<GetTranslation>() {
            //请求成功时候的回调
            @Override
            public void onResponse(Call<GetTranslation> call, Response<GetTranslation> response) {
                //请求处理,输出结果
                response.body().show();
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<GetTranslation> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }
}
