package com.ruoyi.media.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.media.mapper.MediaCategoryMapper;
import com.ruoyi.media.domain.MediaCategory;
import com.ruoyi.media.service.IMediaCategoryService;
import com.ruoyi.common.utils.DateUtils;

/**
 * 媒体分类Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@Service
public class MediaCategoryServiceImpl implements IMediaCategoryService 
{
    @Autowired
    private MediaCategoryMapper mediaCategoryMapper;

    /**
     * 查询媒体分类
     * 
     * @param categoryId 媒体分类主键
     * @return 媒体分类
     */
    @Override
    public MediaCategory selectMediaCategoryByCategoryId(Long categoryId)
    {
        return mediaCategoryMapper.selectMediaCategoryByCategoryId(categoryId);
    }

    /**
     * 查询媒体分类列表
     * 
     * @param mediaCategory 媒体分类
     * @return 媒体分类
     */
    @Override
    public List<MediaCategory> selectMediaCategoryList(MediaCategory mediaCategory)
    {
        return mediaCategoryMapper.selectMediaCategoryList(mediaCategory);
    }

    /**
     * 新增媒体分类
     * 
     * @param mediaCategory 媒体分类
     * @return 结果
     */
    @Override
    public int insertMediaCategory(MediaCategory mediaCategory)
    {
        mediaCategory.setCreateTime(DateUtils.getNowDate());
        return mediaCategoryMapper.insertMediaCategory(mediaCategory);
    }

    /**
     * 修改媒体分类
     * 
     * @param mediaCategory 媒体分类
     * @return 结果
     */
    @Override
    public int updateMediaCategory(MediaCategory mediaCategory)
    {
        mediaCategory.setUpdateTime(DateUtils.getNowDate());
        return mediaCategoryMapper.updateMediaCategory(mediaCategory);
    }

    /**
     * 批量删除媒体分类
     * 
     * @param categoryIds 需要删除的媒体分类主键
     * @return 结果
     */
    @Override
    public int deleteMediaCategoryByCategoryIds(Long[] categoryIds)
    {
        return mediaCategoryMapper.deleteMediaCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除媒体分类信息
     * 
     * @param categoryId 媒体分类主键
     * @return 结果
     */
    @Override
    public int deleteMediaCategoryByCategoryId(Long categoryId)
    {
        return mediaCategoryMapper.deleteMediaCategoryByCategoryId(categoryId);
    }

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param categories 媒体分类列表
     * @return 下拉树结构列表
     */
    @Override
    public List<MediaCategory> buildCategoryTreeSelect(List<MediaCategory> categories)
    {
        List<MediaCategory> returnList = new ArrayList<MediaCategory>();
        List<Long> tempList = new ArrayList<Long>();
        for (MediaCategory category : categories)
        {
            tempList.add(category.getCategoryId());
        }
        for (MediaCategory category : categories)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(category.getParentId()))
            {
                recursionFn(categories, category);
                returnList.add(category);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = categories;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<MediaCategory> list, MediaCategory t)
    {
        // 得到子节点列表
        List<MediaCategory> childList = getChildList(list, t);
        t.setChildren(childList);
        for (MediaCategory tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<MediaCategory> getChildList(List<MediaCategory> list, MediaCategory t)
    {
        List<MediaCategory> tlist = new ArrayList<MediaCategory>();
        for (MediaCategory n : list)
        {
            if (n.getParentId() != null && n.getParentId().longValue() == t.getCategoryId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<MediaCategory> list, MediaCategory t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}