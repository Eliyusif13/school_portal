package com.sadiqov.entity.school_portal.controller;

import com.sadiqov.entity.school_portal.servise.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schoolPortal")
public class ControllerClass {
    @Autowired
    StudentService serviceClass;

}
