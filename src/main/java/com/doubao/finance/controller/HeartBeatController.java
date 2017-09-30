package com.doubao.finance.controller;

import com.doubao.finance.dao.BillingDao;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HeartBeatController
{
    @Autowired
    private BillingDao billingDao;

    @RequestMapping({"/api/monitor"})
    public ModelAndView echo(Map<String, Object> map)
    {
        String dbOk = "OK";
        try
        {
            long l = this.billingDao.echo();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            dbOk = "NO";
        }
        map.put("ALL_PASS", dbOk);
        return new ModelAndView("monitor", map);
    }
}

