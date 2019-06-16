package com.he.community.controller;

import com.he.community.controller.dto.AccessTokenDTO;
import com.he.community.controller.dto.GithubUser;
import com.he.community.controller.mapper.UserMapper;
import com.he.community.controller.provider.GithubProvider;
import com.he.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubprovider;

    @Value("${github.client.id}")
    private String ClientId;
    @Value("${github.client.secret}")
    private String ClientSecret;
    @Value("${github.redirect.uri}")
    private String RedirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(ClientId);
        accessTokenDTO.setClient_secret(ClientSecret);
        accessTokenDTO.setRedirect_uri(RedirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubprovider.getAccessToken(accessTokenDTO);
        GithubUser  githubuser = githubprovider.getUser(accessToken);
        if(githubuser != null){
            System.out.println(githubuser.getName());
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubuser.getName());
            user.setAcountId(String.valueOf(githubuser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            userMapper.insert(user);
            /*登录成功，写Cookie以及Session*/
            request.getSession().setAttribute("githubuser", githubuser);
            return "redirect:/";
        }else{
            /*登录失败，重新登录*/
            return "redirect:/";
        }
/*        return "index";*/
    }

}
