package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author lynn
 */
//@Repository
public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserByUserName(String userName);
    public List<Map<String,Object>> queryAllCustomerManager();

}