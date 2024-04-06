package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //아래 함수가 시작할 때, start transaction; 을 해줌
    //함수가 예외 없이 잘 끝났다면 commit
    //혹시라도 문제가 있다면 rollback;
    @Transactional
    public void saveUser(UserCreateRequest request){
        userRepository.save(userRepository.save(new User(request.getName(), request.getAge())));
    }
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                //            .map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
                //.map(Userresponse::new)
                .map(user -> new UserResponse(user))
                .collect(Collectors.toList());
    }
    @Transactional
    public void updateUser(UserUpdateRequest request){
        //영속성 컨텍스트 시작

        //1. 수정할 유저의 존재유무 확인
        // findById(): select * from user where id = ?; -> 반환값: Optional<User>
        //User가 존재한다면 아래 user에 User 객체가 들어옴. 아니면 그 밑의 줄 실행
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException:: new);
        //2. sql을 사용하지 않고, user의 객체를 가져와 업데이트
        user.updateName(request.getName());
        //userRepository.save(user);
    }
    @Transactional
    public void deleteUser(String name){
        //SELECT * FROM user WHERE name=?
        User user = userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
        if(user == null){
            throw new IllegalArgumentException();
        }
        userRepository.delete(user);
    }

}
