package com.trs.service;

import com.trs.model.Config;

/**
 * Created by pz on 2017/4/20.
 */
public interface ConfigService {

    public Config getValue(String name);

    int save(String name, String value);

    public int updateConfig(Config config);

}
