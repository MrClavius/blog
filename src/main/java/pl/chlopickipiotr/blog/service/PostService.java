package pl.chlopickipiotr.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.chlopickipiotr.blog.dto.CommentForm;
import pl.chlopickipiotr.blog.dto.PostForm;
import pl.chlopickipiotr.blog.jpa.model.CommentEntity;
import pl.chlopickipiotr.blog.jpa.model.PostEntity;
import pl.chlopickipiotr.blog.jpa.repository.CommentRepository;
import pl.chlopickipiotr.blog.jpa.repository.PostRepository;
import pl.chlopickipiotr.blog.jpa.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    @PostConstruct
    public void testInit() {

        CommentEntity commentEntity = CommentEntity.builder()
                .content("test comment")
                .addedTime(LocalDateTime.now())
                .build();
        PostEntity postEntity = PostEntity.builder()
                .title("Test")
                .content("test test test")
                .commentList(new ArrayList<>(List.of(commentEntity)))
                .addedTime(LocalDateTime.now())
                .build();

        CommentEntity commentEntity1 = CommentEntity.builder()
                .content("test comment 2 test test")
                .addedTime(LocalDateTime.now())
                .build();

        PostEntity postEntity2 = PostEntity.builder()
                .title("Post nr2")
                .content("test test test")
                .addedTime(LocalDateTime.now())
                .build();

        postEntity.getCommentList().add(commentEntity1);
        postRepository.saveAll(List.of(postEntity, postEntity2));


    }

    public void addPostToDB(PostForm postForm) {
        PostEntity postEntity = PostEntity.builder()
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .addedTime(LocalDateTime.now())
                .build();

        postRepository.save(postEntity);
    }

    public void deletePostFromDB(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void deleteCommentFromDB(Long postId, Long commentId) {
        PostEntity byId = postRepository.findById(postId).get();
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        byId.getCommentList().remove(commentEntity);
    }

    @Transactional
    public void addCommentToPost(Long postId, CommentForm commentForm) {
        PostEntity byId = postRepository.findById(postId).get();
        CommentEntity commentEntity = CommentEntity.builder()
                .content(commentForm.getContent())
                .addedTime(LocalDateTime.now())
                .build();
        byId.addComment(commentEntity);
    }
}
