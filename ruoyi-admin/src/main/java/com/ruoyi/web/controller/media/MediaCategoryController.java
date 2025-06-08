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
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.media.domain.MediaCategory;
import com.ruoyi.media.exception.MediaOperationException;
import com.ruoyi.media.service.IMediaCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 媒体分类Controller
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@Api(tags = "媒体分类管理")
@RestController
@RequestMapping("/media/category")
public class MediaCategoryController extends BaseController
{
    @Autowired
    private IMediaCategoryService mediaCategoryService;

    /**
     * 查询媒体分类列表
     */
    @ApiOperation(value = "获取媒体分类列表", notes = "获取所有媒体分类的列表数据")
    @PreAuthorize("@ss.hasPermi('media:category:list')")
    @GetMapping("/list")
    public AjaxResult list(MediaCategory mediaCategory)
    {
        try {
            List<MediaCategory> list = mediaCategoryService.selectMediaCategoryList(mediaCategory);
            return success(list);
        } catch (Exception e) {
            throw new MediaOperationException("查询媒体分类列表失败：" + e.getMessage());
        }
    }

    /**
     * 导出媒体分类列表
     */
    @ApiOperation(value = "导出媒体分类", notes = "导出所有媒体分类的数据")
    @PreAuthorize("@ss.hasPermi('media:category:export')")
    @Log(title = "媒体分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MediaCategory mediaCategory)
    {
        List<MediaCategory> list = mediaCategoryService.selectMediaCategoryList(mediaCategory);
        ExcelUtil<MediaCategory> util = new ExcelUtil<MediaCategory>(MediaCategory.class);
        util.exportExcel(response, list, "媒体分类数据");
    }

    /**
     * 获取媒体分类详细信息
     */
    @ApiOperation(value = "获取媒体分类详情", notes = "根据分类ID获取媒体分类的详细信息")
    @ApiImplicitParam(name = "categoryId", value = "媒体分类ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("@ss.hasPermi('media:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        try {
            return success(mediaCategoryService.selectMediaCategoryByCategoryId(categoryId));
        } catch (Exception e) {
            return error("获取媒体分类详情失败：" + e.getMessage());
        }
    }

    /**
     * 新增媒体分类
     */
    @ApiOperation(value = "新增媒体分类", notes = "新增媒体分类信息")
    @PreAuthorize("@ss.hasPermi('media:category:add')")
    @Log(title = "媒体分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MediaCategory mediaCategory)
    {
        try {
            if (mediaCategory.getCategoryName() == null || mediaCategory.getCategoryName().isEmpty()) {
                return error("分类名称不能为空");
            }
            return toAjax(mediaCategoryService.insertMediaCategory(mediaCategory));
        } catch (Exception e) {
            return error("新增媒体分类失败：" + e.getMessage());
        }
    }

    /**
     * 修改媒体分类
     */
    @ApiOperation(value = "修改媒体分类", notes = "修改媒体分类信息")
    @PreAuthorize("@ss.hasPermi('media:category:edit')")
    @Log(title = "媒体分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MediaCategory mediaCategory)
    {
        try {
            if (mediaCategory.getCategoryId() == null) {
                return error("分类ID不能为空");
            }
            if (mediaCategory.getCategoryName() == null || mediaCategory.getCategoryName().isEmpty()) {
                return error("分类名称不能为空");
            }
            return toAjax(mediaCategoryService.updateMediaCategory(mediaCategory));
        } catch (Exception e) {
            return error("修改媒体分类失败：" + e.getMessage());
        }
    }

    /**
     * 删除媒体分类
     */
    @ApiOperation(value = "删除媒体分类", notes = "根据分类ID删除媒体分类")
    @ApiImplicitParam(name = "categoryIds", value = "媒体分类ID数组", required = true, dataType = "Long[]", paramType = "path")
    @PreAuthorize("@ss.hasPermi('media:category:remove')")
    @Log(title = "媒体分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        try {
            if (categoryIds == null || categoryIds.length == 0) {
                return error("请选择要删除的媒体分类");
            }
            return toAjax(mediaCategoryService.deleteMediaCategoryByCategoryIds(categoryIds));
        } catch (Exception e) {
            return error("删除媒体分类失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取媒体分类下拉树列表
     */
    @ApiOperation(value = "获取分类下拉树", notes = "获取媒体分类的树形结构数据，用于下拉选择")
    @GetMapping("/treeselect")
    public AjaxResult treeselect(MediaCategory mediaCategory)
    {
        try {
            List<MediaCategory> categories = mediaCategoryService.selectMediaCategoryList(mediaCategory);
            return success(mediaCategoryService.buildCategoryTreeSelect(categories));
        } catch (Exception e) {
            return error("获取媒体分类树失败：" + e.getMessage());
        }
    }
} 