package com.trs.web.admin;

import com.trs.core.util.Util;
import com.trs.model.Book;
import com.trs.model.BookClassify;
import com.trs.model.HotWord;
import com.trs.service.BookService;
import com.trs.service.HotWordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/hot")
public class HotController extends AbstractAdminController {

    @Resource
    private HotWordService hotWordService;
    @Resource
    private BookService bookService;
    /**
     * 获取热词
     *
     * @param map
     * @return
     */
    @RequestMapping("/word")
    public String word(Map map) {
        List<HotWord> hotWords = hotWordService.getHotWords();
        map.put("hotWords", hotWords);
        return "admin/hot/word";
    }

    /**
     * 跳转到添加热词
     * @return
     */
    @RequestMapping("/word/add")
    public String wordAdd() {
        return "admin/hot/addWord";
    }

    /**
     * 保存热词
     * @param hot
     * @return
     */
    @RequestMapping("/word/save")
    public String wordSave(String hot) {
        hotWordService.addHotWord(hot);
        return "redirect:/admin/hot/word";
    }

    /**
     * 删除热词
     *
     * @param
     * @return
     */
    @RequestMapping("/word/remove/{id}")
    public String wordRemove(@PathVariable String id) {
        hotWordService.deleteHotWord(id);
        return "redirect:/admin/hot/word";
    }

    /**
     * 修改热词状态
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/word/publish/{id}/{status}")
    public String publish(@PathVariable String id,@PathVariable String status) {
        hotWordService.updateHotWord(id,status);
        return "redirect:/admin/hot/word";
    }

    /**
     * 修改热词word跳转
     * @param map
     * @return
     */
    @RequestMapping("/word/update")
    public String update(Map map) {
        List<HotWord> hotWord = hotWordService.getHotWords();
        map.put("hotWord",hotWord.get(0));
        return "admin/hot/addWord";
    }
    /**
     * 修改热词word
     *
     * @param
     * @return
     */
    @RequestMapping("/word/updateWord/{id}")
    public String updateWord(String hot,@PathVariable String id) {
        hotWordService.updateHotWordHot(id,hot);
        return "redirect:/admin/hot/word";
    }

    /**
     * 跳转到轮播跟换
     * @return
     */
    @RequestMapping("/search")
    public String search() {
        return "admin/hot/search";
    }

    /**
     * 查询所有用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchs", method = RequestMethod.GET)
    public Map searchs(HttpServletRequest request) {

        String pageSize = request.getParameter("pageSize");
        String currPage = request.getParameter("currPage");
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String state = request.getParameter("state");

        List<Book> bookAll = hotWordService.getBookAll(pageSize,currPage,search);
        Map map = new HashMap();
        map.put("total", hotWordService.getBookAllNum());
        map.put("rows", bookAll);
        map.put("pageSize", Integer.parseInt(pageSize));
        map.put("currPage", Integer.parseInt(currPage));
        map.put("search", search);
        map.put("sort", sort);
        map.put("order", order);
        map.put("state", state);
        return map;

    }

    /**
     * 更新
     * @param id
     * @param state
     * @return
     */
    @RequestMapping(value = "/update/{id}/{state}",method = RequestMethod.POST)
    @ResponseBody
    public Map update(@PathVariable List<String> id,@PathVariable String state) {
        hotWordService.updateBookHotType(id,state);
        Map map=new HashMap();
        return map;
    }

    /**
     * 热词分管理
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/bookClassify")
    public ModelAndView bookClassify(HttpServletRequest request, HttpServletResponse response) {
        String isSubmit = Util.toStr(request.getParameter("isSubmit"));
        if(!isSubmit.equals("")) {
            String[] bookabbreviate = request.getParameterValues("bookabbreviate");
            List<Map<String, Object>> list = new ArrayList<>();

            for (int i = 0; i < bookabbreviate.length; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("sortkey", i+101);
                map.put("bookabbreviate", bookabbreviate[i]);
                list.add(map);
            }
            hotWordService.updateBookClassofyOrder(list);
        }
        List<BookClassify> bookClassify = hotWordService.getBookClassofyOrder();
        ModelAndView modelAndView = new ModelAndView("admin/hot/bookClassify");
        modelAndView.addObject("list", bookClassify);
        return modelAndView;
    }

}
