package com.microsoft.bot.sample.data.services;

import java.util.Arrays;
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
    public void showAllDepartments() {
         
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

   
        public Department findItemByRegexpName(String regexp) {
            List<Department> list = departmentRepository.findItemByRegexpName(regexp);
            list.forEach(item -> System.out.println("Name: " + item.getDeptName() + ", Description: " + item.getDescription()));
            return list.isEmpty() ? null : list.get(0);

        }


        public Department findItemByRegexpDescription(String regexp) {
            List<Department> list = departmentRepository.findItemByRegexpDescription(regexp);
            //list.forEach(item -> System.out.println("Name: " + item.getDeptName() + ", Description: " + item.getDescription()));
            return list.isEmpty() ? null : list.get(0);
        }

    
        public Department findItemByRegexpTags(String regexp) {

            String[] splitted = regexp.split(" ");
            System.out.println(splitted);
            List<Department> list = null;

            if(splitted!=null && splitted.length>=3)
                list = departmentRepository.findItemByRegexpTagsThree(splitted[0]!=null ? splitted[0] : null, splitted[1]!=null ? splitted[1] : null,splitted[2]!=null ? splitted[2] : null);
            else if(splitted!=null && splitted.length==2)
                list = departmentRepository.findItemByRegexpTagsTwo(splitted[0]!=null ? splitted[0] : null, splitted[1]!=null ? splitted[1] : null);
                else if(splitted!=null && splitted.length==1)
                list = departmentRepository.findItemByRegexpTagsOne(splitted[0]!=null ? splitted[0] : null);


            //list.forEach(item -> System.out.println("Name: " + item.getDeptName() + ", Description: " + item.getDescription()));
            return (list==null ||  list.isEmpty()) ? null : list.get(0);
        }

  
        public Department findItemByRegexpTagsAndCondition(String regexp) {

            String[] splitted = regexp.split(" ");
            System.out.println(Arrays.toString(splitted));
            List<Department> list = null;

            if(splitted!=null && splitted.length>=3)
                list = departmentRepository.findItemByRegexpTagsAndConditionThree(splitted[0]!=null ? splitted[0] : null, splitted[1]!=null ? splitted[1] : null,splitted[2]!=null ? splitted[2] : null);
            else if(splitted!=null && splitted.length==2)
                list = departmentRepository.findItemByRegexpTagsAndConditionTwo(splitted[0]!=null ? splitted[0] : null, splitted[1]!=null ? splitted[1] : null);
                else if(splitted!=null && splitted.length==1)
                list = departmentRepository.findItemByRegexpTagsAndConditionOne(splitted[0]!=null ? splitted[0] : null);


            //list.forEach(item -> System.out.println("Name: " + item.getDeptName() + ", Description: " + item.getDescription()));
            return (list==null ||  list.isEmpty()) ? null : list.get(0);
        }
        

    public String getItemDetails(Department item) {

        System.out.println(
        "Item Name: " + item.getDeptName() + 
        ", \nItem Description: " + item.getDescription()
        );
        
        return "";
    }
}
