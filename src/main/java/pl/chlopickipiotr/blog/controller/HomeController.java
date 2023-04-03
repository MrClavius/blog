package pl.chlopickipiotr.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.chlopickipiotr.blog.dto.CommentForm;
import pl.chlopickipiotr.blog.service.PostService;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {

    private final PostService postService;

    @GetMapping(value = {"/", "/index", "/home"})
    public String getHome(Model model) {
        model.addAttribute("postlist", postService.getAllPosts());
        model.addAttribute("commentForm", new CommentForm());
        return "home";
    }
}
