package com.ruoyi.web.controller.media;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.media.domain.MediaCategory;
import com.ruoyi.media.domain.MediaFile;
import com.ruoyi.media.service.IMediaCategoryService;
import com.ruoyi.media.service.IMediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 媒体API Controller，专门提供给iOS应用使用的接口
 * 
 * 注意：这些接口都是公开的，不需要认证
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@Api(tags = "媒体App增强接口")
@RestController
@RequestMapping("/api/media/v2")
public class MediaAppApiController extends BaseController
{
    @Autowired
    private IMediaFileService mediaFileService;
    
    @Autowired
    private IMediaCategoryService mediaCategoryService;

    /**
     * 获取公开的媒体文件列表
     */
    @ApiOperation(value = "获取媒体列表", notes = "获取所有公开的媒体文件列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int"),
        @ApiImplicitParam(name = "pageSize", value = "每页记录数", dataType = "int"),
        @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "String"),
        @ApiImplicitParam(name = "categoryId", value = "分类ID", dataType = "Long"),
        @ApiImplicitParam(name = "title", value = "标题关键字", dataType = "String")
    })
    @GetMapping("/list")
    public TableDataInfo list(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "fileType", required = false) String fileType,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "title", required = false) String title)
    {
        try {
            startPage();
            MediaFile queryParams = new MediaFile();
            queryParams.setFileType(fileType);
            queryParams.setCategoryId(categoryId);
            queryParams.setTitle(title);
            // 确保只查询公开的文件
            queryParams.setIsPublic("1");
            queryParams.setStatus("0"); // 正常状态
            
            List<MediaFile> list = mediaFileService.selectMediaFileList(queryParams);
            return getDataTable(list);
        } catch (Exception e) {
            throw new RuntimeException("获取媒体列表失败：" + e.getMessage());
        }
    }

    /**
     * 获取媒体文件详情
     */
    @ApiOperation(value = "获取媒体详情", notes = "根据ID获取媒体文件的详细信息")
    @ApiImplicitParam(name = "fileId", value = "媒体文件ID", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        try {
            MediaFile mediaFile = mediaFileService.selectMediaFileByFileId(fileId);
            // 检查是否为公开文件
            if (!"1".equals(mediaFile.getIsPublic()) || !"0".equals(mediaFile.getStatus())) {
                return error("该媒体文件不存在或已被禁用");
            }
            return success(mediaFile);
        } catch (Exception e) {
            return error("获取媒体详情失败：" + e.getMessage());
        }
    }

    /**
     * 播放媒体文件（增加播放次数）
     */
    @ApiOperation(value = "播放媒体", notes = "播放媒体文件并记录播放信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fileId", value = "媒体文件ID", required = true, dataType = "Long", paramType = "path"),
        @ApiImplicitParam(name = "userId", value = "用户ID", required = false, dataType = "Long"),
        @ApiImplicitParam(name = "deviceId", value = "设备ID", required = false, dataType = "String")
    })
    @PostMapping("/play/{fileId}")
    public AjaxResult playFile(
            @PathVariable Long fileId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "deviceId", required = false) String deviceId)
    {
        try {
            MediaFile mediaFile = mediaFileService.selectMediaFileByFileId(fileId);
            // 检查是否为公开文件
            if (!"1".equals(mediaFile.getIsPublic()) || !"0".equals(mediaFile.getStatus())) {
                return error("该媒体文件不存在或已被禁用");
            }
            return toAjax(mediaFileService.playMediaFile(fileId, userId, deviceId));
        } catch (Exception e) {
            return error("播放记录失败：" + e.getMessage());
        }
    }

    /**
     * 点赞媒体文件
     */
    @ApiOperation(value = "点赞媒体", notes = "为媒体文件点赞")
    @ApiImplicitParam(name = "fileId", value = "媒体文件ID", required = true, dataType = "Long", paramType = "path")
    @PostMapping("/like/{fileId}")
    public AjaxResult likeFile(@PathVariable Long fileId)
    {
        try {
            MediaFile mediaFile = mediaFileService.selectMediaFileByFileId(fileId);
            // 检查是否为公开文件
            if (!"1".equals(mediaFile.getIsPublic()) || !"0".equals(mediaFile.getStatus())) {
                return error("该媒体文件不存在或已被禁用");
            }
            return toAjax(mediaFileService.likeMediaFile(fileId));
        } catch (Exception e) {
            return error("点赞失败：" + e.getMessage());
        }
    }

    /**
     * 获取分类列表
     */
    @ApiOperation(value = "获取分类列表", notes = "获取所有媒体分类列表")
    @GetMapping("/categories")
    public AjaxResult listCategories()
    {
        try {
            MediaCategory queryParams = new MediaCategory();
            queryParams.setStatus("0"); // 只查询正常状态的分类
            List<MediaCategory> categories = mediaCategoryService.selectMediaCategoryList(queryParams);
            return success(mediaCategoryService.buildCategoryTreeSelect(categories));
        } catch (Exception e) {
            return error("获取分类列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据分类获取媒体文件
     */
    @ApiOperation(value = "获取分类媒体", notes = "获取指定分类下的所有媒体文件")
    @ApiImplicitParam(name = "categoryId", value = "分类ID", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/category/{categoryId}")
    public TableDataInfo listByCategory(
            @PathVariable Long categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize)
    {
        try {
            startPage();
            MediaFile queryParams = new MediaFile();
            queryParams.setCategoryId(categoryId);
            queryParams.setIsPublic("1");
            queryParams.setStatus("0");
            List<MediaFile> list = mediaFileService.selectMediaFileList(queryParams);
            return getDataTable(list);
        } catch (Exception e) {
            throw new RuntimeException("获取分类媒体失败：" + e.getMessage());
        }
    }

    /**
     * 获取热门推荐
     */
    @ApiOperation(value = "获取热门推荐", notes = "获取播放量最高的媒体文件")
    @ApiImplicitParam(name = "limit", value = "返回数量", required = false, dataType = "int")
    @GetMapping("/hot")
    public AjaxResult getHotRecommend(@RequestParam(value = "limit", defaultValue = "10") Integer limit)
    {
        try {
            List<MediaFile> hotList = mediaFileService.getPlayRanking(limit);
            return success(hotList);
        } catch (Exception e) {
            return error("获取热门推荐失败：" + e.getMessage());
        }
    }

    /**
     * 获取最受欢迎
     */
    @ApiOperation(value = "获取最受欢迎", notes = "获取点赞量最高的媒体文件")
    @ApiImplicitParam(name = "limit", value = "返回数量", required = false, dataType = "int")
    @GetMapping("/popular")
    public AjaxResult getPopular(@RequestParam(value = "limit", defaultValue = "10") Integer limit)
    {
        try {
            List<MediaFile> popularList = mediaFileService.getLikeRanking(limit);
            return success(popularList);
        } catch (Exception e) {
            return error("获取最受欢迎失败：" + e.getMessage());
        }
    }

    /**
     * 获取统计数据
     */
    @ApiOperation(value = "获取统计数据", notes = "获取媒体播放和点赞的统计数据")
    @GetMapping("/stats")
    public AjaxResult getStatistics()
    {
        try {
            Map<String, Object> stats = mediaFileService.getPlayStatistics();
            return success(stats);
        } catch (Exception e) {
            return error("获取统计数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取文件类型分布
     */
    @ApiOperation(value = "获取类型分布", notes = "获取媒体文件类型的分布统计")
    @GetMapping("/type-distribution")
    public AjaxResult getTypeDistribution()
    {
        try {
            Map<String, Long> distribution = mediaFileService.getFileTypeDistribution();
            return success(distribution);
        } catch (Exception e) {
            return error("获取类型分布失败：" + e.getMessage());
        }
    }
} 