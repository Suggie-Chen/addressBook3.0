package cn.edu.bupt.todolist_4.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    private String content;//
    private String name;
    private String tel;
    private String email;
    private String QQ;
    private String addr;
}
