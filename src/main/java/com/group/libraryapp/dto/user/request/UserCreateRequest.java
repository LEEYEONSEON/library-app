package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {
    private String name;
    private Integer age;    //null을 표현할 수 있기 때문에 int가 아닌 Integer형

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
