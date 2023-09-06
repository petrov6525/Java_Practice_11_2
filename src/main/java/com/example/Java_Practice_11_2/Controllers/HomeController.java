package com.example.Java_Practice_11_2.Controllers;

import com.example.Java_Practice_11_2.repositories.BaseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
//        BaseRepository.createTables();
//        BaseRepository.fillTables();
        return "index";
    }
}
