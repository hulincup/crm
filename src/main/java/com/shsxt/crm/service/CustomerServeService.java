package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.CustomerMapper;
import com.shsxt.crm.dao.CustomerServeMapper;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.enums.CustomerServeStatus;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CustomerServe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerServeService extends BaseService<CustomerServe,Integer> {
    @Resource
    private CustomerServeMapper customerServeMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private UserMapper userMapper;

    public void saveOrUpdateCustomerServe(CustomerServe customerServe){
        if (customerServe.getId()==null){
            /**  服务添加操作
             * 1.参数校验
             *     客户名  非空
             *     客户类型  非空
             * 2.添加默认值
             *    state  设置状态值
             *    isValid  createDate updateDate
             *  3.执行添加 判断结果
             */
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()),"请指定客户");
            AssertUtil.isTrue(customerMapper.queryCustomerByName(customerServe.getCustomer())==null,"当前客户暂不存在");
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()),"客户类型不能为空");
            customerServe.setIsValid(1);
            customerServe.setUpdateDate(new Date());
            customerServe.setUpdateDate(new Date());
            customerServe.setState(CustomerServeStatus.CREATED.getState());
            AssertUtil.isTrue(insertSelective(customerServe)<1,"服务记录添加失败");

        }else {
            /**
             * 分配  处理  反馈
             */
            CustomerServe temp =  selectByPrimaryKey(customerServe.getId());
            AssertUtil.isTrue(null == temp,"待处理的服务记录不存在!");
            if(customerServe.getState().equals(CustomerServeStatus.ASSIGNED.getState())){
                // 服务分配
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getAssigner())||
                        (null == userMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getAssigner()))),"待分配用户不存在");
                customerServe.setAssignTime(new Date());
                customerServe.setUpdateDate(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务分配失败!");
            } if(customerServe.getState().equals(CustomerServeStatus.PROCEED.getState())){
                // 服务处理
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"请指定处理内容!");
                customerServe.setServiceProceTime(new Date());
                customerServe.setUpdateDate(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务处理失败!");
            }if(customerServe.getState().equals(CustomerServeStatus.FEEDBACK.getState())){
                // 服务反馈
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"请指定反馈内容!");
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"请指定反馈满意度!");
                customerServe.setUpdateDate(new Date());
                customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务反馈失败!");
            }
        }
    }

}
