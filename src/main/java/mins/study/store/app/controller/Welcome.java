package mins.study.store.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Welcome {

    @RequestMapping("/")
    public String home() {

        return "welcome";
    }
}
