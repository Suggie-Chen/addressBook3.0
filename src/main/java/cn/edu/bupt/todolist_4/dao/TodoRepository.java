package cn.edu.bupt.todolist_4.dao;

import cn.edu.bupt.todolist_4.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {
}
