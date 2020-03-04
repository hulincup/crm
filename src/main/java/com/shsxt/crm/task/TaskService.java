package com.shsxt.crm.task;

import com.shsxt.crm.service.CustomerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TaskService {

    @Resource
    private CustomerService customerService;

    //@Scheduled(cron = "0/2 * * * * ?")
    public void task(){
        System.out.println("定时任务执行->"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        customerService.updateCustomerState();
    }

}