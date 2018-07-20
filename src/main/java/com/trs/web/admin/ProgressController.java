package com.trs.web.admin;

import com.trs.core.util.ProgressEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class ProgressController {

    @ResponseBody
    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public Map initCreateInfo(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        ProgressEntity ps = (ProgressEntity) session.getAttribute("upload_ps");
        NumberFormat nf = NumberFormat.getPercentInstance();
        if (null == ps) {
            result.put("status", "0%");
            return result;
        }
        nf.setMaximumFractionDigits(0);
        if (ps.getpContentLength() == 0) {
            result.put("status", "0%");
            return result;
        }
        result.put("status", nf.format((double) ps.getpBytesRead() / (double) ps.getpContentLength()));
        /*if(result.get("status").equals("100%")){
            session.removeAttribute("upload_ps");
        }*/
        return result;
    }
}

