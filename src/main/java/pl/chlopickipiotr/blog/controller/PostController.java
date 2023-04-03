package pl.chlopickipiotr.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.chlopickipiotr.blog.dto.CommentForm;
import pl.chlopickipiotr.blog.dto.PostForm;
import pl.chlopickipiotr.blog.dto.SignUpForm;
import pl.chlopickipiotr.blog.service.PostService;

@RequiredArgsConstructor
@Controller
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "form";
    }

    @PostMapping("/form")
    public String sendingPostToDB(Model model, @ModelAttribute PostForm postForm, BindingResult bindingResult) {
        model.addAttribute("postForm", new PostForm());
        log.info("postForm: {}", postForm);
        if (bindingResult.hasErrors()) return "form";
        postService.addPostToDB(postForm);
        return "redirect:/home";
    }

    @PostMapping("/delete-post/{id}")
    public String deletingPost(@PathVariable Long id) {
        log.info("post.id: {}", id);
        postService.deletePostFromDB(id);
        return "redirect:/home";
    }

    @PostMapping("/delete-comment/{postId}/{commentId}")
    public String deletingComment(@PathVariable Long postId, @PathVariable Long commentId) {
        log.info("comment.id: {}, post.id: {}", postId, commentId);
        postService.deleteCommentFromDB(postId, commentId);
        return "redirect:/home";
    }

    @PostMapping("/add-comment/{postId}")
    public String addingComment(@ModelAttribute CommentForm commentForm, @PathVariable Long postId) {
        log.info("comment.form: {}, post.id: {}", commentForm, postId);
        postService.addCommentToPost(postId, commentForm);
        return "redirect:/home";
    }

}
