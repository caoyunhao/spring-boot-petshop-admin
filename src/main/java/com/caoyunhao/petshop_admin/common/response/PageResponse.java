package com.caoyunhao.petshop_admin.common.response;

import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.util.ConvertUtil;
import com.caoyunhao.petshop_admin.common.util.RowConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public class PageResponse<T> extends ListResponse<T> {
    private int pageNum = 0;
    private int pages = 0;

    /**
     * 基本构造器
     */
    public PageResponse() throws Exception {
    }

    /**
     * 返回PageResponse
     *
     * @param dataList 源数据
     * @throws Exception
     */
    public PageResponse(Page<T> dataList) throws Exception {
        getPageParamsFromPage(dataList);
    }

    /**
     * 返回PageResponse
     *
     * @param dataList     源数据
     * @param rowConverter 转换器，用来进行转换数据
     * @throws Exception
     */
    public <From> PageResponse(Page<From> dataList, RowConverter<From, T> rowConverter) throws Exception {
        getPageParamsFromPage(dataList);
        data = ConvertUtil.convertList(dataList, rowConverter);
    }

    public static <From, T> PageResponse<T> copyListProperties(Page<From> dataList, final Class<T> clazz) throws Exception {
        PageResponse<T> pageResponse = new PageResponse();
        pageResponse.getPageParamsFromPage(dataList);
        pageResponse.data = ConvertUtil.convertList(dataList, new RowConverter<From, T>() {
            @Override
            public T convertRow(From from) throws Exception {
                T t = (T) (clazz.newInstance());
                BeanUtils.copyProperties(from, t);
                return t;
            }
        });
        return pageResponse;
    }

    private void getPageParamsFromPage(Page dataList) {
        if (null != dataList && 0L != dataList.getTotalElements()) {
            pageNum = dataList.getNumber() + 1;
            pages = dataList.getTotalPages();
            data = dataList.getContent();
        } else {
            setError(ErrorCode.QUERY_DATA_EMPTY);
            pageNum = 0;
            pages = 0;
            data = null;
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
