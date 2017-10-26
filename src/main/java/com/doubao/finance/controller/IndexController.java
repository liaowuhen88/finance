package com.doubao.finance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = {"/index"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView list() {
        // return new ModelAndView("realCollection/realCollectionList");
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"/gatewayLog/login"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView login() {
        return new ModelAndView("index");

    }
}
