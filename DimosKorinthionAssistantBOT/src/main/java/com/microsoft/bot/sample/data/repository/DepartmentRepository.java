package com.microsoft.bot.sample.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.microsoft.bot.sample.data.model.Department;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    
    @Query("{DeptName:'?0'}")
    Department findItemByName(String name);
    

    public long count();

}