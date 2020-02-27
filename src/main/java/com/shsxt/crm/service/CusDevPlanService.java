package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CusDevPlan;
import com.shsxt.crm.vo.SaleChance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;
    public void saveCusDevPlan(CusDevPlan cusDevPlan){
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
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(cusDevPlan)<1,"计划项纪录添加失败");

    }

    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        AssertUtil.isTrue(saleChanceId==null||saleChanceMapper.selectByPrimaryKey(saleChanceId)==null,"请输入销售机会id");
        AssertUtil.isTrue(planItem==null,"请输入计划项内容");
        AssertUtil.isTrue(planDate==null,"请输入指定计划项时间");
    }

    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *     id  非空 记录存在
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         updateDate
         *  3.执行更新  判断结果
         */
        AssertUtil.isTrue(cusDevPlan.getId()==null||selectByPrimaryKey(cusDevPlan.getId())==null,"请选择待更新的记录");
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项记录更新失败");

    }

    public void delCusDevPlan(Integer id){
        CusDevPlan cusDevPlan = selectByPrimaryKey(id);
        AssertUtil.isTrue(id==null || cusDevPlan==null,"删除的记录不存在");
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项纪录删除失败");
    }

}
