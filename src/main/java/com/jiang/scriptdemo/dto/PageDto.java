package com.jiang.scriptdemo.dto;

import java.util.ArrayList;
import java.util.List;

public class PageDto<T> {
    private List<T> data;
    //是否展示前一页
    private boolean showPre;
    //是否展示后一页
    private boolean shownext;
    //是否展示第一页
    private boolean showfirst;
    //是否展示最后一页
    private boolean showlast;

    private int page;
    //当前展示的页码集合
    private List<Integer> pages = new ArrayList<>();
    //所有页数
    private int totalpage;

    public void setPagination(int totalcount, int page, int size) {
        this.page = page;
        if (totalcount % size == 0) {
            totalpage = totalcount / size;
        } else {
            totalpage = totalcount / size + 1;
        }
        //page<1就显示1，page>最大页数就显示最大页数
        if (page<1){
            this.page=1;
        }
        if (page>totalpage){
            this.page=totalpage;
        }
        //将需要展示的页码插入到pages中
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalpage) {
                pages.add(page + i);
            }
        }
        //是否展示上一页
        showPre = page != 1;
        //是否展示后一页
        shownext = page != totalpage;
        //是否展示第一页
        showfirst = !pages.contains(1);
        //是否展示最后一页
        if (pages.contains(totalpage)) {
            showlast = false;
        } else {
            showlast = true;
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isShowPre() {
        return showPre;
    }

    public void setShowPre(boolean showPre) {
        this.showPre = showPre;
    }

    public boolean isShownext() {
        return shownext;
    }

    public void setShownext(boolean shownext) {
        this.shownext = shownext;
    }

    public boolean isShowfirst() {
        return showfirst;
    }

    public void setShowfirst(boolean showfirst) {
        this.showfirst = showfirst;
    }

    public boolean isShowlast() {
        return showlast;
    }

    public void setShowlast(boolean showlast) {
        this.showlast = showlast;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }
}
