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
    
    @Query("{ 'DeptName' : { $regex: ?0, $options:'i' }}")
    List<Department> findItemByRegexpName(String regexp);

    //@Query("{ 'Description' : { $regex: ?0 } }")
    @Query("{$or : [{'Description': { $regex: ?0, $options:'i' }}, {'DeptName': { $regex: ?0, $options:'i' }}]}")
    List<Department> findItemByRegexpDescription(String regexp);

    @Query("{ 'Tags': { $in: [ { $regex: ?0, $options:'i' }, { $regex: ?1, $options:'i' },{ $regex: ?2, $options:'i' } ] } }")
    List<Department> findItemByRegexpTagsThree(String token1, String token2,String token3);

    @Query("{ 'Tags': { $in: [ { $regex: ?0, $options:'i' }, { $regex: ?1, $options:'i' } ] } }")
    List<Department> findItemByRegexpTagsTwo(String token1, String token2);

    @Query("{ 'Tags': { $in: [ { $regex: ?0, $options:'i' } ] } }")
    List<Department> findItemByRegexpTagsOne(String token1);


    @Query("{ $and: [ {Tags: { $in: [ { $regex: ?0, $options:'i' } ] }}, {Tags: { $in: [{ $regex: ?1, $options:'i' } ] }},{Tags: { $in: [{ $regex: ?2, $options:'i' }] }}  ] }")
    List<Department> findItemByRegexpTagsAndConditionThree(String token1, String token2,String token3);

    @Query("{ $and: [ {Tags: { $in: [ { $regex: ?0, $options:'i' } ] }}, {Tags: { $in: [{ $regex: ?1, $options:'i' } ] }}] }")
    List<Department> findItemByRegexpTagsAndConditionTwo(String token1, String token2);

    @Query("{ 'Tags': { $in: [ { $regex: ?0, $options:'i' } ] } }")
    List<Department> findItemByRegexpTagsAndConditionOne(String token1);


    


        


}