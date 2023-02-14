package com.kyjsoft.ex83retrofit2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kyjsoft.ex83retrofit2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    // HTTP통신 작업을 위한 Library 사용해보기

    // retrofit2 라이브러리 사용해보기
    // 특징 : json data를 주고받는 데 특화
    // 어노테이션 @ 많이 사용한대 -> 인터페이스를 알아듣게 할라고

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(view -> clickBtn());
        binding.btn2.setOnClickListener(view -> clickBtn2());
        binding.btn3.setOnClickListener(view -> clickBtn3());
        binding.btn4.setOnClickListener(view -> clickBtn4());
        binding.btn5.setOnClickListener(view -> clickBtn5());
        binding.btn6.setOnClickListener(view -> clickBtn6());
        binding.btn7.setOnClickListener(view -> clickBtn7());
        binding.btn8.setOnClickListener(view -> clickBtn8());
        binding.btn9.setOnClickListener(view -> clickBtn9());

    }

    void clickBtn9(){
        // 서버 응답 결과를 text로 받아오기 json문자열이 아닐 때는 반드시 필요한 기술
        // json이 아니므로 GsonConverter를 사용하지 않음.
        // 단순하게 글씨를 결과를 받는 Converter : ScalarsConverter(라이브러리 추가 필요)

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://kyjsoft.dothome.co.kr/");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.getPlainText();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s = response.body();
                binding.tv.setText(s);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                binding.tv.setText("error :" + t.getMessage());
            }
        });


    }

    void clickBtn8(){

        // 서버의 응답데이터가 json array일 때, 자동 파싱하여 ArrayList로 곧바로 받기

        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<ArrayList<Item>> call= retrofitService.getBoardArray();
        call.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                ArrayList<Item> items = response.body();

                StringBuffer buffer = new StringBuffer();
                for(Item item: items){
                    buffer.append(item.no + "." +item.title + item.msg + "\n");
                    binding.tv.setText(buffer.toString());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                binding.tv.setText("error : ");
            }
        });


    }



    void clickBtn7(){

        // POST방식으로 데이터를 따로따로 보내기 -> 마치 GET방식의 @Query로 보냈던 것처럼
        int no = 2;
        String title = "데이터 따로따로";
        String message = " 보내는거야";

        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<Item> call = retrofitService.postMethodTest2(no, title, message);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.no + "." + item.title + item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error : "+ t.getMessage());
            }
        });




    }


    void clickBtn6(){

        Item item = new Item(1, "POST방식으로", "보내기");


        // POST방식으로 데이터 보내고 php에서 json으로 만들어서 다시 안드로이드로 보내면 그 데이터를 textview에 보여주기
        // hashmap처럼 item객체로 만들어서 멤버변수의 이름을 식별자로
        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class); // 어떤 인터페이스 쓸건지

        Call<Item> call = retrofitService.postMethodTest(item); // 인터페이스안에 쓸 함수
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item i = response.body();
                binding.tv.setText(i.no + "." + i.title + i.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error : "+ t.getMessage());
            }
        });
    }


    void clickBtn5(){

        // GET 방식으로 보낼 데이터들을 한방에 보내기(map collection)
        // 서버에 전달할 데이터들을 하나의 map 으로 묶기 <식별자, 값>
        HashMap<String, String> datas = new HashMap<>();
        datas.put("title", "해시맵이용하는거야.");
        datas.put("msg", "해시맵이용할때는 put.");

        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<Item> call = retrofitService.getMethodTest3(datas);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.title + " " + item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error : "+ t.getMessage());
            }
        });
    }


    void clickBtn4(){

        // GET 방식으로 주소창에 데이터 전달+ 경로로 파라미터로 -> 어노테이션으로 함수안에 파라미터 여러개 준다는 거임.

        String title = "화이팅";
        String message = "화이팅";

        // 별도 클래스의 기능으로 retrofit 객체 쉽게 만들기~!
        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<Item> call = retrofitService.getMethodTest2("getTest.php", title, message);

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.title + " " +item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error :"+ t.getMessage());
            }
        });



    }


    void clickBtn3(){
        // 안드로이드에서 데이터를 보내고 http통신으로 웹서버에서 json으로 변환해서 나한테 주면 나는 그 json을 문자열로 변환해서 textview에 보여주는거임

        String title = " 안녕하세요 만나서 반갑다.";
        String message = "만나서 반가워라.";

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://kyjsoft.dothome.co.kr/");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // RetrofitService인터페이스 객체 생성하고 그 안에서 쓸 함수 호출
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<Item> call = retrofitService.getMethodTest(title, message);

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.title + " " +item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error : "+ t.getMessage());
            }
        });
    }



    void clickBtn2(){
        // 경로의 이름을 파라미터로 전달하여 데이터 가져오기

        // 1. retrofit 객체 생성
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://kyjsoft.dothome.co.kr/");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // 2. Retrofitservice 인터페이스 설계 ( 원하는 GET, POST 동작을 하는 추상메소드 설계 )

        // 3. 인터페이스를 객체로 생성해야 메소드를 쓸 수 있음.
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // 4. 위 단계에서 설계한 추상메소드 호출
        Call<Item> call = retrofitService.getJsonToPath("04Retrofit","board.json");

        // 5. 네트워크 시작!
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                // 응답된 json을 Gson을 이용해서 Item객체로 자동 파싱한 결과 받기
                Item item = response.body(); // 이게 gson으로 파싱한 결과물임.
                binding.tv.setText(item.no + "." + item.title + ": " + item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("실패 : "+ t.getMessage());
            }
        });
    }


    void clickBtn(){
        // 단순하게 get방식으로 json문자열 읽어오기

        // Retrofit Library를 이용하여 서버에서 json data를 읽어와서 item객체로 곧바로 파싱

        // 1. Retrofit 객체 생성(대신 만들어주는 빌더가 있음. -> retrofit을 만들게 아님)
        Retrofit.Builder builder = new Retrofit.Builder();
        // 도메인과 경로를 구분해서 빌더한데 세팅, 메소드 이름 잘봐
        builder.baseUrl("http://kyjsoft.dothome.co.kr/");
        builder.addConverterFactory(GsonConverterFactory.create()); // json을 원하는 객체로 변환해주는 공장을 만들어라.
        Retrofit retrofit = builder.build(); // 기본주소랑 json가지고 객체로 변환하는 컨버터를 가진 retrofit 객체 생성~!

        // 2. service 인터페이스 설계 ( 원하는 GET, POST 동작을 하는 추상메소드 설계 ) ->  통신이 이루어졌으면 하는 방법과 경로를 지정해주는 코드

        // 3. 2단계에서 설계한 Retrofitservice 인터 페이스를 우선을 객체로 생성( 인터페이스들 알아서 코드 써주는 단계 )
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // 4. 위에서 만든 서비스 객체의 추상 메소드를 호출하여서 실제 서버 작업을 수행하는 코드를 가진 Call 객체를 리턴 받자
        Call<Item> call = retrofitService.getjson(); // 여기까지 하면 run만 안한 상태 -> 코드 완성시키는 단계

        // 5. 위 4단계에서 call 객체에게 네트워크 작업을 수행하도록 요청
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                // 파라미터로 전달된 응답 객체로부터 Gson라이브러리에 의해 자동으로 아이템 객체로 변환되어 있는 data값을 얻어오기
                Item item = response.body(); // 헤더말고 내용물만
                binding.tv.setText(item.no + "." + item.title + " : " + item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("실패 : "+ t.getMessage());

            }
        });
    }






}