package com.trs.core.util;

import java.util.List;

/**
 * Created by epro on 2017/3/23.
 */
public class SearchResult {
    // TRS数据库关联项目
    public List trsList;//数据列表
    int TRSCount;       //总条数（方便计算分页）
    String LicenceCode; //当前连接的LicenseCode
    List categoryList;
    boolean returnFlag; // 结果状态

    public List getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List categoryList) {
        this.categoryList = categoryList;
    }

    public boolean isReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(boolean returnFlag) {
        this.returnFlag = returnFlag;
    }

    public List getTrsList() {
        return trsList;
    }

    public void setTrsList(List trsList) {
        this.trsList = trsList;
    }

    public int getTRSCount() {
        return TRSCount;
    }

    public void setTRSCount(int TRSCount) {
        this.TRSCount = TRSCount;
    }

    public String getLicenceCode() {
        return LicenceCode;
    }

    public void setLicenceCode(String licenceCode) {
        LicenceCode = licenceCode;
    }
}
