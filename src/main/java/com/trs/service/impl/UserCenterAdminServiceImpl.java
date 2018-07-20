package com.trs.service.impl;

import com.trs.mapper.CollectMapper;
import com.trs.mapper.SpliceMapper;
import com.trs.mapper.UserCenterAdminMapper;
import com.trs.model.Collect;
import com.trs.model.Concern;
import com.trs.model.Splice;
import com.trs.service.UserCenterAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by epro on 2017/8/24.
 */

@Service
public class UserCenterAdminServiceImpl implements UserCenterAdminService {
    @Resource
    private UserCenterAdminMapper userCenterAdminMapper;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private SpliceMapper spliceMapper;

    /**
     * 查询   我得关注
     * @param username
     * @return
     */
    public List<Concern> selectConcern(String  username){

        List<Concern> list=userCenterAdminMapper.selectConcern(username);

        return list;
    }
    /**
     * 查询   我一周得关注
     * @param username
     * @return
     */
    @Override
    public List<Concern> selectConcernWeek(String username) {
        List<Concern> concernList = userCenterAdminMapper.selectConcernWeek(username);
        return concernList;
    }

    /**
     * 删除关注词
     * @param id
     * @return
     */
    public Map deleteConcern(int id){
        Map resultMap = new HashMap();
        int  concern=userCenterAdminMapper.deleteConcern(id);
//        System.out.println("删除之后是什么=="+concern);
        if(concern==1){
            resultMap.put("code", "0");
        }else{
            resultMap.put("code", "1");
        }
        return resultMap;
    }

    /**
     * 查询   我得收藏
     * @param map
     * @return
     */
    public List<Collect> collectShow(Map map){

        List<Collect> list=collectMapper.collectShow(map);

        return list;
    }

    /**
     * 查询   我得一周收藏
     * @param username
     * @return
     */
    @Override
    public List<Collect> collectShowWeek(String username) {
        List<Collect> list=collectMapper.collectShowWeek(username);
        return list;
    }

    @Override
    public List<Splice> collectSpliceWeek(String username) {
        return collectMapper.collectSpliceWeek(username);
    }


    /**
     * 我的收藏文件夹查找
     * @return
     */
    public List<Collect> collectFolder(String username ){

        List<Collect> list=collectMapper.collectFolder(username);

        return list;
    }

    /**
     * 删除我的收藏
     * @param id
     * @return
     */
    public Map collectDelete(int id){
        Map resultMap = new HashMap();
        int  concern=collectMapper.collectDelete(id);
        if(concern==1){
            resultMap.put("code", "0");
        }else{
            resultMap.put("code", "1");
        }

        return resultMap;
    }


    /**
     * 删除我的收藏文件夹
     * @param map
     * @return
     */
    public Map collectFolderDelete(Map map){
        Map resultMap = new HashMap();
        int  concern=collectMapper.collectFolderDelete(map);
        if(concern>0){
            resultMap.put("code", "0");
        }else{
            resultMap.put("code", "1");
        }
        return resultMap;
    }


    /**
     * 文件夹重命名
     * @param map
     * @return
     */
    public Map collectFolderReName(Map map){
        Map resultMap = new HashMap();
        // 查找文件夹名字是否存在
        String folderName=(String )map.get("folderName");
        String username=(String)map.get("username");
        Map map1=new HashMap();
        map1.put("username",username);
        map1.put("folderName",folderName);
        List<Collect> list=collectMapper.selectFolderName(map1);
        if (list.size()!=0) {
            //添加失败
            resultMap.put("code", "2");
            return resultMap;
        }
        int  concern=collectMapper.collectFolderReName(map);
        if(concern>0){
            resultMap.put("code", "0");
        }else{
            resultMap.put("code", "1");
        }
        return resultMap;
    }

    /**
     * 添加收藏夹
     * @param collect
     * @return
     */
    @Override
    public Map insertFolder(Collect collect) {
        Map resultMap = new HashMap();
        // 查找文件夹名字是否存在
        String folderName=collect.getFoldername();
        String username=collect.getUsername();
        Map map=new HashMap();
        map.put("username",username);
        map.put("folderName",folderName);
        //查询文件夹个数
        List<Collect> listCollect=collectMapper.collectFolder(username);
        if(listCollect.size()>10){
            resultMap.put("state", "3");
            resultMap.put("msg", "已超过最大收藏夹数量！！");

            return resultMap;
        }
        List<Collect> list=collectMapper.selectFolderName(map);
        if (list.size()!=0) {
            resultMap.put("state", "2");
            resultMap.put("msg", "该名称已经被使用，请重新填写。");

            return resultMap;
        }

        // 添加 文件夹
        int result=collectMapper.insertFolder(collect);
        if(result>0){
            resultMap.put("state", "0");
            resultMap.put("msg", "添加成功");
        }else {
            resultMap.put("state", "1");
            resultMap.put("msg", "添加失败");
        }
        return resultMap;
    }

    /**
     * 添加收藏
     * @param collect
     * @return
     */
    @Override
    public Map addCollect(Collect collect) {
        Map addMap = new HashMap();
        // 添加收藏
        int addCollect=collectMapper.addCollect(collect);
        if(addCollect==1){
            addMap.put("state", "0");
            addMap.put("msg", "添加成功");
        }else {
            addMap.put("state", "1");
            addMap.put("msg", "添加失败");
        }
        return addMap;
    }

    /**
     * 根据用户名，tid查找收藏
     * @param map
     * @return
     */
    public Map selectByUserNameAndTID(Map map){
        Map mapResult = new HashMap();
        // 添加收藏
        List<Collect> selectCollect=collectMapper.selectByUserNameAndTID(map);
        if(selectCollect.size()!=0){
            // 存在该收藏
            mapResult.put("state", "2");
        }else {
            mapResult.put("state", "1");
        }
        return mapResult;
    }


    /**
     * 获取收藏总数
     * @param map
     * @return
     */
    @Override
    public Integer collectShowCount(Map map) {
        Integer booksNum = collectMapper.collectShowCount(map);
        int currPage=(int)map.get("currPage");//一次显示几条
        Integer i = booksNum / currPage;
        Double y = (double) booksNum / currPage;
        if (y>i){
            i++;
        }
        return i;
    }

    /**
     * 根据用户名，tid判断是否已经拼接
     * @param map
     * @return
     */
    public Map selectSpliceByUserNameAndTID(Map map){
        Map mapResult = new HashMap();
        // 添加收藏
        List<Splice> selectCollect=spliceMapper.selectSpliceByUserNameAndTID(map);
        if(selectCollect.size()!=0){
            // 存在该收藏
            mapResult.put("state", "2");
        }else {
            mapResult.put("state", "1");
        }
        return mapResult;
    }

    /**
     * 更新拼接
     * @param splice
     * @return
     */
    public Map upSplice(Splice splice){
        Map resultMap = new HashMap();
        int  splices=spliceMapper.upSplice(splice);
        if(splices>0){
            resultMap.put("code", "0");
        }else{
            resultMap.put("code", "1");
        }
        return resultMap;
    }
    /**
     * 添加拼接
     * @param splice
     * @return
     */
    @Override
    public Map mySpliceAdd(Splice splice) {
        Map addMap = new HashMap();
        // 添加收藏
        int addSplice=spliceMapper.mySpliceAdd(splice);
        if(addSplice==1){
            //成功
            addMap.put("code", "0");
        }else {
            addMap.put("code", "1");
        }
        return addMap;
    }

    /**
     * 我的拼接查找
     * @return
     */
    public List<Splice> spliceShow(Map map){

        List<Splice> list=spliceMapper.spliceShow(map);

        return list;
    }

    /**
     * 删除我的收藏文件夹
     * @param id
     * @return
     */
    public Map deleteSplice(int id){
        Map resultMap = new HashMap();
        int  splice=spliceMapper.deleteSplice(id);
        if(splice>0){
            resultMap.put("code", "0");
        }else{
            resultMap.put("code", "1");
        }
        return resultMap;
    }

    /**
     * 我得拼接总数
     * @param map
     * @return
     */
    @Override
    public Integer allSpliceShowCount(Map map) {
        Integer booksNum = spliceMapper.allSpliceShowCount(map);
        int currPage=(int)map.get("currPage");//一次显示几条
        Integer i = booksNum / currPage;
        Double y = (double) booksNum / currPage;
        if (y>i){
            i++;
        }
        return i;
    }


    /**
     * 根据用户名，tid判断是否已经拼关注
     * @param map
     * @return
     */
    public Map selectConcernByUserNameAndConcern(Map map){
        Map mapResult = new HashMap();
        // 添加收藏
        List<Concern> selectCollect=userCenterAdminMapper.selectConcernByUserNameAndConcern(map);
        if(selectCollect.size()!=0){
            // 存在该收藏
            mapResult.put("state", "2");
        }else {
            mapResult.put("state", "1");
        }
        return mapResult;
    }

    /**
     * 更新关注
     * @param concern
     * @return
     */
    public Map upConcern(Concern concern){
        Map resultMap = new HashMap();
        int  concerns=userCenterAdminMapper.upConcern(concern);
        if(concerns>0){
            resultMap.put("state", "0");
        }else{
            resultMap.put("state", "1");
        }
        return resultMap;
    }
    /**
     * 添加关注
     * @param concern
     * @return
     */
    @Override
    public Map insertConcern(Concern concern) {
        Map addMap = new HashMap();
        // 添加收藏
        int addConcern=userCenterAdminMapper.insertConcern(concern);
        if(addConcern==1){
            //成功
            addMap.put("state", "0");
        }else {
            addMap.put("state", "1");
        }
        return addMap;
    }

    /**
     * 添加关注
     * @return
     */
    @Override
    public int allConcern(Map map) {
//        Map addMap = new HashMap();
//        // 添加收藏
//        int addConcern=userCenterAdminMapper.allConcern(map);
//        if(addConcern>30){
//            //成功
//            addMap.put("state", "30");
//        }else {
//            addMap.put("state", "1");
//        }
        return userCenterAdminMapper.allConcern(map);
    }
}
