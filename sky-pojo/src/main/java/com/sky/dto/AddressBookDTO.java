package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class AddressBookDTO implements Serializable {

    private Long id;
    private Long userId;
    private String consignee;
    private String phone;
    private String sex;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;

    // 添加这两个字段来接收前端数据
    private String name; // 用于接收前端错误的'name'字段
    private Integer type; // 用于接收前端多余的'type'字段
}