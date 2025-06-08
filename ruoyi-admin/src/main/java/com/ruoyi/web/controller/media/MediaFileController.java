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
import com.ruoyi.media.exception.MediaOperationException;
import com.ruoyi.media.service.IMediaFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 媒体文件Controller
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@Api(tags = "媒体文件管理")
@RestController
@RequestMapping("/media/file")
public class MediaFileController extends BaseController
{
    @Autowired
    private IMediaFileService mediaFileService;

    /**
     * 查询媒体文件列表
     */
    @ApiOperation(value = "获取媒体文件列表", notes = "获取所有媒体文件的列表数据")
    @PreAuthorize("@ss.hasPermi('media:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(MediaFile mediaFile)
    {
        try {
            startPage();
            List<MediaFile> list = mediaFileService.selectMediaFileList(mediaFile);
            return getDataTable(list);
        } catch (Exception e) {
            throw new MediaOperationException("查询媒体文件列表失败：" + e.getMessage());
        }
    }

    /**
     * 导出媒体文件列表
     */
    @ApiOperation(value = "导出媒体文件", notes = "导出所有媒体文件的数据")
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
    @ApiOperation(value = "获取媒体文件详情", notes = "根据文件ID获取媒体文件的详细信息")
    @ApiImplicitParam(name = "fileId", value = "媒体文件ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('media:file:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        try {
            return success(mediaFileService.selectMediaFileByFileId(fileId));
        } catch (Exception e) {
            return error("获取媒体文件详情失败：" + e.getMessage());
        }
    }

    /**
     * 新增媒体文件
     */
    @ApiOperation(value = "新增媒体文件", notes = "新增媒体文件信息")
    @PreAuthorize("@ss.hasPermi('media:file:add')")
    @Log(title = "媒体文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MediaFile mediaFile)
    {
        try {
            if (mediaFile.getTitle() == null || mediaFile.getTitle().isEmpty()) {
                return error("媒体标题不能为空");
            }
            return toAjax(mediaFileService.insertMediaFile(mediaFile));
        } catch (Exception e) {
            return error("新增媒体文件失败：" + e.getMessage());
        }
    }

    /**
     * 修改媒体文件
     */
    @ApiOperation(value = "修改媒体文件", notes = "修改媒体文件信息")
    @PreAuthorize("@ss.hasPermi('media:file:edit')")
    @Log(title = "媒体文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MediaFile mediaFile)
    {
        try {
            if (mediaFile.getFileId() == null) {
                return error("媒体文件ID不能为空");
            }
            if (mediaFile.getTitle() == null || mediaFile.getTitle().isEmpty()) {
                return error("媒体标题不能为空");
            }
            return toAjax(mediaFileService.updateMediaFile(mediaFile));
        } catch (Exception e) {
            return error("修改媒体文件失败：" + e.getMessage());
        }
    }

    /**
     * 删除媒体文件
     */
    @ApiOperation(value = "删除媒体文件", notes = "根据文件ID删除媒体文件")
    @ApiImplicitParam(name = "fileIds", value = "媒体文件ID数组", required = true, dataType = "Long[]", paramType = "path")
    @PreAuthorize("@ss.hasPermi('media:file:remove')")
    @Log(title = "媒体文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        try {
            if (fileIds == null || fileIds.length == 0) {
                return error("请选择要删除的媒体文件");
            }
            return toAjax(mediaFileService.deleteMediaFileByFileIds(fileIds));
        } catch (Exception e) {
            return error("删除媒体文件失败：" + e.getMessage());
        }
    }

    /**
     * 上传媒体文件
     */
    @ApiOperation(value = "上传媒体文件", notes = "上传媒体文件到服务器")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile"),
        @ApiImplicitParam(name = "title", value = "媒体标题", required = true, dataType = "String"),
        @ApiImplicitParam(name = "description", value = "媒体描述", required = false, dataType = "String"),
        @ApiImplicitParam(name = "categoryId", value = "分类ID", required = false, dataType = "Long"),
        @ApiImplicitParam(name = "tags", value = "标签，多个用逗号分隔", required = false, dataType = "String")
    })
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
            if (file == null || file.isEmpty()) {
                return error("上传的文件不能为空");
            }
            if (title == null || title.isEmpty()) {
                return error("媒体标题不能为空");
            }
            MediaFile mediaFile = mediaFileService.uploadMediaFile(file, title, description, categoryId, tags);
            return success(mediaFile);
        }
        catch (MediaOperationException e)
        {
            return error(e.getMessage());
        }
        catch (Exception e)
        {
            return error("上传失败：" + e.getMessage());
        }
    }

    /**
     * 播放媒体文件（增加播放次数）
     */
    @ApiOperation(value = "播放媒体文件", notes = "播放媒体文件并增加播放次数")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fileId", value = "媒体文件ID", required = true, dataType = "Long", paramType = "path"),
        @ApiImplicitParam(name = "userId", value = "用户ID", required = false, dataType = "Long"),
        @ApiImplicitParam(name = "deviceId", value = "设备ID", required = false, dataType = "String")
    })
    @PostMapping("/play/{fileId}")
    public AjaxResult playFile(@PathVariable Long fileId,
                              @RequestParam(value = "userId", required = false) Long userId,
                              @RequestParam(value = "deviceId", required = false) String deviceId)
    {
        try {
            return toAjax(mediaFileService.playMediaFile(fileId, userId, deviceId));
        } catch (Exception e) {
            return error("播放记录失败：" + e.getMessage());
        }
    }

    /**
     * 点赞媒体文件
     */
    @ApiOperation(value = "点赞媒体文件", notes = "对媒体文件进行点赞并增加点赞次数")
    @ApiImplicitParam(name = "fileId", value = "媒体文件ID", required = true, dataType = "Long", paramType = "path")
    @PostMapping("/like/{fileId}")
    public AjaxResult likeFile(@PathVariable Long fileId)
    {
        try {
            return toAjax(mediaFileService.likeMediaFile(fileId));
        } catch (Exception e) {
            return error("点赞失败：" + e.getMessage());
        }
    }
} 