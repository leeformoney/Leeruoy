package com.ruoyi.media.mapper;

import java.util.List;
import com.ruoyi.media.domain.MediaFile;

/**
 * 媒体文件Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
public interface MediaFileMapper 
{
    /**
     * 查询媒体文件
     * 
     * @param fileId 媒体文件主键
     * @return 媒体文件
     */
    public MediaFile selectMediaFileByFileId(Long fileId);

    /**
     * 查询媒体文件列表
     * 
     * @param mediaFile 媒体文件
     * @return 媒体文件集合
     */
    public List<MediaFile> selectMediaFileList(MediaFile mediaFile);

    /**
     * 新增媒体文件
     * 
     * @param mediaFile 媒体文件
     * @return 结果
     */
    public int insertMediaFile(MediaFile mediaFile);

    /**
     * 修改媒体文件
     * 
     * @param mediaFile 媒体文件
     * @return 结果
     */
    public int updateMediaFile(MediaFile mediaFile);

    /**
     * 删除媒体文件
     * 
     * @param fileId 媒体文件主键
     * @return 结果
     */
    public int deleteMediaFileByFileId(Long fileId);

    /**
     * 批量删除媒体文件
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMediaFileByFileIds(Long[] fileIds);

    /**
     * 增加播放次数
     * 
     * @param fileId 文件ID
     * @return 结果
     */
    public int incrementViewCount(Long fileId);

    /**
     * 增加点赞数
     * 
     * @param fileId 文件ID
     * @return 结果
     */
    public int incrementLikeCount(Long fileId);

    /**
     * 根据分类查询媒体文件
     * 
     * @param categoryId 分类ID
     * @return 媒体文件集合
     */
    public List<MediaFile> selectMediaFilesByCategoryId(Long categoryId);

    /**
     * 查询公开的媒体文件列表（用于iOS应用）
     * 
     * @param mediaFile 查询条件
     * @return 媒体文件集合
     */
    public List<MediaFile> selectPublicMediaFileList(MediaFile mediaFile);
}