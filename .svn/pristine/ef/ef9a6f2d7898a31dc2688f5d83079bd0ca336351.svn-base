/**
 * 
 */
package com.netfinworks.restx.persist.jdbc;

/**
 * @author knico
 * @since Sep 26, 2012
 * 
 */
public class QueryConditionBase {
	// 当前页
	private Integer currentPage;
	// 每页数量
	private Integer pageSize;
	// 计算总量
	private boolean isCountTotal;
	// 是否模糊
	private boolean fuzzy;
	// 是否排序
	private boolean order;

	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(Integer currentPage) {
		if (currentPage != null) {
			this.currentPage = currentPage > 1 ? currentPage : 1;
		}
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the isCountTotal
	 */
	public boolean isCountTotal() {
		return isCountTotal;
	}

	/**
	 * @param isCountTotal the isCountTotal to set
	 */
	public void setCountTotal(boolean isCountTotal) {
		this.isCountTotal = isCountTotal;
	}

	/**
	 * @return the fuzzy
	 */
	public boolean isFuzzy() {
		return fuzzy;
	}

	/**
	 * @param fuzzy the fuzzy to set
	 */
	public void setFuzzy(boolean fuzzy) {
		this.fuzzy = fuzzy;
	}

	/**
	 * @return the order
	 */
	public boolean isOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(boolean order) {
		this.order = order;
	}

	public PageInfo generatePageInfo() {
		if (currentPage != null) {
			PageInfo ret = new PageInfo();
			ret.setCurrentPage(currentPage);
			if (pageSize != null) {
				ret.setPageSize(pageSize);
			}
			return ret;
		}
		return PageInfo.EMPTY;
	}

	public Integer getOffset() {
		if (this.currentPage != null) {
			return (this.currentPage - 1) * this.currentPageSize();
		}
		return null;
	}

	public Integer getStartRow() {
		if (this.currentPage != null) {
			return (this.currentPage - 1) * this.currentPageSize() + 1;
		}
		return null;
	}

	public Integer getEndRow() {
		if (this.currentPage != null) {
			return this.currentPage * this.currentPageSize();
		}
		return null;
	}

	private int currentPageSize() {
		return this.pageSize == null ? PageInfo.DEFAULT_PAGE_SIZE : this.pageSize;
	}
}
