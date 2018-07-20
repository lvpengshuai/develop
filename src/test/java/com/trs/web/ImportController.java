package com.trs.web;

import com.trs.core.util.ExcelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by 李春雨 on 2017/2/23.
 */
@Controller
public class ImportController {

    @RequestMapping(value = "/test")
    public ModelAndView tets(MultipartFile file){
        try {
            List<Map> maps = ExcelUtils.read(file);
            for(Map<String,String> map:maps){
                String value = map.get("期刊名称");
                System.out.println(value);
            }
            System.out.println(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
