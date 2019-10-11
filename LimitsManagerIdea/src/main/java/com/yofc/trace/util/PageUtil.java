package com.yofc.trace.util;

import java.util.List;

public class PageUtil<T> {

	private Integer index = 1;
	private Integer size = 10;
	private Integer count;
	private Integer total;
	private List<T> list;
	private int previousPage;
	private int nextPage;
	private int firstPage = 1;
	private int lastPage;

	public void otherAttr() {
		// 总页数
		this.total = this.count % this.size == 0 ? this.count / this.size : this.count / this.size + 1;
		// 第一页
		this.firstPage = 1;
		// 最后一页
		this.lastPage = this.total;
		// 前一页
		if (this.index > 1) {
			this.previousPage = this.index - 1;
		} else {
			this.previousPage = this.firstPage;
		}
		// 下一页
		if (this.index < this.lastPage) {
			this.nextPage = this.index + 1;
		} else {
			this.nextPage = this.lastPage;
		}
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
		otherAttr();
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
		
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

}
