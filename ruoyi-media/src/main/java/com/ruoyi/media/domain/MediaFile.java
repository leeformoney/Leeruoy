package com.ruoyi.media.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 媒体文件对象 media_file
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
public class MediaFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件ID */
    private Long fileId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 原始文件名 */
    @Excel(name = "原始文件名")
    private String originalName;

    /** 文件路径 */
    private String filePath;

    /** 文件访问URL */
    @Excel(name = "文件URL")
    private String fileUrl;

    /** 文件大小（字节） */
    @Excel(name = "文件大小")
    private Long fileSize;

    /** 文件类型（audio/video） */
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件格式（mp3,mp4,avi等） */
    @Excel(name = "文件格式")
    private String fileFormat;

    /** 时长（秒） */
    @Excel(name = "时长")
    private Integer duration;

    /** 缩略图URL */
    private String thumbnailUrl;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 媒体标题 */
    @Excel(name = "媒体标题")
    private String title;

    /** 媒体描述 */
    @Excel(name = "媒体描述")
    private String description;

    /** 标签（逗号分隔） */
    @Excel(name = "标签")
    private String tags;

    /** 播放次数 */
    @Excel(name = "播放次数")
    private Integer viewCount;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Integer likeCount;

    /** 状态（0正常 1停用 2审核中） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用,2=审核中")
    private String status;

    /** 是否公开（0私有 1公开） */
    @Excel(name = "是否公开", readConverterExp = "0=私有,1=公开")
    private String isPublic;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortOrder;

    /** 分类名称 */
    private String categoryName;

    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }

    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }

    public void setOriginalName(String originalName) 
    {
        this.originalName = originalName;
    }

    public String getOriginalName() 
    {
        return originalName;
    }

    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }

    public void setFileUrl(String fileUrl) 
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() 
    {
        return fileUrl;
    }

    public void setFileSize(Long fileSize) 
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize() 
    {
        return fileSize;
    }

    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
    }

    public void setFileFormat(String fileFormat) 
    {
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() 
    {
        return fileFormat;
    }

    public void setDuration(Integer duration) 
    {
        this.duration = duration;
    }

    public Integer getDuration() 
    {
        return duration;
    }

    public void setThumbnailUrl(String thumbnailUrl) 
    {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() 
    {
        return thumbnailUrl;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setTags(String tags) 
    {
        this.tags = tags;
    }

    public String getTags() 
    {
        return tags;
    }

    public void setViewCount(Integer viewCount) 
    {
        this.viewCount = viewCount;
    }

    public Integer getViewCount() 
    {
        return viewCount;
    }

    public void setLikeCount(Integer likeCount) 
    {
        this.likeCount = likeCount;
    }

    public Integer getLikeCount() 
    {
        return likeCount;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setIsPublic(String isPublic) 
    {
        this.isPublic = isPublic;
    }

    public String getIsPublic() 
    {
        return isPublic;
    }

    public void setSortOrder(Integer sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() 
    {
        return sortOrder;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", originalName='" + originalName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", duration=" + duration +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", status='" + status + '\'' +
                ", isPublic='" + isPublic + '\'' +
                ", sortOrder=" + sortOrder +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}