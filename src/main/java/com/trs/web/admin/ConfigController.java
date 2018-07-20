package com.trs.web.admin;

import com.alibaba.fastjson.JSON;
import com.trs.model.Config;
import com.trs.service.ConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zly on 2017-6-15.
 */
@Controller
public class ConfigController extends AbstractAdminController {

    @Resource
    private ConfigService configService;


    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public ModelAndView setting() throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        String configName = "index-resource";

        Config config = configService.getValue(configName);
        if (config != null) {
            Map indexResourceMap = (Map) JSON.parse(config.getValue());
            modelAndView.addObject("indexResourceMap", indexResourceMap);
        }

        modelAndView.setViewName("admin/config/config");
        modelAndView.addObject("title", "设置");

        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    public Map saveConfig(String book, String journal, String standard) throws Exception {
        Map resultMap = new HashMap();

        String configName = "index-resource";
        Map paramMap = new HashMap();
        paramMap.put("book", book);
        paramMap.put("journal", journal);
        paramMap.put("standard", standard);
        String s = JSON.toJSONString(paramMap);


        Config config = configService.getValue(configName);
        if (config != null) {

            config.setValue(s);
            int i = configService.updateConfig(config);
            resultMap.put("code", i);
        } else {
            int i = configService.save(configName, s);
            resultMap.put("code", i);
        }

        return resultMap;
    }

}
