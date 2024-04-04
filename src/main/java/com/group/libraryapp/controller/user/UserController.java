package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserServiceV1;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    //user을 메모리에 저장해주던 코드를 지워줌

    //users: User 객체를 담는 리스트
    //private final List<User> users = new ArrayList<>();

    //JdbcTemplate: Java database connector에 대한 클래스
    //ㄴ  이걸 통해 database에 접근가능

    //jdbcTemplate을 선언하고, jdbcTemplate을 받아서 초기화시키는 UserController 생성자 생성

    private final UserServiceV2 userService;

    public UserController(UserServiceV2 userService){
        this.userService = userService;
    }

    @PostMapping("/user")   // POST /user
    public void saveUser(@RequestBody UserCreateRequest request){
        //user를 저장하기 위해 User라는 객체를 만들어 저장예정
        //users.add(new User(request.getName(), request.getAge()));
        userService.saveUser(request);
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers(){
        return userService.getUsers();
    //목표: id, 이름, 나이 반환
    //dto에 UserResponse 를 만들어준 후,
    //
//        List<UserResponse> responses = new ArrayList<>();
//        for(int i=0; i<users.size(); i++){
//            responses.add(new UserResponse(i+1, users.get(i)));
//        }
//        return responses;
}
//수정시에 예외처리:
    //데이터 존재 여부 확인: 조회용 SQL 작성
    //jdbcTeplate.query()메서드는 하나라도 들어온 id의 값과 맞는 데이터가 존재하면
    //0이 존재하는 list를 반환하고 그러면 isUserNotExist 변수는 false값을 가지게 됨
    //id의 값과 맞는 데이터가 하나도 존재하지 않는다면, 비어있는 list가 나오고
    //isUserNotExist 변수는 true 값의 가짐
    //(rs, rowNum)->0: 만약 readSql의 값이 있다면 0을 반환해줌
//http를 통해 받아온 body가 UserUpdateRequest의 객체로 들어감
@PutMapping("/user")
public void updateUser(@RequestBody UserUpdateRequest request){
    userService.updateUser(request);
}

//삭제시에도 삭제할 이름이 table에 없다면 예외처리해줌

//http를 통해 query를 통해 정보를 받아 오기 때문에 @RequestParma 사용하고,
//삭제되어야 하는 사용자 이름을 String형 name 변수로 인자에 받음
//정보가 UserDeleteResponse의 객체로 들어감
@DeleteMapping("/user")
public void  deleteUser(@RequestParam String name){
    userService.deleteUser(name);
}




}
