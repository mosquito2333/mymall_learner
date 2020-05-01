package com.mymall.dao;

import com.mymall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);
    int insert(User record);
    int insertSelective(User record);
    User selectByPrimaryKey(Integer userid);
    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);
    int checkUsername(String username);
    int checkEmail(String email);
    User selectLogin(@Param("username") String username, @Param("password") String password);
    String selectQuestionByUsername(String username);
    int checkAnswer(@Param("username") String username,@Param("question")String question,@Param("answer")String answer);
    int updatePasswordByUsername(@Param("username") String username,@Param("passwordNew") String passwordNew);
    int checkPassword(@Param(value="password")String password,@Param("userid")Integer userid);
    int checkEmailByUserId(@Param(value="email")String email,@Param(value="userid")Integer userid);
}