package com.ruoyi.media.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.media.domain.MediaFile;
import com.ruoyi.media.mapper.MediaFileMapper;
import com.ruoyi.media.service.IMediaFileService;

/**
 * 媒体文件Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@Service
public class MediaFileServiceImpl implements IMediaFileService 
{
    @Autowired
    private MediaFileMapper mediaFileMapper;

    /**
     * 查询媒体文件
     * 
     * @param fileId 媒体文件主键
     * @return 媒体文件
     */
    @Override
    public MediaFile selectMediaFileByFileId(Long fileId)
    {
        return mediaFileMapper.selectMediaFileByFileId(fileId);
    }

    /**
     * 查询媒体文件列表
     * 
     * @param mediaFile 媒体文件
     * @return 媒体文件
     */
    @Override
    public List<MediaFile> selectMediaFileList(MediaFile mediaFile)
    {
        return mediaFileMapper.selectMediaFileList(mediaFile);
    }

    /**
     * 新增媒体文件
     * 
     * @param mediaFile 媒体文件
     * @return 结果
     */
    @Override
    public int insertMediaFile(MediaFile mediaFile)
    {
        mediaFile.setCreateTime(DateUtils.getNowDate());
        return mediaFileMapper.insertMediaFile(mediaFile);
    }

    /**
     * 修改媒体文件
     * 
     * @param mediaFile 媒体文件
     * @return 结果
     */
    @Override
    public int updateMediaFile(MediaFile mediaFile)
    {
        mediaFile.setUpdateTime(DateUtils.getNowDate());
        return mediaFileMapper.updateMediaFile(mediaFile);
    }

    /**
     * 批量删除媒体文件
     * 
     * @param fileIds 需要删除的媒体文件主键
     * @return 结果
     */
    @Override
    public int deleteMediaFileByFileIds(Long[] fileIds)
    {
        return mediaFileMapper.deleteMediaFileByFileIds(fileIds);
    }

    /**
     * 删除媒体文件信息
     * 
     * @param fileId 媒体文件主键
     * @return 结果
     */
    @Override
    public int deleteMediaFileByFileId(Long fileId)
    {
        return mediaFileMapper.deleteMediaFileByFileId(fileId);
    }

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
    @Override
    public MediaFile uploadMediaFile(MultipartFile file, String title, String description, Long categoryId, String tags)
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath() + "/media";
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file, MimeTypeUtils.MEDIA_ALLOWED_EXTENSION);
            
            // 创建媒体文件对象
            MediaFile mediaFile = new MediaFile();
            mediaFile.setFileName(fileName);
            mediaFile.setOriginalName(file.getOriginalFilename());
            mediaFile.setFilePath(filePath + "/" + fileName);
            mediaFile.setFileUrl("/profile/media/" + fileName);
            mediaFile.setFileSize(file.getSize());
            mediaFile.setTitle(StringUtils.isNotEmpty(title) ? title : file.getOriginalFilename());
            mediaFile.setDescription(description);
            mediaFile.setCategoryId(categoryId);
            mediaFile.setTags(tags);
            
            // 判断文件类型
            String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
            if (isAudioFile(extension)) {
                mediaFile.setFileType("audio");
            } else if (isVideoFile(extension)) {
                mediaFile.setFileType("video");
            }
            mediaFile.setFileFormat(extension);
            
            // 设置默认值
            mediaFile.setViewCount(0);
            mediaFile.setLikeCount(0);
            mediaFile.setStatus("0");
            mediaFile.setIsPublic("1");
            mediaFile.setSortOrder(0);
            mediaFile.setCreateBy(SecurityUtils.getUsername());
            mediaFile.setCreateTime(DateUtils.getNowDate());
            
            // 保存到数据库
            insertMediaFile(mediaFile);
            
            return mediaFile;
        }
        catch (Exception e)
        {
            throw new RuntimeException("上传失败：" + e.getMessage(), e);
        }
    }

    /**
     * 播放媒体文件（增加播放次数）
     * 
     * @param fileId 文件ID
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 结果
     */
    @Override
    public int playMediaFile(Long fileId, Long userId, String deviceId)
    {
        // 增加播放次数
        int result = mediaFileMapper.incrementViewCount(fileId);
        
        // TODO: 记录播放记录到 media_play_record 表
        // 这里可以添加播放记录的逻辑
        
        return result;
    }

    /**
     * 点赞媒体文件
     * 
     * @param fileId 文件ID
     * @return 结果
     */
    @Override
    public int likeMediaFile(Long fileId)
    {
        return mediaFileMapper.incrementLikeCount(fileId);
    }

    /**
     * 查询公开的媒体文件列表（用于iOS应用）
     * 
     * @param mediaFile 查询条件
     * @return 媒体文件集合
     */
    @Override
    public List<MediaFile> selectPublicMediaFileList(MediaFile mediaFile)
    {
        // 确保只查询公开且正常状态的文件
        mediaFile.setIsPublic("1");
        mediaFile.setStatus("0");
        return mediaFileMapper.selectPublicMediaFileList(mediaFile);
    }

    /**
     * 根据分类查询媒体文件
     * 
     * @param categoryId 分类ID
     * @return 媒体文件集合
     */
    @Override
    public List<MediaFile> selectMediaFilesByCategoryId(Long categoryId)
    {
        return mediaFileMapper.selectMediaFilesByCategoryId(categoryId);
    }

    /**
     * 判断是否为音频文件
     */
    private boolean isAudioFile(String extension)
    {
        String[] audioExtensions = {"mp3", "wav", "wma", "aac", "flac", "ogg", "m4a"};
        for (String ext : audioExtensions) {
            if (ext.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为视频文件
     */
    private boolean isVideoFile(String extension)
    {
        String[] videoExtensions = {"mp4", "avi", "rmvb", "wmv", "mov", "flv", "mkv", "webm", "m4v"};
        for (String ext : videoExtensions) {
            if (ext.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取播放统计数据
     */
    @Override
    public Map<String, Object> getPlayStatistics()
    {
        Map<String, Object> result = new HashMap<>();
        
        // 总文件数
        List<MediaFile> allFiles = mediaFileMapper.selectMediaFileList(new MediaFile());
        result.put("totalFiles", allFiles.size());
        
        // 总播放次数
        long totalPlays = allFiles.stream().mapToLong(file -> file.getViewCount() != null ? file.getViewCount() : 0).sum();
        result.put("totalPlays", totalPlays);
        
        // 总点赞数
        long totalLikes = allFiles.stream().mapToLong(file -> file.getLikeCount() != null ? file.getLikeCount() : 0).sum();
        result.put("totalLikes", totalLikes);
        
        // 平均播放次数
        result.put("avgPlays", allFiles.size() > 0 ? totalPlays / allFiles.size() : 0);
        
        return result;
    }
    
    /**
     * 获取播放排行榜
     */
    @Override
    public List<MediaFile> getPlayRanking(int limit)
    {
        MediaFile mediaFile = new MediaFile();
        List<MediaFile> allFiles = mediaFileMapper.selectMediaFileList(mediaFile);
        return allFiles.stream()
                .sorted((a, b) -> Long.compare(
                    b.getViewCount() != null ? b.getViewCount() : 0,
                    a.getViewCount() != null ? a.getViewCount() : 0))
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * 获取点赞排行榜
     */
    @Override
    public List<MediaFile> getLikeRanking(int limit)
    {
        MediaFile mediaFile = new MediaFile();
        List<MediaFile> allFiles = mediaFileMapper.selectMediaFileList(mediaFile);
        return allFiles.stream()
                .sorted((a, b) -> Long.compare(
                    b.getLikeCount() != null ? b.getLikeCount() : 0,
                    a.getLikeCount() != null ? a.getLikeCount() : 0))
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * 获取文件类型分布
     */
    @Override
    public Map<String, Long> getFileTypeDistribution()
    {
        MediaFile mediaFile = new MediaFile();
        List<MediaFile> allFiles = mediaFileMapper.selectMediaFileList(mediaFile);
        return allFiles.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    file -> file.getFileType() != null ? file.getFileType() : "unknown",
                    java.util.stream.Collectors.counting()));
    }
}