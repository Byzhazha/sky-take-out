package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    // 新增一个方法，用于根据ID更新员工信息
    @Update("update employee set password = #{password} where id = #{id}")
    void update(Employee employee);

    // 新增一个方法，用于获取所有员工
    @Select("select * from employee")
    List<Employee> getAll();
}