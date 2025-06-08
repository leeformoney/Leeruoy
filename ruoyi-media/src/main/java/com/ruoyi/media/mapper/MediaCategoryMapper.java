package com.ruoyi.media.mapper;

import java.util.List;
import com.ruoyi.media.domain.MediaCategory;

/**
 * 媒体分类Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
public interface MediaCategoryMapper 
{
    /**
     * 查询媒体分类
     * 
     * @param categoryId 媒体分类主键
     * @return 媒体分类
     */
    public MediaCategory selectMediaCategoryByCategoryId(Long categoryId);

    /**
     * 查询媒体分类列表
     * 
     * @param mediaCategory 媒体分类
     * @return 媒体分类集合
     */
    public List<MediaCategory> selectMediaCategoryList(MediaCategory mediaCategory);

    /**
     * 新增媒体分类
     * 
     * @param mediaCategory 媒体分类
     * @return 结果
     */
    public int insertMediaCategory(MediaCategory mediaCategory);

    /**
     * 修改媒体分类
     * 
     * @param mediaCategory 媒体分类
     * @return 结果
     */
    public int updateMediaCategory(MediaCategory mediaCategory);

    /**
     * 删除媒体分类
     * 
     * @param categoryId 媒体分类主键
     * @return 结果
     */
    public int deleteMediaCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除媒体分类
     * 
     * @param categoryIds 需要删除的媒体分类主键集合
     * @return 结果
     */
    public int deleteMediaCategoryByCategoryIds(Long[] categoryIds);
}