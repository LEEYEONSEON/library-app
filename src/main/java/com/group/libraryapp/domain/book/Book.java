package com.group.libraryapp.domain.book;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 255) //필드이름이랑 같아 생략 가능 name="name"
    private String name;

    //jpa는 기본생성자가 필요하기 때문에
    protected Book() {
    }

    //id는 자동생성이니 이름만 받는 생성자를 만들어줌
    public Book(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
