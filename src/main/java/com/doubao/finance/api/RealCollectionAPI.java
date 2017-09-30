package com.doubao.finance.api;

import com.doubao.finance.model.PageResponse;
import com.doubao.finance.model.request.RealCollectionQueryCondition;
import com.doubao.finance.service.RealCollectionService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/realCollection"})
public class RealCollectionAPI
{
    public static final Logger LOGGER = LoggerFactory.getLogger(RealCollectionAPI.class);
    @Autowired
    private RealCollectionService realCollectionService;

    @RequestMapping(value={"/query"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public void queryEntities(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="name", defaultValue="") String name, @RequestParam(value="callback", required=true) String callback)
    {
        if (LOGGER.isDebugEnabled())
        {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                LOGGER.debug("key:{},value:{}", entry.getKey(), Arrays.toString((Object[])entry.getValue()));
            }
        }
        RealCollectionQueryCondition condition = new RealCollectionQueryCondition();
        condition.setChargeEntityName(name);

        Object collectionPageResponse = this.realCollectionService.queryByCondition(condition);

        String result = callback + "(" + ((PageResponse)collectionPageResponse).getData().toString() + ")";
        try
        {
            response.getWriter().write(result);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
