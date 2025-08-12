package com.sky.service;

import com.sky.dto.DishDTO;

public interface Dishservice {
    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
