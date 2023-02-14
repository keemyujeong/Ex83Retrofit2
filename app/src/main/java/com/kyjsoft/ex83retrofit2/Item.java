package com.kyjsoft.ex83retrofit2;

// json의 식별자와 값 쓰기
public class Item {
    int no;             // 이 멤버 변수의 이름은 json문자열의 식별자와 일치 해야함.
    String title;       // 이거 정수형도 String으로 만들어도 에러 안남, 자료형 헷갈리면 다 String쓰셈
    String msg;

    public Item(int no, String title, String msg) {
        this.no = no;
        this.title = title;
        this.msg = msg;
    }

    public Item() {
    }
}
