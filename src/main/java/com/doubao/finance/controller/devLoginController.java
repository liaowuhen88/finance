package com.doubao.finance.controller;

import com.doubao.finance.model.request.BillingQueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class devLoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(devLoginController.class);

    @ResponseBody
    @RequestMapping(value = {"/dev_login"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public void list(HttpServletRequest request, HttpServletResponse response, BillingQueryCondition queryCondition) {
        try {
            request.getSession().setAttribute("UserName", "abc");

            //response.sendRedirect("/");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
