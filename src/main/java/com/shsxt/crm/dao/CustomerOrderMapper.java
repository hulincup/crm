package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.CustomerOrder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {

    public Map<String,Object> queryOrderDetailByOrderId(Integer orderId);
    public CustomerOrder queryLastCustomerOrderByCusId(Integer cusId);
}