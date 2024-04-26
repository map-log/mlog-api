package com.mlog.user.repository;

import com.mlog.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("select * from user where email = #{email}")
    Optional<User> findByEmail(String email);

    @Select("select * from user where id = #{id}")
    Optional<User> findById(Long id);

    @Insert("insert into user (email, name, password, role, created_at, updated_at) " +
            "values (#{email}, #{name}, #{password}, #{role}, #{createdAt}, #{updatedAt}) ")
    void save(User user);

    @Delete("delete from user where id = #{id}")
    void delete(Long id);

    @Update("update user set email=#{email}, name=#{name}, password=#{password}, role=#{role}, updated_at=#{updatedAt} " +
            "where id = #{id}")
    void update(User user);
}
