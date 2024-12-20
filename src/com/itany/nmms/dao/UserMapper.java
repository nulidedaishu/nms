package com.itany.nmms.dao;

import com.itany.nmms.entity.Staff;
import com.itany.nmms.entity.User;
import com.itany.nmms.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int countByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int deleteByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Jun 23 19:02:29 CST 2024
     */
    int updateByPrimaryKey(User record);

    List<User> selectByParams(User param);

}