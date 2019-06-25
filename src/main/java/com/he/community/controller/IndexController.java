package com.he.community.controller;
/**
 *  springvmc采用经典的三层分层控制结构，
 *  在持久层，业务层和控制层分别采用
 *  @Repository、@Service、@Controller对
 *  分层中的类进行注解，而@Component对那些比较中立的类进行注解
 */

import com.he.community.dto.PaginationDTO;
import com.he.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    /*@GetMapping 用于将HTTP GET请求映射到特定处理程序方法的注释，
    * @GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写。*/
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "4") Integer size){

        PaginationDTO pagination = questionService.list(page, size);
       /* List<Question> questionList = questionMapper.list();*/
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
