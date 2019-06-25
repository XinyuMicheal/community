package com.he.community.controller;

import com.he.community.dto.QuestionDTO;
import com.he.community.mapper.QuestionMapper;
import com.he.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        /*System.out.println(id);*/
        /*阅读数*/
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getById(id);


        model.addAttribute("question",questionDTO);
        return "question";
    }
}
