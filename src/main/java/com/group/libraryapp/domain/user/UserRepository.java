package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //이름에 해당하는 User을 찾는 함수 만들기
    Optional<User> findByName(String name);
    boolean existsByName(String name);
    long countByAge(Integer age);
}
