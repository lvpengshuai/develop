package com.trs.service;

import com.trs.model.User;
import com.trs.model.UserCenterTree;

import java.util.List;
import java.util.Map;

/**
 * Created by lihuan on 2017-3-20.
 */
public interface UserCenterService {

    List<UserCenterTree> getTree();
}
