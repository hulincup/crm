package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.Log;
import com.shsxt.crm.vo.LogWithBLOBs;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMapper extends BaseMapper<LogWithBLOBs,Integer> {

}