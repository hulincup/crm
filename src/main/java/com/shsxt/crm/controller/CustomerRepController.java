package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.query.CustomerRepQuery;
import com.shsxt.crm.service.CustomerLossService;
import com.shsxt.crm.service.CustomerRepService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_rep")
public class CustomerRepController extends BaseController {

    @Resource
    private CustomerLossService customerLossService;
    @Resource
    private CustomerRepService customerRepService;

    @RequestMapping("index")
    public String index(String cusNo, Model model){
        model.addAttribute("customerLoss",customerLossService.queryCustomerLossByCusNo(cusNo));
        return "customer_rep";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerRepsByParams(CustomerRepQuery customerRepQuery){
        return customerRepService.queryByParamsForDataGrid(customerRepQuery);
    }

}
