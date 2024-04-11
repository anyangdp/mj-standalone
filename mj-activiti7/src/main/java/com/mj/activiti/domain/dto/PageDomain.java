package com.mj.activiti.domain.dto;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 分页数据
 * 
 * @author ruoyi
 */
@Setter
@Getter
public class PageDomain
{
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtils.upperCase(orderByColumn) + " " + isAsc;
    }
}
