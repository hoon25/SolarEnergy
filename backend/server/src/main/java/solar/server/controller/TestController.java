package solar.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {



    @GetMapping("/test")
    public String path(){

        String path = System.getProperty("user.dir");
        System.out.println("Working Directory = " + path);

        return path;
    }
}
