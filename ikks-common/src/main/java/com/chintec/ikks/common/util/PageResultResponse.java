package com.chintec.ikks.common.util;

import lombok.Data;

import java.util.List;

/**
 * 分页汇总
 *
 * @author rubin·lv
 * @version 1.0
 * @date 2020/9/1 9:46
 */
@Data
public class PageResultResponse<T> {

    private static final long serialVersionUID = -3962892031742807934L;

    private Long totalRecords;
    private int currentPage;
    private int pageSize;
    private Long totalPages;

    private List<T> results;

    public PageResultResponse() {
    }

    public PageResultResponse(Long totalRecords, int currentPage, int pageSize) {
        this.totalRecords = totalRecords;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    /**
     * @return the results
     */
    public List<T> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<T> results) {
        this.results = results;
    }

}
