package com.group.libraryapp.dto.user.request;
//API SPEC에서 정해진 것처럼 id, name을 받아야 함.
//id, name 변수를 생성하고, Getter를 통해 getId(), getName() 만들어주기
public class UserUpdateRequest {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
