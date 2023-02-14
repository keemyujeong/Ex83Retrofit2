package com.kyjsoft.ex83retrofit2;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {

    // 1. GET방식으로 json문자열을 읽어오기 -> get방식은 이미지를 불러오기 어려움.
    @GET("04Retrofit/board.json") // retrofit이 이미 baseurl 기본주소를 알고 있으니까 여기 다가 폴더 경로만 써주셈
    Call<Item> getjson(); // 인터페이스는 기능의 이름만 써. 기능을 안써. 안 써 -> 이런 기능을 구현하고 싶어 @어노테이션으로 남기기
    // Call<Item> -> 결과물을 여기다가 쓰는 거야 json으로 Item객체를 만들려는 거니까
    // 이렇게 하면 알아서 코드(스레드, 인풋스트림, 아웃풋스트림, Url)를 써주는 데 그 결과(코드 자체)를 리턴해줘 -> 정해진 클래스안에 넣어줌. -> 그 클래스 안에서 인터넷 접속이 되는거임.

    // 2. get방식으로 폴더 경로가 다양할때 그때 그때 바꿔쓸수 있도록 -> 경로의 이름을 고정X 함수의 파라미터로 전달받아서 지정하기
    @GET("{folder}/{filename}")
    Call<Item> getJsonToPath(@Path("folder") String path, @Path("filename") String filename); // folder, filename 값을 바꿔치기 할때

    // 3. get방식으로 데이터를 서버에 전달해보기 -> @query써서 데이터 보내기
    @GET("04Retrofit/getTest.php")
    Call<Item> getMethodTest(@Query("title") String title, @Query("msg") String msg); // @Query('식별자(php에서 정해놓은 파라미터)')를 쓰면 알아서 ?붙이고 저함수 파라미터들 붙임.

    // 4. 2번+3번 파라미터 경로랑 데이터 다 받도록
    @GET("04Retrofit/{filename}")
    Call<Item> getMethodTest2(@Path("filename") String filename, @Query("title") String title, @Query("msg") String msg);

    // 5. 4번꺼를 hashmap으로 만들어서 한방에 보내기 [@QueryMap]
    @GET("04Retrofit/getTest.php")
    Call<Item> getMethodTest3(@QueryMap Map<String, String> datas); // 맵 안에 이미 식별자가 들어가 있어서 따로 여기서 쓸필요 없음

    // 6. POST방식으로 data 보내기( item 객체를 전달하면 문자열로 변환해서 보냄 )
    @POST("04Retrofit/postTest.php")
    Call<Item> postMethodTest(@Body Item item); // @Body -> 얘가 내부적으로 객체를 json문자열 형태로 변환해서 주는 Call함수를 만든거야.

    // 7. POST방식으로 개별 데이터 전달하기(** @field를 사용하려면 추가로 반드시 @FormUrlEncoded와 함께 써야함.)
    @FormUrlEncoded
    @POST("04Retrofit/postTest2.php")
    Call<Item> postMethodTest2(@Field("no") int no, @Field("title") String title, @Field("msg") String msg);

    // 8. GET방식으로 json array를 받아서 읽어와서 곧바로 ArrayList<Item> 변환
    @GET("04Retrofit/boardArray.json")
    Call<ArrayList<Item>> getBoardArray(); // json배열 일 때 ArrayList<Item>이걸 받겠다.

    // 9. GET방식으로 서버로부터 응답을 받되, 그냥 글씨로 파싱 X
    @GET("04Retrofit/board.json")
    Call<String> getPlainText(); // Item으로 하면 파싱해서 주는 거, String그냥 글씨로 주는 거





}
