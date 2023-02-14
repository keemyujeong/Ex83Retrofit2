package com.kyjsoft.ex83retrofit2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    // 기본 서버주소 멤버변수로
    public static String baseUrl = "http://kyjsoft.dothome.co.kr/";

    // retrofit객체를 만들어서 리턴해주는 기능 메소드 (객체 생성 없이 사용가능하도록 -> static 사용하기 -> 다른 패키지 안에서도 사용할 수 있도록 public)
    public static Retrofit getRetrofitInstance(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        return retrofit;
    }



}
