package com.trs.web.admin;

import com.trs.model.Book;
import com.trs.model.BookDetails;
import com.trs.service.ResourceManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiangYong
 * @create 2017/11/21-14:27
 */
@Controller
@RequestMapping(value = "/admin")
public class SourceManagerController {

    @Resource
    private ResourceManagerService resourceManagerService;

    /**
     * 图书管理
     *
     * @param map
     * @return
     */
    //进入图书管理
    @RequestMapping(value = "/bookManager")
    public String bookManager(Map<String, Object> map) {
        map.put("title", "年鉴管理");
        Boolean model = (Boolean) map.get("model");
        map.put("model", model);
        return "admin/resources/bookManager";
    }

    //获取全部图书的数据
    @ResponseBody
    @RequestMapping(value = "/getAllBookManager")
    public Map<String, Object> getAllBookManager(String pageSize, String currPage, String order, String search, String sort) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("currPage", currPage);
        map.put("order", order);
        map.put("search", search);
        map.put("sort", sort);
        Map<String, Object> allBooks = resourceManagerService.getAllBooks(map);
        return allBooks;
    }

    //通过id去查出需要修改的图书
    @RequestMapping(value = "/updateBookManager/{id}", method = RequestMethod.GET)
    public String updateBookManager(@PathVariable String id, Map<String, Object> map) {
        Book bookById = resourceManagerService.getBookById(id);
        map.put("book", bookById);
        map.put("stitle", "修改：" + bookById.getTitle());
        map.put("title", "年鉴管理");
        map.put("href", "/admin/bookManager");
        return "admin/resources/updateBookManager";
    }

    //修改图书
    @RequestMapping(value = "/updateBookManager", method = RequestMethod.POST)
    public String updateBookManagerSubmit(Book book, Map<String, Object> map, RedirectAttributes attributes) {
        Boolean aBoolean = resourceManagerService.setBookByBook(book, map);
        //map.put("model",aBoolean);
        attributes.addFlashAttribute("model", aBoolean);
        if (aBoolean) {
            attributes.addFlashAttribute("manager", "修改成功");
        } else {
            attributes.addFlashAttribute("manager", "修改失败重新修改");
        }
        return "redirect:bookManager";
    }

    //删除book
    @ResponseBody
    @RequestMapping(value = "/deleteBookManager/{bookCodes}", method = RequestMethod.GET)
    public Boolean deleteBookManager(@PathVariable String[] bookCodes) {
        Boolean aBoolean = resourceManagerService.deleteBooks(bookCodes);
        return aBoolean;
    }

    /**
     * 人物词条
     *
     * @param map
     * @return
     */
    //跳转到人物词条
    @RequestMapping(value = "/personEntry")
    public String personEntry(Map<String, Object> map) {
        map.put("title", "人物词条");
        Boolean model = (Boolean) map.get("model");
        map.put("model", model);
        return "admin/resources/personEntry";
    }

    //查询出全部人物词条
    @ResponseBody
    @RequestMapping(value = "/getAllPersonEntry")
    public Map<String, Object> getAllPersonEntry(@RequestParam Map<String, Object> map) {
        Map<String, Object> allBooks = resourceManagerService.getAllPersonEntry(map);
        return allBooks;
    }

    //查出需要修改的人物词条
    @RequestMapping(value = "/setPersonEntry/{id}/{bookcode}", method = RequestMethod.GET)
    public String setPersonEntry(Map<String, Object> map, @PathVariable String id, @PathVariable String bookcode) {
        BookDetails personEntry = resourceManagerService.getPersonEntry(id, bookcode);
        map.put("stitle", "修改人物词条：" + personEntry.getTitle());
        map.put("title", "人物词条");
        map.put("href", "/admin/personEntry");
        map.put("bookDetails", personEntry);
        return "admin/resources/updatePersonEntrty";
    }

    //提交修改的人物词条
    @RequestMapping(value = "/setPersonEntrySubmit", method = RequestMethod.POST)
    public String setPersonEntrySubmit(BookDetails bookDetails, RedirectAttributes redirectAttributes) {
        Boolean aBoolean = resourceManagerService.setPersonEntry(bookDetails);
        redirectAttributes.addFlashAttribute("model", aBoolean);
        if (aBoolean) {
            redirectAttributes.addFlashAttribute("manager", "修改成功");
        } else {
            redirectAttributes.addFlashAttribute("manager", "修改失败重新修改");
        }
        return "redirect:personEntry";
    }

    //删除人物词条
    @ResponseBody
    @RequestMapping(value = "/deletePersonEntry/{ids}/{bookCodes}", method = RequestMethod.GET)
    public Boolean deletePersonEntry(@PathVariable String[] ids, @PathVariable String[] bookCodes) {
        Boolean aBoolean = resourceManagerService.delPersonEntry(ids, bookCodes);
        return aBoolean;
    }

    /**
     * 论文词条
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/paperEntry")
    public String paperEntry(Map<String, Object> map) {
        map.put("title", "论文词条");
        return "admin/resources/paperEntry";
    }

    /**
     * 查询全部论文词条
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/paperEntry")
    public Map<String, Object> getPaperEntry(@RequestParam Map<String, Object> map) {
        Map<String, Object> paperEntry = resourceManagerService.getAllPaperEntry(map);
        return paperEntry;
    }

    /**
     * 跳转到修改论文词条
     * @param map
     * @param id
     * @param bookcode
     * @return
     */
    @RequestMapping(value = "/set/paperEntry/{id}/{bookcode}", method = RequestMethod.GET)
    public String setPaperEntry(Map<String, Object> map, @PathVariable String id, @PathVariable String bookcode) {
        BookDetails paperEntry = resourceManagerService.getPaperEntry(id, bookcode);
        map.put("stitle", "修改论文词条：" + paperEntry.getTitle());
        map.put("title", "论文词条");
        map.put("href", "/admin/paperEntry");
        map.put("bookDetails", paperEntry);
        return "admin/resources/updatePaperEntry";
    }

    /**
     * 提交修改论文词条
     * @param bookDetails
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/set/paperEntry/submit", method = RequestMethod.POST)
    public String setPaperEntrySubmit(BookDetails bookDetails, RedirectAttributes redirectAttributes) {
        Boolean aBoolean = resourceManagerService.setPaperEntry(bookDetails);
        redirectAttributes.addFlashAttribute("model", aBoolean);
        if (aBoolean) {
            redirectAttributes.addFlashAttribute("manager", "修改成功");
        } else {
            redirectAttributes.addFlashAttribute("manager", "修改失败重新修改");
        }
        return "redirect:/admin/paperEntry";
    }

    /**
     * 删除论文词条
     * @param ids
     * @param bookCodes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del/paperEntry/{ids}/{bookCodes}", method = RequestMethod.GET)
    public Boolean delPaperEntry(@PathVariable String[] ids, @PathVariable String[] bookCodes) {
        Boolean aBoolean = resourceManagerService.delPaperEntry(ids, bookCodes);
        return aBoolean;
    }

    /**
     * 课题词条
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/topicEntry")
    public String topicEntry(Map<String, Object> map) {
        map.put("title", "课题词条");
        map.put("state", "立项");
        return "admin/resources/topicEntry";
    }

    /**
     * 查询全部课题词条
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/topicEntry")
    public Map<String, Object> getTopicEntry(@RequestParam Map<String, Object> map) {
        Map<String, Object> allTopicEntry = resourceManagerService.getAllTopicEntry(map);
        return allTopicEntry;
    }

    /**
     * 跳转到修改课题词条
     * @param map
     * @param id
     * @param bookcode
     * @return
     */
    @RequestMapping(value = "/set/topicEntry/{id}/{bookcode}")
    public String setTopicEntry(Map<String, Object> map, @PathVariable String id, @PathVariable String bookcode) {
        Map<String, Object> topicEntry = resourceManagerService.getTopicEntry(id, bookcode);
        map.put("stitle", "修改课题词条");
        map.put("title", "课题词条");
        map.put("href", "/admin/topicEntry");
        map.put("bookDetails", topicEntry.get("bookDetails"));
        map.put("exdataMap", topicEntry.get("exdataMap"));
        return "admin/resources/updateTopicEntry";
    }

    /**
     * 提交修改课题词条
     * @param bookDetails
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/set/topicEntry/submit", method = RequestMethod.POST)
    public String setTopicEntrySubmit(@RequestParam Map<String, Object> bookDetails, RedirectAttributes redirectAttributes) {
        Boolean aBoolean = resourceManagerService.setTopicEntry(bookDetails);
        redirectAttributes.addFlashAttribute("model", aBoolean);
        if (aBoolean) {
            redirectAttributes.addFlashAttribute("manager", "修改成功");
        } else {
            redirectAttributes.addFlashAttribute("manager", "修改失败重新修改");
        }
        return "redirect:/admin/topicEntry";
    }

    /**
     * 删除课题词条
     * @param ids
     * @param bookCodes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del/topicEntry/{ids}/{bookCodes}", method = RequestMethod.GET)
    public Boolean delTopicEntry(@PathVariable String[] ids, @PathVariable String[] bookCodes) {
        Boolean aBoolean = resourceManagerService.delPaperEntry(ids, bookCodes);
        return aBoolean;
    }

    /**
     * 图书词条
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/bookEntry")
    public String bookEntry(Map<String, Object> map) {
        map.put("title", "图书词条");
        return "admin/resources/bookEntry";
    }

    /**
     * 查询全部图书词条
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/bookEntry")
    public Map<String, Object> getBookEntry(@RequestParam Map<String, Object> map) {
        Map<String, Object> allTopicEntry = resourceManagerService.getAllBookEntry(map);
        return allTopicEntry;
    }

    /**
     * 跳转到修改图书词条
     * @param map
     * @param id
     * @param bookcode
     * @return
     */
    @RequestMapping(value = "/set/bookEntry/{id}/{bookcode}")
    public String setBookEntry(Map<String, Object> map, @PathVariable String id, @PathVariable String bookcode) {
        BookDetails bookEntry = resourceManagerService.getBookEntry(id, bookcode);
        map.put("stitle", "修改图书词条：" + bookEntry.getTitle());
        map.put("title", "图书词条");
        map.put("href", "/admin/bookEntry");
        map.put("bookDetails", bookEntry);
        return "admin/resources/updateBookEntry";
    }

    /**
     * 提交修改图书词条
     * @param bookDetails
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/set/bookEntry/submit", method = RequestMethod.POST)
    public String setBookEntrySubmit(BookDetails bookDetails, RedirectAttributes redirectAttributes) {
        Boolean aBoolean = resourceManagerService.setBookEntry(bookDetails);
        redirectAttributes.addFlashAttribute("model", aBoolean);
        if (aBoolean) {
            redirectAttributes.addFlashAttribute("manager", "修改成功");
        } else {
            redirectAttributes.addFlashAttribute("manager", "修改失败重新修改");
        }
        return "redirect:/admin/bookEntry";
    }

    /**
     * 删除图书词条
     * @param ids
     * @param bookCodes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del/bookEntry/{ids}/{bookCodes}", method = RequestMethod.GET)
    public Boolean delBookEntry(@PathVariable String[] ids, @PathVariable String[] bookCodes) {
        Boolean aBoolean = resourceManagerService.delBookEntry(ids, bookCodes);
        return aBoolean;
    }

    /**
     * 会议词条
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/meetingEntry")
    public String meetingEntry(Map<String, Object> map) {
        map.put("title", "会议词条");
        return "admin/resources/meetingEntry";
    }

    /**
     * 查询全部会议词条
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/meetingEntry")
    public Map<String, Object> getMeetingEntry(@RequestParam Map<String, Object> map) {
        Map<String, Object> allTopicEntry = resourceManagerService.getAllMeetingBookEntry(map);
        return allTopicEntry;
    }

    /**
     * 跳转到修改会议词条
     * @param map
     * @param id
     * @param bookcode
     * @return
     */
    @RequestMapping(value = "/set/meetingEntry/{id}/{bookcode}")
    public String setMeetingEntry(Map<String, Object> map, @PathVariable String id, @PathVariable String bookcode) {
        BookDetails bookEntry = resourceManagerService.getMeetingEntry(id, bookcode);
        map.put("stitle", "会议词条修改：" + bookEntry.getTitle());
        map.put("title", "会议词条");
        map.put("href", "/admin/meetingEntry");
        map.put("bookDetails", bookEntry);
        return "admin/resources/updateMeetingEntry";
    }

    /**
     * 提交修改会议词条
     * @param bookDetails
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/set/meetingEntry/submit", method = RequestMethod.POST)
    public String setMeetingEntrySubmit(BookDetails bookDetails, RedirectAttributes redirectAttributes) {
        Boolean aBoolean = resourceManagerService.setMeetingEntry(bookDetails);
        redirectAttributes.addFlashAttribute("model", aBoolean);
        if (aBoolean) {
            redirectAttributes.addFlashAttribute("manager", "修改成功");
        } else {
            redirectAttributes.addFlashAttribute("manager", "修改失败重新修改");
        }
        return "redirect:/admin/meetingEntry";
    }

    /**
     * 删除会议词条
     * @param ids
     * @param bookCodes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del/meetingEntry/{ids}/{bookCodes}", method = RequestMethod.GET)
    public Boolean delMeetingEntry(@PathVariable String[] ids, @PathVariable String[] bookCodes) {
        Boolean aBoolean = resourceManagerService.delMeetingEntry(ids, bookCodes);
        return aBoolean;
    }

    /**
     * 机构词条
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/mechanismEntry")
    public String mechanismEntry(Map<String, Object> map) {
        map.put("title", "机构词条");
        map.put("state","1");
        return "admin/resources/mechanismEntry";
    }

    /**
     * 查询全部机构词条
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/mechanismEntry")
    public Map<String, Object> getMechanismEntry(@RequestParam Map<String, Object> map) {
        Map<String, Object> allTopicEntry = resourceManagerService.getAllMechanismEntry(map);
        return allTopicEntry;
    }

    /**
     * 跳转到修改机构词条
     * @param map
     * @param id
     * @param bookcode
     * @return
     */
    @RequestMapping(value = "/set/mechanismEntry/{id}/{bookcode}")
    public String setMechanismEntry(Map<String, Object> map, @PathVariable String id, @PathVariable String bookcode) {
        BookDetails bookEntry = resourceManagerService.getMechanismEntry(id, bookcode);
        map.put("stitle", "机构词条修改：" + bookEntry.getTitle());
        map.put("title", "机构词条");
        map.put("href", "/admin/mechanismEntry");
        map.put("bookDetails", bookEntry);
        return "admin/resources/updateMechanismEntry";
    }

    /**
     * 提交修改机构词条
     * @param bookDetails
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/set/mechanismEntry/submit", method = RequestMethod.POST)
    public String setMechanismEntrySubmit(BookDetails bookDetails, RedirectAttributes redirectAttributes) {
        Boolean aBoolean = resourceManagerService.setMechanismEntry(bookDetails);
        redirectAttributes.addFlashAttribute("model", aBoolean);
        if (aBoolean) {
            redirectAttributes.addFlashAttribute("manager", "修改成功");
        } else {
            redirectAttributes.addFlashAttribute("manager", "修改失败重新修改");
        }
        return "redirect:/admin/mechanismEntry";
    }

    /**
     * 删除机构词条
     * @param ids
     * @param bookCodes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del/mechanismEntry/{ids}/{bookCodes}", method = RequestMethod.GET)
    public Boolean delMechanismEntry(@PathVariable String[] ids, @PathVariable String[] bookCodes) {
        Boolean aBoolean = resourceManagerService.delMechanismEntry(ids, bookCodes);
        return aBoolean;
    }

    /**
     * 大事记词条
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/memorabiliayEntry")
    public String memorabiliayEntry(Map<String, Object> map) {
        map.put("title", "大事记词条");
        Boolean model = (Boolean) map.get("model");
        map.put("model", model);
        return "admin/resources/memorabiliayEntry";
    }

    /**
     * 查询全部大事记词条
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllMemorabiliayEntry")
    public Map<String, Object> getAllMemorabiliayEntry(@RequestParam Map<String, Object> map) {
        Map<String, Object> allBooks = resourceManagerService.getAllMemorabiliayEntry(map);
        return allBooks;
    }

    /**
     * 跳转到修改大事记词条
     * @param map
     * @param id
     * @param bookcode
     * @return
     */
    @RequestMapping(value = "/setMemorabiliayEntry/{id}/{bookcode}", method = RequestMethod.GET)
    public String setMemorabiliayEntry(Map<String, Object> map, @PathVariable String id, @PathVariable String bookcode) {
        BookDetails memorabiliayEntry = resourceManagerService.getMemorabiliayEntry(id, bookcode);
        map.put("stitle", "修改大事记词条：" + memorabiliayEntry.getTitle());
        map.put("title", "大事记词条");
        map.put("href", "/admin/memorabiliayEntry");
        map.put("bookDetails", memorabiliayEntry);
        return "admin/resources/updateMemorabiliayEntrty";
    }

    /**
     * 提交修改大事记词条
     * @param bookDetails
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/setMemorabiliayEntrySubmit", method = RequestMethod.POST)
    public String setMemorabiliayEntrySubmit(BookDetails bookDetails, RedirectAttributes redirectAttributes) {
        Boolean aBoolean = resourceManagerService.setMemorabiliayEntry(bookDetails);
        redirectAttributes.addFlashAttribute("model", aBoolean);
        if (aBoolean) {
            redirectAttributes.addFlashAttribute("manager", "修改成功");
        } else {
            redirectAttributes.addFlashAttribute("manager", "修改失败重新修改");
        }
        return "admin/resources/memorabiliayEntry";
    }

    /**
     * 删除大事记词条
     * @param ids
     * @param bookCodes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteMemorabiliayEntry/{ids}/{bookCodes}", method = RequestMethod.GET)
    public Boolean deleteMemorabiliayEntry(@PathVariable String[] ids, @PathVariable String[] bookCodes) {
        Boolean aBoolean = resourceManagerService.delMemorabiliayEntry(ids,bookCodes);
        return aBoolean;
    }

}
