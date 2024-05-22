package com.mlog.user.repository;

import com.mlog.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("select * from users where email = #{email}")
    Optional<User> findByEmail(String email);

    @Select("select * from users where id = #{id}")
    Optional<User> findById(Long id);

    @Insert("insert into users (email, name, password, role, created_at, updated_at) " +
            "values (#{email}, #{name}, #{password}, #{role}, #{createdAt}, #{updatedAt}) ")
    void save(User user);

    @Delete("delete from users where id = #{id}")
    void delete(Long id);

    @Update("update users set email=#{email}, name=#{name}, password=#{password}, role=#{role}, updated_at=now() " +
            "where id = #{id}")
    void update(User user);

    @Update("update users set password=#{password}, updated_at=now() " +
            "where email = #{email}")
    void updatePassword(String email, String password);
}
