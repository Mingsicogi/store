package mins.study.store.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data", "Hello Thymeleaf");

        return "hello";
    }
}
