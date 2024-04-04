package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean isUserNotExist(long id){
        String readSql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum)->0, id).isEmpty();

    }
    public boolean isUserNotExist(String name){
        String readSql = "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum)->0, name).isEmpty();
    }
    public void saveUserName(String name, int age){
        String sql = "INSERT INTO user (name, age) VALUES(?, ?)";
        jdbcTemplate.update(sql, name, age);
    }
    public List<UserResponse> getUsersName(){
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }
    public void updateUserName(String name, long id){
        //UserUpdateRequest 객체 request 안에서 특정 id를 가진 User의 name을
        //변경해주어야 함.
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        //POST에서도 사용해주었던 JdbcTemplate.update()메서드
        //인자로 sql과 ?에 들어갈 값들을 차례로 받음
        jdbcTemplate.update(sql, name, id);
    }
    public void deleteUserName(String name){
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }
}
