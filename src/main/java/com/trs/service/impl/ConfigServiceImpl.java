package com.trs.service.impl;

import com.trs.mapper.ConfigMapper;
import com.trs.model.Config;
import com.trs.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by pz on 2017/4/20.
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Resource
    private ConfigMapper configMapper;

    @Override
    public Config getValue(String name) {
        return configMapper.selectByPrimaryKey(name);
    }

    @Override
    public int save(String name, String value) {
        Config config = this.getValue(name);
        if (null == config) {
            config = new Config();
            config.setName(name);
            config.setValue(value);
            return configMapper.insert(config);
        } else {
            config.setValue(value);
            return configMapper.updateByPrimaryKeySelective(config);
        }
    }

    @Override
    public int updateConfig(Config config) {
        return configMapper.updateByPrimaryKeyWithBLOBs(config);
    }

}
