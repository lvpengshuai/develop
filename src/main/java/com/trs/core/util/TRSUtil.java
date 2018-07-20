package com.trs.core.util;

import com.trs.client.TRSConnection;
import com.trs.client.TRSException;

/**
 * Created by zly on 2017-3-13.
 */
public class TRSUtil {

    /**
     * 获取TRS Server 连接
     * @return
     */
    public TRSConnection getTrsconnect(){
        TRSConnection trsConnection = null;

        String trsHost = Config.getKey("trs.server.host");
        String trsPort = Config.getKey("trs.server.port");
        String trsUsername = Config.getKey("trs.server.username");
        String trsPassword = Config.getKey("trs.server.password");
        String trsSpare = Config.getKey("trs.server.spare");

        try {
            trsConnection = new TRSConnection();
            trsConnection.connect(trsHost, trsPort, trsUsername, trsPassword, trsSpare);
        } catch (TRSException e) {
            e.printStackTrace();
        }

        return trsConnection;
    }
    public TRSConnection getReTrsConnect(TRSConnection connect,String licenceCode){

        String trsHost = Config.getKey("trs.server.host");
        String trsPort = Config.getKey("trs.server.port");
        try {
            connect.reconnect(trsHost,trsPort,licenceCode);
        } catch (TRSException e) {
            e.printStackTrace();
        }

        return connect;
    }
}
