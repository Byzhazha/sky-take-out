package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.Dishservice;
import com.sky.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Tag(name = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private Dishservice dishservice;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishservice.saveWithFlavor(dishDTO);
        return Result.success();
    }


    @GetMapping("/page")
    @Operation(summary = "菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询", dishPageQueryDTO);
        PageResult pageResult = dishservice.pageQuery(dishPageQueryDTO);

        return Result.success(pageResult);
    }


    @DeleteMapping
    @Operation(summary = "菜品的批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品的批量删除：{}", ids);
        dishservice.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品：{}", id);
        DishVO dishVO = dishservice.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @Operation(summary = "修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("根据id查询菜品:{}",dishDTO);
        dishservice.updateWithFlavor(dishDTO);
        return Result.success(dishDTO);

    }
}
