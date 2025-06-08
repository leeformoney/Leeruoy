package com.ruoyi.web.controller.api;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.media.domain.MediaFile;
import com.ruoyi.media.service.IMediaFileService;

/**
 * iOS应用媒体API控制器
 * 专门为iOS应用提供的媒体文件接口
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@RestController
@RequestMapping("/api/media")
public class MediaApiController extends BaseController
{
    @Autowired
    private IMediaFileService mediaFileService;

    /**
     * 获取公开的媒体文件列表（iOS应用使用）
     */
    @GetMapping("/public/list")
    public TableDataInfo getPublicMediaList(@RequestParam(value = "fileType", required = false) String fileType,
                                           @RequestParam(value = "categoryId", required = false) Long categoryId,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize)
    {
        startPage();
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileType(fileType);
        mediaFile.setCategoryId(categoryId);
        mediaFile.setIsPublic("1"); // 只查询公开的文件
        mediaFile.setStatus("0");   // 只查询正常状态的文件
        List<MediaFile> list = mediaFileService.selectPublicMediaFileList(mediaFile);
        return getDataTable(list);
    }

    /**
     * 获取媒体文件详情（iOS应用使用）
     */
    @GetMapping("/public/{fileId}")
    public AjaxResult getMediaDetail(@PathVariable Long fileId)
    {
        MediaFile mediaFile = mediaFileService.selectMediaFileByFileId(fileId);
        if (mediaFile != null && "1".equals(mediaFile.getIsPublic()) && "0".equals(mediaFile.getStatus())) {
            return success(mediaFile);
        }
        return error("媒体文件不存在或不可访问");
    }

    /**
     * 播放媒体文件（iOS应用使用，增加播放次数）
     */
    @PostMapping("/play/{fileId}")
    public AjaxResult playMedia(@PathVariable Long fileId,
                               @RequestParam(value = "deviceId", required = false) String deviceId)
    {
        try {
            mediaFileService.playMediaFile(fileId, null, deviceId);
            return success("播放记录已更新");
        } catch (Exception e) {
            return error("播放记录更新失败");
        }
    }

    /**
     * 点赞媒体文件（iOS应用使用）
     */
    @PostMapping("/like/{fileId}")
    public AjaxResult likeMedia(@PathVariable Long fileId)
    {
        try {
            mediaFileService.likeMediaFile(fileId);
            return success("点赞成功");
        } catch (Exception e) {
            return error("点赞失败");
        }
    }

    /**
     * 根据分类获取媒体文件（iOS应用使用）
     */
    @GetMapping("/category/{categoryId}")
    public TableDataInfo getMediaByCategory(@PathVariable Long categoryId)
    {
        startPage();
        List<MediaFile> list = mediaFileService.selectMediaFilesByCategoryId(categoryId);
        // 过滤只返回公开且正常状态的文件
        list = list.stream()
                  .filter(file -> "1".equals(file.getIsPublic()) && "0".equals(file.getStatus()))
                  .collect(Collectors.toList());
        return getDataTable(list);
    }

    /**
     * 上传媒体文件（iOS应用使用）
     * 注意：这个接口可能需要额外的认证机制
     */
    @PostMapping("/upload")
    public AjaxResult uploadMedia(@RequestParam("file") MultipartFile file,
                                 @RequestParam("title") String title,
                                 @RequestParam(value = "description", required = false) String description,
                                 @RequestParam(value = "categoryId", required = false) Long categoryId,
                                 @RequestParam(value = "tags", required = false) String tags)
    {
        try {
            MediaFile mediaFile = mediaFileService.uploadMediaFile(file, title, description, categoryId, tags);
            return success(mediaFile);
        } catch (Exception e) {
            return error("上传失败：" + e.getMessage());
        }
    }

    /**
     * 获取热门媒体文件（iOS应用使用）
     */
    @GetMapping("/hot")
    public TableDataInfo getHotMedia(@RequestParam(value = "fileType", required = false) String fileType,
                                    @RequestParam(value = "limit", defaultValue = "20") Integer limit)
    {
        startPage();
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileType(fileType);
        mediaFile.setIsPublic("1");
        mediaFile.setStatus("0");
        List<MediaFile> list = mediaFileService.selectPublicMediaFileList(mediaFile);
        // 按播放次数和点赞数排序，返回热门内容
        list = list.stream()
                  .sorted((a, b) -> {
                      int scoreA = (a.getViewCount() != null ? a.getViewCount() : 0) + 
                                  (a.getLikeCount() != null ? a.getLikeCount() : 0) * 2;
                      int scoreB = (b.getViewCount() != null ? b.getViewCount() : 0) + 
                                  (b.getLikeCount() != null ? b.getLikeCount() : 0) * 2;
                      return Integer.compare(scoreB, scoreA);
                  })
                  .limit(limit)
                  .collect(Collectors.toList());
        return getDataTable(list);
    }

    /**
     * 搜索媒体文件（iOS应用使用）
     */
    @GetMapping("/search")
    public TableDataInfo searchMedia(@RequestParam("keyword") String keyword,
                                    @RequestParam(value = "fileType", required = false) String fileType)
    {
        startPage();
        MediaFile mediaFile = new MediaFile();
        mediaFile.setTitle(keyword); // 在title中搜索关键词
        mediaFile.setFileType(fileType);
        mediaFile.setIsPublic("1");
        mediaFile.setStatus("0");
        List<MediaFile> list = mediaFileService.selectPublicMediaFileList(mediaFile);
        return getDataTable(list);
    }
} 