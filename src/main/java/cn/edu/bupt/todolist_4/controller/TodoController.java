package cn.edu.bupt.todolist_4.controller;

import cn.edu.bupt.todolist_4.dao.TodoRepository;
import cn.edu.bupt.todolist_4.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/todo")
public class TodoController {
    private TodoRepository todoRepository;

    @Autowired
    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/list")
    String list(Model model,
                @RequestParam(value = "start",defaultValue = "0") Integer start,
                @RequestParam(value = "limit",defaultValue = "5") Integer limit){
        start = start <0 ? 0 :start;
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start,limit,sort);
        Page<Todo> todos = todoRepository.findAll(pageable);
        model.addAttribute("todos",todos);
        return "todolist";
    }

    @GetMapping("/add")
    String addForm(){
        return "todoadd";
    }

    @PostMapping("/add")
    String add(Todo todo){
        todoRepository.save(todo);
        return "success";
    }

    @GetMapping("/delete")
    String delete(@RequestParam Long todoId){
        todoRepository.deleteById(todoId);
        return "success";
    }

    @GetMapping("/edit")
    String editForm(Model model, @RequestParam Long todoId){
        Optional<Todo> todoOptional = todoRepository.findById(todoId);
        if(todoOptional.isPresent()){
            Todo todo = todoOptional.get();
            model.addAttribute("todo",todo);
            return "todoedit";
        }else{
            return "failure";
        }
    }

    @PostMapping("/edit")
    String edit(Todo todo){
        todoRepository.save(todo);
        return "success";
    }
}
