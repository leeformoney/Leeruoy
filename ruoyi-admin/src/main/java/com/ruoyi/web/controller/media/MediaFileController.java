package com.ruoyi.web.controller.media;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.media.domain.MediaFile;
import com.ruoyi.media.service.IMediaFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 媒体文件Controller
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@RestController
@RequestMapping("/media/file")
public class MediaFileController extends BaseController
{
    @Autowired
    private IMediaFileService mediaFileService;

    /**
     * 查询媒体文件列表
     */
    @PreAuthorize("@ss.hasPermi('media:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(MediaFile mediaFile)
    {
        startPage();
        List<MediaFile> list = mediaFileService.selectMediaFileList(mediaFile);
        return getDataTable(list);
    }

    /**
     * 导出媒体文件列表
     */
    @PreAuthorize("@ss.hasPermi('media:file:export')")
    @Log(title = "媒体文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MediaFile mediaFile)
    {
        List<MediaFile> list = mediaFileService.selectMediaFileList(mediaFile);
        ExcelUtil<MediaFile> util = new ExcelUtil<MediaFile>(MediaFile.class);
        util.exportExcel(response, list, "媒体文件数据");
    }

    /**
     * 获取媒体文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('media:file:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return success(mediaFileService.selectMediaFileByFileId(fileId));
    }

    /**
     * 新增媒体文件
     */
    @PreAuthorize("@ss.hasPermi('media:file:add')")
    @Log(title = "媒体文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MediaFile mediaFile)
    {
        return toAjax(mediaFileService.insertMediaFile(mediaFile));
    }

    /**
     * 修改媒体文件
     */
    @PreAuthorize("@ss.hasPermi('media:file:edit')")
    @Log(title = "媒体文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MediaFile mediaFile)
    {
        return toAjax(mediaFileService.updateMediaFile(mediaFile));
    }

    /**
     * 删除媒体文件
     */
    @PreAuthorize("@ss.hasPermi('media:file:remove')")
    @Log(title = "媒体文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(mediaFileService.deleteMediaFileByFileIds(fileIds));
    }

    /**
     * 上传媒体文件
     */
    @PreAuthorize("@ss.hasPermi('media:file:upload')")
    @Log(title = "媒体文件上传", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file,
                                @RequestParam("title") String title,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "categoryId", required = false) Long categoryId,
                                @RequestParam(value = "tags", required = false) String tags)
    {
        try
        {
            MediaFile mediaFile = mediaFileService.uploadMediaFile(file, title, description, categoryId, tags);
            return success(mediaFile);
        }
        catch (Exception e)
        {
            return error("上传失败：" + e.getMessage());
        }
    }

    /**
     * 播放媒体文件（增加播放次数）
     */
    @PostMapping("/play/{fileId}")
    public AjaxResult playFile(@PathVariable Long fileId,
                              @RequestParam(value = "userId", required = false) Long userId,
                              @RequestParam(value = "deviceId", required = false) String deviceId)
    {
        return toAjax(mediaFileService.playMediaFile(fileId, userId, deviceId));
    }

    /**
     * 点赞媒体文件
     */
    @PostMapping("/like/{fileId}")
    public AjaxResult likeFile(@PathVariable Long fileId)
    {
        return toAjax(mediaFileService.likeMediaFile(fileId));
    }
} 