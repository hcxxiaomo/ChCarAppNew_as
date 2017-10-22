package com.xiaomo.util;

public class PageBean {

	private int start;//��ʼҳ
	private int page;//�ڼ�ҳ
	private int pageSize;//ÿҳ��¼��
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	public int getStart() {
		return (page - 1 ) * pageSize;
	}
	/*public void setStart(int start) {
		this.start = start;
	}*/
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
