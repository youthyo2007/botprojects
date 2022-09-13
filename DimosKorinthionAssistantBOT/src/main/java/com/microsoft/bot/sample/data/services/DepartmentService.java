package com.microsoft.bot.sample.data.services;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import com.microsoft.bot.sample.data.model.Department;
import com.microsoft.bot.sample.data.repository.DepartmentRepository;

@Service
public class DepartmentService {


    @Autowired
    DepartmentRepository departmentRepository;
    

// READ
    // 1. Show all the data
    public void showAllItems() {
         
        departmentRepository.findAll().forEach(item -> System.out.println(getItemDetails(item)));
    }
    
    // 2. Get item by name
    public void getItemByName(String name) {
        System.out.println("Getting item by name: " + name);
        Department item = departmentRepository.findItemByName(name);
        System.out.println(getItemDetails(item));
    }

        
    // 3. Get name and quantity of a all items of a particular category
    public List<Department>  getItemsList() {
        List<Department> list = departmentRepository.findAll();
        list.forEach(item -> System.out.println("Name: " + item.getDeptName() + ", Description: " + item.getDescription()));
        return list;


    }


    public String getItemDetails(Department item) {

        System.out.println(
        "Item Name: " + item.getDeptName() + 
        ", \nItem Description: " + item.getDescription()
        );
        
        return "";
    }
}
