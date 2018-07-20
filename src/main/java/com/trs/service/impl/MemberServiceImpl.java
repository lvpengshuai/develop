package com.trs.service.impl;

import com.trs.core.annotations.Log;
import com.trs.core.util.Config;
import com.trs.core.util.StringUtil;
import com.trs.core.util.Util;
import com.trs.mapper.MemberMapper;
import com.trs.mapper.MemberMapperOnline;
import com.trs.mapper.OrganizeMapper;
import com.trs.mapper.RoleMapper;
import com.trs.model.Member;
import com.trs.model.Role;
import com.trs.service.MemberService;
import org.nlpcn.commons.lang.util.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by lcy on 2017/3/31.
 */
@Service
public class MemberServiceImpl implements MemberService {

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private MemberMapperOnline memberMapperOnline;

    @Resource
    private OrganizeMapper organizeMapper;

    @Resource
    private RoleMapper roleMapper;

    public final String idsUrl = Config.getKey("ids.domain.name");

    public MemberServiceImpl() {
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_SELECT, targetType = com.trs.model.Log.LOG_TARGETTYPE_MEMBERMANAGER, description = "查看会员用户")
    @Override
    public Map findAllMember(Map map) {
        Map resultMap = new HashMap();

        List<Member> members = memberMapper.listAllMember(map);
        int total = memberMapper.total(map);

        resultMap.put("total", total);
        resultMap.put("rows", members);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_ENABLE, targetType = com.trs.model.Log.LOG_TARGETTYPE_MEMBERMANAGER, description = "启用或禁用会员用户")
    @Override
    public Map changeStatus(Map map) {
        Map resultMap = new HashMap();
        List list = new ArrayList();

        List ids = (List) map.get("ids");
        String status = (String) map.get("status");

        for (int i = 0; i < ids.size(); i++) {
            int id = Integer.parseInt(String.valueOf(ids.get(i)));
            // Member member = memberMapper.selectByPrimaryKey(id);
            Map paramMap = new HashMap();
            paramMap.put("id", id);
            paramMap.put("status", status);
            paramMap.put("gmtModified", new Date());
            list.add(id);
            memberMapper.changeStatus(paramMap);
        }
        resultMap.put("status", status);
        resultMap.put("total", ids.size());
        resultMap.put("chgtotal", list.size());
        return resultMap;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_DELETE, targetType = com.trs.model.Log.LOG_TARGETTYPE_MEMBERMANAGER, description = "删除会员用户")
    @Override
    public Map deleteMemberByid(List<Integer> ids) {

        Map resultMap = new HashMap();

        List list = new ArrayList();

        int id = 0;

        for (int i = 0; i < ids.size(); i++) {
            id = ids.get(i);
            Member member = memberMapper.selectByPrimaryKey(id);

            if (0 == member.getStatus()) {
                resultMap.put("used", 1);
                continue;
            }
            memberMapper.deleteByPrimaryKey(id);
            list.add(id);
        }
        resultMap.put("total", ids.size());
        resultMap.put("deltotal", list.size());
        resultMap.put("code", "0");
        return resultMap;
    }

    @Override
    public Map resetPwd(Map map) {
        Map resultMap = new HashMap();

        String id = (String) map.get("id");
        //String oldpassword = (String) map.get("oldpassword");
        String newpassword = (String) map.get("newpassword");

        Member member = memberMapper.selectByPrimaryKey(Integer.parseInt(id));

        if (null == member) {
            resultMap.put("code", 1);
            resultMap.put("msg", "您要修改的用户不存在");
            return resultMap;
        }
        member.setGmtModified(new Date());
        member.setPwd(Util.toMD5(newpassword));

        memberMapper.updateByPrimaryKey(member);
        resultMap.put("code", 0);
        return resultMap;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_UPDATA, targetType = com.trs.model.Log.LOG_TARGETTYPE_MEMBERMANAGER, description = "更改会员用户权限")
    @Override
    public Map<String, String> changRoleId(Map map) {
        Map<String, String> resultMap = new HashMap<>();
        List<Integer> list = (List) map.get("ids");
        String roleId = (String) map.get("roleId");
        for (int i = 0; i < list.size(); i++) {
            Map paraMap = new HashMap();
            paraMap.put("id", list.get(i));
            paraMap.put("roleId", roleId);

            // 获取角色ID
            Member member = memberMapper.selectByPrimaryKey(list.get(i));
            Integer roleId1 = member.getRoleId();
            // 通过角色ID查询会员表
            List<Member> memberByRoleId = memberMapper.findMemberByRoleId(member);

            // 检查角色是否被使用
            if (memberByRoleId.size() == 0) {
                Role role = roleMapper.selectByPrimaryKey(roleId1);
                if (!StringUtil.isEmpty(role)) {
                    role.setUsed(1);
                    roleMapper.updateByPrimaryKey(role);
                }
            }
            Role role = roleMapper.selectByPrimaryKey(Integer.parseInt(roleId));
            if (!StringUtil.isEmpty(role)) {
                role.setUsed(0);
                roleMapper.updateByPrimaryKey(role);
            }
            memberMapper.changRoleId(paraMap);
        }
        resultMap.put("code", "1");
        return resultMap;
    }

    @Override
    public List<Member> findMemberByRoleId(Member member) {
        List<Member> memberByRoleId = memberMapper.findMemberByRoleId(member);
        return memberByRoleId;
    }

    @Override
    public Member findMemberByUserName(String userName) {
        return memberMapper.findMemberByUserName(userName);
    }

    @Override
    public Map add(Member member) {
        Map resultMap = new HashMap();
        Member userByName = memberMapper.findMemberByUserName(member.getUsername());
        if (userByName != null) {
            resultMap.put("state", "1");
            resultMap.put("msg", "该账户已经被使用，请重新填写。");

            return resultMap;
        }
        String password = Util.toMD5(member.getPwd());
        member.setPwd(password);
        Date date = new Date();
        member.setGmtCreate(date);
        member.setGmtModified(date);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        member.setUuCode(uuid);
        Integer roleId = member.getRoleId();
        if (roleId != 0) {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            role.setUsed(0);
            roleMapper.updateByPrimaryKey(role);
        }
        memberMapper.insert(member);
        resultMap.put("state", "0");
        resultMap.put("msg", "添加成功");

        return resultMap;
    }

    @Override
    public void synAdd(Member member) {
        memberMapper.insert(member);
    }

    @Override
    public Member findMemberById(int id) {

        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> idsRegister(Member member) {
       /* String serviceUrl = idsUrl +
                "/service?idsServiceType=remoteapi&method=userRegister";
        Map<String, Object> result = new HashMap<>();
        String data = "userName="+member.getUsername()+"&password="+member.getPwd()+"&email="+member.getEmail()+
                "&mobile="+member.getTelephone()+"&corpName="+member.getOrganization()+"&education="+member.getEducation()+
                "&IDSEXT_MAJOR="+member.getMajor()+"&IDSEXT_ADDRESS="+member.getAddress()+"&createdDate="+new Date();
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(data.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData = digest+"&"+dataAfterDESEncode;

            // 调用IDS注册接口，保存注册参数
            PostMethod methodPost = new PostMethod(serviceUrl);
            methodPost.addParameter("appName","chemical-knowledge-platform-lcy");
            methodPost.addParameter("type", "xml");
            methodPost.addParameter("data", finalData);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            Element code = elements.select("code").get(0);
            Element desc = elements.select("desc").get(0);
            result.put("status", code.text());
            result.put("msg", desc.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map<String, Object> idsUpdateInfo(Member member) {
       /* String updateUrl = idsUrl +
                "/service?idsServiceType=remoteapi&method=userManageService";
        Map<String, Object> result = new HashMap<>();
        String data = "userName="+member.getUsername()+"&email="+member.getEmail()+
                "&mobile="+member.getTelephone()+"&corpName="+member.getOrganization()+"&education="+member.getEducation()+
                "&IDSEXT_MAJOR="+member.getMajor()+"&IDSEXT_ADDRESS="+member.getAddress()+"&createdDate="+member.getGmtCreate()+"&lastEditTime="+new Date();
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(data.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData = digest+"&"+dataAfterDESEncode;

            // 调用IDS注册接口，保存注册参数
            PostMethod methodPost = new PostMethod(updateUrl);
            methodPost.addParameter("appName","chemical-knowledge-platform-lcy");
            methodPost.addParameter("manageServiceTag","managerUpdateUser");
            methodPost.addParameter("data", finalData);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            Element code = elements.select("code").get(0);
            Element desc = elements.select("desc").get(0);
            result.put("status", code.text());
            result.put("msg", desc.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map<String, Object> idsUpdatePwd(String userName, String oldPwd, String newPwd, String pwdAgain) {
        /*String rePwdUrl = idsUrl +
                "/service?idsServiceType=remoteapi&method=pwdReset";
        Map<String, Object> result = new HashMap<>();
        String data = "attrName=userName"+"&attrValue="+userName+
                "&oldPassword="+oldPwd+"&newPassword="+newPwd+"&ensurePassword="+pwdAgain+"&lastEditTime="+new Date();
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(data.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData = digest+"&"+dataAfterDESEncode;

            // 调用IDS注册接口，保存注册参数
            PostMethod methodPost = new PostMethod(rePwdUrl);
            methodPost.addParameter("appName","chemical-knowledge-platform-lcy");
            methodPost.addParameter("data", finalData);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            Element code = elements.select("code").get(0);
            Element desc = elements.select("desc").get(0);
            result.put("status", code.text());
            result.put("msg", desc.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map<String, Object> idsFindPwd(String userName, String password, String password_again) {
        /*String rePwdUrl = idsUrl +
                "/service?idsServiceType=remoteapi&method=resetPwdv5";
        Map<String, Object> result = new HashMap<>();
        String data = "attrName=mobile"+"&attrValue=13021019541"+
                "&newPassword="+password+"&ensurePassword="+password_again+"&activationCode=b396ef0ad2516d05406af140ee572c36";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(data.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData = digest+"&"+dataAfterDESEncode;

            // 保存参数
            PostMethod methodPost = new PostMethod(rePwdUrl);
            methodPost.addParameter("appName","chemical-knowledge-platform-lcy");
            methodPost.addParameter("data", finalData);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            //String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            Element code = elements.select("code").get(0);
            Element desc = elements.select("desc").get(0);
            result.put("status", code.text());
            result.put("msg", desc.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map<String, Object> idsDelete(String userName) {
       /* String delUrl = idsUrl +
                "/service?idsServiceType=remoteapi&method=userManageService";
        Map<String, Object> result = new HashMap<>();
        String data = "userName="+userName;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(data.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData = digest+"&"+dataAfterDESEncode;

            // 保存参数
            PostMethod methodPost = new PostMethod(delUrl);
            methodPost.addParameter("appName","chemical-knowledge-platform-lcy");
            methodPost.addParameter("manageServiceTag","managerRemoveUser");
            methodPost.addParameter("data", finalData);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            //String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            Element code = elements.select("code").get(0);
            Element desc = elements.select("desc").get(0);
            result.put("status", code.text());
            result.put("msg", desc.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map<String, Object> idsCheckAccountMapping(String uid, String authSite, String accessToken) {
        /*String checkUrl = idsUrl +
                "/service?idsServiceType=remoteapi&method=checkAccountMapping";
        Map<String, Object> result = new HashMap<>();
        String dataPram = "uid="+uid+"&authSite="+authSite+"&accessToken="+accessToken;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(dataPram.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(dataPram.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData2 = digest+"&"+dataAfterDESEncode;

            // 保存参数
            PostMethod methodPost = new PostMethod(checkUrl);
            methodPost.addParameter("appName","chemical-knowledge-platform-zly");
            methodPost.addParameter("data", finalData2);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            //String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            Element code = elements.select("code").get(0);
            Element desc = elements.select("desc").get(0);
            result.put("status", code.text());
            result.put("msg", desc.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map<String, Object> idsBindUser(String uid, String authSite, String accessToken, String userName, String password) {
        /*String checkUrl = idsUrl +
                "/service?idsServiceType=remoteapi&method=addAccountMapping";
        Map<String, Object> result = new HashMap<>();
        String dataPram = "uid="+uid+"&authSite="+authSite+"&accessToken="+accessToken+"&userName="+userName+"&password="+password+
                "&isBindExistUser=true";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(dataPram.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(dataPram.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData2 = digest+"&"+dataAfterDESEncode;

            // 保存参数
            PostMethod methodPost = new PostMethod(checkUrl);
            methodPost.addParameter("appName","chemical-knowledge-platform-zly");
            methodPost.addParameter("data", finalData2);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            //String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            System.out.println(elements);
            Element code = elements.select("code").get(0);
            Element desc = elements.select("desc").get(0);
            result.put("status", code.text());
            result.put("msg", desc.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map<String, Object> idsOauthLogin(String uid, String authSite, String accessToken, String coSessionId) {
       /* String loginUrl = idsUrl +
                "/service?idsServiceType=httpssoservice&serviceName=loginByUID";
        Map<String, Object> result = new HashMap<>();
        String dataPram = "uid="+uid+"&authSite="+authSite+"&accessToken="+accessToken+"&coSessionId="+coSessionId+"&accessSecret=ee9c39d91d42a10ef1984d21e04e0ab6";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(dataPram.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(dataPram.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData2 = digest+"&"+dataAfterDESEncode;

            // 保存参数
            PostMethod methodPost = new PostMethod(loginUrl);
            methodPost.addParameter("coAppName","chemical-knowledge-platform-zly");
            methodPost.addParameter("data", finalData2);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            //String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            Element code = elements.select("responsecode").get(0);
            Element msg = elements.select("sdtoken").get(0);
            System.out.println(msg.text());
            System.out.println(code.text());
            result.put("code", code.text());
            result.put("msg", msg.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map<String, Object> idsLogin(String userName, String sessionID) {
        /*String loginUrl = idsUrl +
                "/service?idsServiceType=httpssoservice&serviceName=loginByUP";
        Map<String, Object> result = new HashMap<>();
        String dataPram = "userName="+userName+"&password=trs.admin"+"&coSessionId="+sessionID;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(dataPram.getBytes("UTF-8"));
            byte[] digestByte = md.digest();
            // 将生成的摘要转成16进制数表示
            String digest = StringHelper.toString(digestByte);
            String base64Encoded = new
                    String(org.apache.commons.codec.binary.Base64.encodeBase64(dataPram.getBytes("UTF-8")));
            // 对data进行加密处理  并将结果转换成16进制表示
            String dataAfterDESEncode =
                    DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"),"123456789");
            String finalData2 = digest+"&"+dataAfterDESEncode;

            // 保存参数
            PostMethod methodPost = new PostMethod(loginUrl);
            methodPost.addParameter("coAppName","chemical-knowledge-platform-zly");
            methodPost.addParameter("version","v5.0");
            methodPost.addParameter("data", finalData2);

            // 发送数据并解析返回数据
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(methodPost);
            String response = new String(methodPost.getResponseBody(), "utf-8");
            // 拆分摘要和结果信息
            String[] digestAndResult = StringHelper.split(response, "&");
            String digestOfServer = digestAndResult[0];
            String responseResult = digestAndResult[1];
            // 解密响应结果
            String afterDESResult = null;
            afterDESResult = DesEncryptUtil.decrypt(responseResult, "123456789");
            String afterBase64Decode =new String(Base64.decodeBase64
                    (afterDESResult.getBytes("UTF-8")),"UTF-8");
            MessageDigest sd = MessageDigest.getInstance("MD5");
            sd.update(afterBase64Decode.getBytes("UTF-8"));
            //String digestOfAgent = StringHelper.toString(sd.digest());

            Document doc= Jsoup.parse(afterBase64Decode);
            Elements elements = doc.body().children();
            System.out.println(elements);
            Element code = elements.select("code").get(0);
            Element desc = elements.select("desc").get(0);
            result.put("status", code.text());
            result.put("msg", desc.text());
        }catch (Exception e){
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;*/
        return null;
    }

    @Override
    public Map updateInfo(Member member) {
        Map resultMap = new HashMap();

//        User userByName = userMapper.findUserByName(user.getUsername());
//        if (userByName != null) {
//            resultMap.put("state", "1");
//            resultMap.put("msg", "该账户已经被使用，请重新填写。");
//
//            return resultMap;
//        }

        Date date = new Date();
        member.setGmtModified(date);

        // 修改用户信息之前查询原数据信息
        Integer id = member.getId();
        if (ONLINESTATUS.equals("ON")){
            Member user1 = memberMapperOnline.selectByPrimaryKey(id);
            //修改信息  密码不可为空
            member.setPwd(user1.getPwd());
        }else{
            Member user1 = memberMapper.selectByPrimaryKey(id);
            //修改信息  密码不可为空
            member.setPwd(user1.getPwd());
        }
//        // 获取原数据的角色ID
//        Integer roleId = user1.getRoleId();
//
//        // 获取新数据角色ID
//        Integer roleId1 = member.getRoleId();
//        Member user2 = new Member();
//        user2.setRoleId(roleId1);
//        user2.setId(id);
//        // 根据ID查询本表数据
//        List<Member> userByRoleId = memberMapper.findMemberByRoleId(user2);
//
//        // 判断是否存在数据，如果存在则不进行更新操作，如果不存在则将角色的使用情况改为未使用
//        if (userByRoleId.size() == 0) {
//            Role role = roleMapper.selectByPrimaryKey(roleId);
//            role.setUsed(1);
//            roleMapper.updateByPrimaryKey(role);
//        }
//        Role role = roleMapper.selectByPrimaryKey(roleId1);
//        if (role != null) {
//            role.setUsed(0);
//            roleMapper.updateByPrimaryKey(role);
//        }
        memberMapper.updateByPrimaryKeySelective(member);
        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");

        return resultMap;
    }

    public Map updatePassword(Member member) {
        Map resultMap = new HashMap();

        Date date = new Date();
        member.setGmtModified(date);

        // 修改用户信息之前查询原数据信息
        Integer id = member.getId();
        memberMapper.updateByPrimaryKeySelective(member);
        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");

        return resultMap;
    }

    @Override
    public boolean findMemberByUserNameAndPwd(Map map, HttpSession session) {
        String userName = (String) map.get("userName");
        String password = (String) map.get("password");

        Member memberByUserName = memberMapper.findMemberByUserName(userName);
        if (null == memberByUserName) {
            return false;
        } else if (!MD5.code(password).equals(memberByUserName.getPwd())) {
            return false;
        }
        if (null != memberByUserName) {
            List list = organizeMapper.selectOrganizeAdminByMember(memberByUserName.getId());
            if (list.size() > 0) {
                Map m = (Map) list.get(0);
                int organize_id = Integer.parseInt(String.valueOf(m.get("organizeId")));
                session.setAttribute("orgFlag", organize_id);
            }
        }
        return true;
    }

    public List<Member> findMemberByEmail(String email) {

        return memberMapper.findMemberByEmail(email);
    }

    @Override
    public Member findMemberById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map updataOrganize(Map map) {
        Map resultMap = new HashMap();
        memberMapper.updataOrganize(map);
        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");
        return resultMap;
    }

    @Override
    public Map updataOrganizeByUserName(Map map) {
        Map resultMap = new HashMap();
        memberMapper.updataOrganizeByUserName(map);
        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");
        return resultMap;
    }


    @Override
    public Map findMemberByOrgId(Map map) {
        Map resultMap = new HashMap();

        List<Member> members = memberMapper.findMemberByOrgId(map);
        int total = memberMapper.total(map);

        resultMap.put("total", total);
        resultMap.put("rows", members);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;
    }
}
