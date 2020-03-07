package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.CustomerLossMapper;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CusDevPlan;
import com.shsxt.crm.vo.CustomerRep;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerRepService extends BaseService<CustomerRep,Integer> {

    @Resource
    private CustomerLossMapper customerLossMapper;

    public void saveCustomerRep(CustomerRep customerRep){
        /**
         * 1.参数校验
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         *    is_valid createDate  updateDate
         *
         *  3.执行添加  判断结果
         */
        checkParams(customerRep.getLossId(),customerRep.getMeasure());
        customerRep.setCreateDate(new Date());
        customerRep.setUpdateDate(new Date());
        customerRep.setIsValid(0);
        AssertUtil.isTrue(insertSelective(customerRep)<1,"计划项纪录添加失败");
    }

    private void checkParams(Integer lossId, String measure) {
        AssertUtil.isTrue(lossId==null||customerLossMapper.selectByPrimaryKey(lossId)==null,"请输入正确的流失ID");
        AssertUtil.isTrue(StringUtils.isBlank(measure),"请输入暂缓流失措施");
    }

}
