package com.ruoyi.media.service;

import java.util.List;
import com.ruoyi.media.domain.MediaFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 媒体文件Service接口
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
public interface IMediaFileService 
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
     * 批量删除媒体文件
     * 
     * @param fileIds 需要删除的媒体文件主键集合
     * @return 结果
     */
    public int deleteMediaFileByFileIds(Long[] fileIds);

    /**
     * 删除媒体文件信息
     * 
     * @param fileId 媒体文件主键
     * @return 结果
     */
    public int deleteMediaFileByFileId(Long fileId);

    /**
     * 上传媒体文件
     * 
     * @param file 上传的文件
     * @param title 媒体标题
     * @param description 媒体描述
     * @param categoryId 分类ID
     * @param tags 标签
     * @return 结果
     */
    public MediaFile uploadMediaFile(MultipartFile file, String title, String description, Long categoryId, String tags);

    /**
     * 播放媒体文件（增加播放次数）
     * 
     * @param fileId 文件ID
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 结果
     */
    public int playMediaFile(Long fileId, Long userId, String deviceId);

    /**
     * 点赞媒体文件
     * 
     * @param fileId 文件ID
     * @return 结果
     */
    public int likeMediaFile(Long fileId);

    /**
     * 查询公开的媒体文件列表（用于iOS应用）
     * 
     * @param mediaFile 查询条件
     * @return 媒体文件集合
     */
    public List<MediaFile> selectPublicMediaFileList(MediaFile mediaFile);

    /**
     * 根据分类查询媒体文件
     * 
     * @param categoryId 分类ID
     * @return 媒体文件集合
     */
    public List<MediaFile> selectMediaFilesByCategoryId(Long categoryId);
    
    /**
     * 获取播放统计数据
     * 
     * @return 统计数据
     */
    public java.util.Map<String, Object> getPlayStatistics();
    
    /**
     * 获取播放排行榜
     * 
     * @param limit 限制数量
     * @return 媒体文件集合
     */
    public List<MediaFile> getPlayRanking(int limit);
    
    /**
     * 获取点赞排行榜
     * 
     * @param limit 限制数量
     * @return 媒体文件集合
     */
    public List<MediaFile> getLikeRanking(int limit);
    
    /**
     * 获取文件类型分布
     * 
     * @return 类型分布统计
     */
    public java.util.Map<String, Long> getFileTypeDistribution();
} 