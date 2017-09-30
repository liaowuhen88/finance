package com.doubao.finance.controller;

import com.instance.cas.utill.SessionUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/user"})
public class UserController
{
    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Value("${cas.server.login.url}")
    private String casServerUrl;
    @Value("${cas.url.finance}")
    private String financeUrl;

    @RequestMapping(value={"/logout"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public void logout(HttpServletResponse response)
    {
        String url = this.casServerUrl + "/logout?service=" + this.financeUrl;
        SessionUtil.destroySession();
        try
        {
            response.sendRedirect(url);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
