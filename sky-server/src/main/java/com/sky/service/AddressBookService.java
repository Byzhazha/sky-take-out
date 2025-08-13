package com.sky.service;

import com.sky.dto.AddressBookDTO; // 导入新的DTO
import com.sky.entity.AddressBook;
import java.util.List;

public interface AddressBookService {

    List<AddressBook> list(AddressBook addressBook);

    // 将 save 方法的参数修改为 AddressBookDTO
    void save(AddressBookDTO addressBookDTO);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    void setDefault(AddressBook addressBook);

    void deleteById(Long id);

}