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

    @ResponseBody
    @PostMapping("/checkphone")//通过ajax检查当前电话是否存在
    public String checkphone( @RequestParam("tel")String tel){
        List<Todo> todos = todoRepository.findAll();

        System.out.println("控制器端接收到的tel="+tel);

        String result="";
        for (Todo each:todos
        ) {
            System.out.println(each.getTel());
            if(each.getTel().equals(tel)){
                result="success";
                break;
            }
        }
        System.out.println("控制器端result="+result);
        return result;//否则返回"error"
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
