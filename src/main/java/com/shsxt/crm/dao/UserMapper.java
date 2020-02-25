package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.User;
import org.springframework.stereotype.Repository;

/**
 * @author lynn
 */
@Repository
public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserByUserName(String userName);
}