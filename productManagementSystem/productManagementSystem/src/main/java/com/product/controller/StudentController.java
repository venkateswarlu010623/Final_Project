package com.product.controller;

import com.product.modal.Student;
import com.product.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("create/student")
    public Student createProduct(@RequestBody Student student)
    {
        return studentService.saveStudent(student);
    }

}
