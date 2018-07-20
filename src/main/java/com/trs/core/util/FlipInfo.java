package com.trs.core.util;

import java.util.List;

/**
 * Created by 李春雨 on 2016/8/31.
 */
public class FlipInfo {
    /**分页信息保存在request中的参数名称*/
    public static final String FLIP_SOURCE = "FLIP_SOURCE" ;

    public FlipInfo(int recCount, int pageNo, int pageSize) {
        this(recCount, pageNo, pageSize, null);
    }

    /**
     * 构造函数。
     * @param recCount 总的纪录数
     * @param pageNo 当前的页，以1开始，作为第一个页。
     * @param pageSize 每页显示的纪录数
     * @param elements 数据
     **/
    public FlipInfo(int recCount, int pageNo, int pageSize, List elements) {
        this(recCount, pageNo, pageSize, elements, null);
    }

    public FlipInfo(int recCount, int pageNo, int pageSize, List elements,
                    String licenseCode) {
        pageStart = pageNo - 5;
        if (pageStart < 0)
            pageStart = 1;
        this.pageSize = (pageSize <= 0) ? 20 : pageSize;
        this.recCount = recCount;
        if (recCount % pageSize == 0) {
            pageCount = (recCount / pageSize);
        } else {
            pageCount = (recCount / pageSize) + 1;
        }
        this.elements = elements;
        this.pageNo = pageNo > 0 ? pageNo : 1 ;
        this.licenseCode = licenseCode;
    }

    private int pageStart;

    private Object title ; //表示此PageInfo存放的是什么内容。

    private int pageNo;

    private int pageSize;

    private int pageCount;

    private int recCount;

//	private int pageEnd ; //在页面上分页的结束页

    private int pagesShow = 10 ; //在页面上显示的分页数

    private List elements;

    private String licenseCode;

    private int index = -1 ;

    //基准地址，例如：http://www.book.com/listBook.do?uid=1&pageNo=3
    private String flipURL ;

    /**
     * @return Returns the pageStart.
     */
    public int getPageStart()
    {
        int before = pageNo - 1 ;
        int after = pageCount - pageNo ;

        if(before < 5) return 1 ; //如果前面小于5页就全部留下，或者往后翻。
        else{
            if(after > pagesShow - 5){
                return pageNo - 4 ;
            }else{
                if(pageCount > pagesShow){
                    if(after <= 5){
                        return pageCount - 9 ;
                    }
                    return pageCount - 10 ;
                }else{
                    return 1 ;
                }
            }
        }
    }

    public int getPageEnd() {
        if(pageCount < 1) return 1 ;

//        int before = pageNo - 1 ;
        int after = pageCount - pageNo ;

        if(after < 5) return pageCount ;

        else{
            if(getPageStart() > 1){
                return Math.min(getPageStart() + pagesShow - 1, pageCount) ;
            }else{
                return Math.min(pageCount, pagesShow) ;
            }
        }
    }

    /**
     * @param pageStart
     *            The pageStart to set.
     */
    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public boolean isHasNextPage(){
        if(pageNo < 1) pageNo = 1 ;
        return pageCount > pageNo ;
    }

    public boolean isHasPreviousPage(){
        return pageNo > 1 ;
    }

    public int getPreviousPageNum(){
        if(isHasPreviousPage()){
            return pageNo - 1 ;
        }
        return 1 ;
    }

    public int getNextPageNum(){
        if(!isHasNextPage()){
            return pageNo ;
        }
        if(pageNo > 1){
            return (pageNo + 1) ;
        }else{
            return 2 ;
        }
    }

    public int getIndex(){
        return getIndex(true) ;
    }

    public int getIndex(boolean updateIndex){
        if(index < 0){
            index = ((pageNo > 1 ? pageNo : 1) - 1) * pageSize + 1 ;
        }
        if(updateIndex){
            return index++ ;
        }else{
            return index ;
        }
    }

    /**
     * @return Returns the recCount.
     */
    public int getRecCount() {
        return recCount;
    }

    /**
     * @param recCount
     *            The recCount to set.
     */
    public void setRecCount(int recCount) {
        this.recCount = recCount;
    }

    /**
     * @return Returns the pageCount.
     */
    public int getPageCount() {
        return pageCount > 0 ? pageCount : 1 ;
    }

    /**
     * @param pageCount
     *            The pageCount to set.
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return Returns the pageNo.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo
     *            The pageNo to set.
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo > 0 ? pageNo : 1 ;
    }

    /**
     * @return Returns the pageSize.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            The pageSize to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return Returns the elements.
     */
    public List getElements() {
        return elements;
    }

    /**
     * @param elements
     *            The elements to set.
     */
    public void setElements(List elements) {
        this.elements = elements;
    }

    public boolean isEmpty() {
        return ((elements == null) || (elements.isEmpty()));
    }

    public Object getAt(int i) {
        if (elements.isEmpty())
            return null;
        return elements.get(i);
    }

    /**
     * @return Returns the licenseCode.
     */
    public String getLicenseCode() {
        return licenseCode;
    }

    /**
     * @param licenseCode
     *            The licenseCode to set.
     */
    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public int getPagesShow() {
        return pagesShow;
    }

    public void setPagesShow(int pagesShow) {
        this.pagesShow = pagesShow;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public int getSize(){
        return elements.size() ;
    }

    public int getPageStartIndex(){
        return getIndex(false) ;
    }

    public int getPageEndIndex(){
        int startIndex = getIndex(false) ;
        int elementNum = this.getElements().size() ;

        int toreturn ;
        if(startIndex + getPageSize() > elementNum){
            toreturn =  startIndex + elementNum - 1 ;
        }else{
            toreturn =  startIndex + getPageSize() - 1 ;
        }
        return toreturn > 0 ? toreturn : 1 ;
    }

    public String getFlipURL() {
        return flipURL;
    }

    public void setFlipURL(String flipURL) {
        this.flipURL = flipURL;
    }
}
