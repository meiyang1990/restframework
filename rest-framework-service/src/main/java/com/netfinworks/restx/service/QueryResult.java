/**
 * 
 */
package com.netfinworks.restx.service;

import java.io.Serializable;
import java.util.List;

import com.netfinworks.restx.persist.jdbc.DOBase;
import com.netfinworks.restx.persist.jdbc.PageInfo;

/**
 * <p>注释</p>
 * @author zhangjiewen
 * @version $Id: ServiceQueryResult.java, v 0.1 2012-9-20 上午10:02:54 zhangjiewen Exp $
 */
public class QueryResult<T extends DOBase> implements Serializable {
    private static final long serialVersionUID = 6061672061442753533L;    
    private PageInfo pageInfo = null;
    private List<T> items = null;
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
    public List<T> getItems() {
        return items;
    }
    public void setItems(List<T> items) {
        this.items = items;
    }    
}
