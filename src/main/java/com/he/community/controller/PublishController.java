package com.he.community.controller;

import com.he.community.dto.QuestionDTO;
import com.he.community.mapper.QuestionMapper;
import com.he.community.model.Question;
import com.he.community.model.User;
import com.he.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String Edit(@PathVariable(name = "id") Integer id,
                       Model model){
        /*@PathVariable用于将请求URL中的模板变量（URL路径部分）映射到功能处理方法的参数上。*/
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }

    /*@PostMapping 用于将HTTP POST请求映射到特定处理程序方法的注释。
    * @PostMapping是一个组合注解，是@RequestMapping(method = RequestMethod.POST)的缩写。*/
    @PostMapping("/publish")
    public String  doPublish(@RequestParam(value = "title",required = false)String title,
                             @RequestParam(value ="description",required = false)String description,
                             @RequestParam(value ="tag",required = false)String tag,
                             @RequestParam(value ="id",required = false)Integer id,
                             HttpServletRequest request,
                             Model model)
    {
        /*通过注解@RequestParam可以轻松地将URL中的参数部分（非路径部分）绑定到处理函数方法的变量中：*/
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();

        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
