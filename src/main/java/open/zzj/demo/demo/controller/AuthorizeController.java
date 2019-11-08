package open.zzj.demo.demo.controller;


import open.zzj.demo.demo.dto.AccessTokenDTO;
import open.zzj.demo.demo.dto.GithubUser;
import open.zzj.demo.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://127.0.0.1:8080/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("99c9d3cce8bc3668ba98");
        accessTokenDTO.setClient_secret("271abcb5f8583d103e0599b32b4f111e9686fd47");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }

}
