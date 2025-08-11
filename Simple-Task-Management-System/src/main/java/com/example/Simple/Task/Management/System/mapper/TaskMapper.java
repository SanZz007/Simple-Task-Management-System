package com.example.Simple.Task.Management.System.mapper;


import com.example.Simple.Task.Management.System.model.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskMapper {

    @Select("SELECT id, title, description, completed FROM tasks")
    List<Task> findAll();

    @Select("SELECT id, title, description, completed FROM tasks WHERE id = #{id}")
    Optional<Task> findById(Long id);

    @Insert("INSERT INTO tasks(title, description, completed) VALUES(#{title}, #{description}, #{completed})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Task task);

    @Update("UPDATE tasks SET title=#{title}, description=#{description}, completed=#{completed} WHERE id=#{id}")
    int update(Task task);

    @Delete("DELETE FROM tasks WHERE id = #{id}")
    int deleteById(Long id);
}