package com.trs.core.util;

import java.util.List;

/**
 * Created by 李春雨 on 2016/8/31.
 */
public class PagedList {

    /**分页计算信息*/
    private FlipInfo flipInfo ;

    private String licenseCode ;

    /**
     * 构造分页结果集.
     * @see #PagedList(int, int, int, List, int)
     */
    public PagedList(int currPage, int pageSize, int itemTotal, List items) {
        this(currPage, pageSize, itemTotal, items, 10);
    }

    /**
     * 构造分页结果集.
     * @param currPage
     * @param pageSize
     * @param itemTotal
     * @param items
     * @param step
     */
    public PagedList(int currPage, int pageSize, int itemTotal, List items, int step) {
        this.currPage = (currPage < 0) ? 0 : currPage;
        this.pageSize = (pageSize <= 0) ? 5 : pageSize;
        this.itemTotal = itemTotal;
        this.items = items;

        computePageIndex(step);
    }

    /**
     * 计算页码导航的各个值.
     * @param stepValue 页码导航显示多少页.
     */
    private void computePageIndex(int stepValue) {
        if (itemTotal <= 0) {
            pageTotal = 0;
        } else {
            pageTotal = ( itemTotal / pageSize ) + ((itemTotal % pageSize==0) ? 0 : 1);
        }
        prevPage = (currPage == 0) ? 0 : currPage - 1;
        nextPage = (currPage >= pageTotal - 1) ? pageTotal - 1 : currPage + 1;
        step = stepValue;
        startPage = currPage / step;
        endPage = (startPage + step >= pageTotal) ? pageTotal - 1 : startPage + step;
        prevStartPage = (startPage == 0) ? 0 : startPage - step;
        nextStartPage = startPage + step;
    }

    /**
     * 当前页. 0为第一页.
     */
    private int currPage;

    /**
     * 每页显示的记录数.
     */
    private int pageSize;

    /**
     * 总页数.
     */
    private int pageTotal;

    /**
     * 总记录数.
     */
    private int itemTotal;

    // ls@06-0319

    /**
     * 导航的步进. (即导航条显示多少页)
     */
    private int step;

    /**
     * 导航的起始页.
     */
    private int startPage;

    /**
     * 导航的结束页.
     */
    private int endPage;

    /**
     * 当前页的上一页.
     */
    private int prevPage;

    /**
     * 当前页的下一页.
     */
    private int nextPage;

    /**
     * 上一个导航的起始页.
     */
    private int prevStartPage;

    /**
     * 下一个导航的起始页.
     */
    private int nextStartPage;

    /**
     * 当前页的记录集.
     */
    private List items;

    /**
     * Returns the {@link #currPage}.
     * @return Returns the currPage.
     */
    public int getCurrPage() {
        return currPage;
    }
    /**
     * Returns the {@link #items}.
     * @return Returns the items.
     */
    public List getItems() {
        return items;
    }
    /**
     * Returns the {@link #itemTotal}.
     * @return Returns the itemTotal.
     */
    public int getItemTotal() {
        return itemTotal;
    }
    /**
     * Returns the {@link #pageSize}.
     * @return Returns the pageSize.
     */
    public int getPageSize() {
        return pageSize;
    }
    /**
     * Returns the {@link #pageTotal}.
     * @return Returns the pageTotal.
     */
    public int getPageTotal() {
        return pageTotal;
    }

    /**
     * Returns the {@link #endPage}.
     * @return Returns the endPage.
     */
    public int getEndPage() {
        return endPage;
    }

    /**
     * Returns the {@link #nextStartPage}.
     * @return Returns the nextStartPage.
     */
    public int getNextStartPage() {
        return nextStartPage;
    }

    /**
     * Returns the {@link #prevStartPage}.
     * @return Returns the prevStartPage.
     */
    public int getPrevStartPage() {
        return prevStartPage;
    }

    /**
     * Returns the {@link #startPage}.
     * @return Returns the startPage.
     */
    public int getStartPage() {
        return startPage;
    }

    /**
     * Returns the {@link #step}.
     * @return Returns the step.
     */
    public int getStep() {
        return step;
    }

    /**
     * 获取结果集的分页信息。<br>
     * 每次返回的都是同一个对象。
     */
    public FlipInfo getFlipInfo() {
        if(flipInfo == null){
            flipInfo = new FlipInfo(this.getItemTotal(), this.getCurrPage() + 1, this.getPageSize(), this.getItems()) ;
        }
        return flipInfo;
    }

    public void setFlipInfo(FlipInfo flipInfo) {
        this.flipInfo = flipInfo;
    }

    /**
     * Returns the {@link #nextPage}.
     * @return Returns the nextPage.
     */
    public int getNextPage() {
        return nextPage;
    }

    /**
     * Returns the {@link #prevPage}.
     * @return Returns the prevPage.
     */
    public int getPrevPage() {
        return prevPage;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }
}
