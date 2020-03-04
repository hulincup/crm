package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.Customer;

import java.util.List;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {
    Customer queryCustomerByName(String name);
    public List<Customer> queryLossCustomers();
    public int updateCustomerStateByIds(List<Integer> lossCusIds);

}