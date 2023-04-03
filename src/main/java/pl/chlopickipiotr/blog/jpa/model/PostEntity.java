package pl.chlopickipiotr.blog.jpa.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "POST")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 140)
    private String title;
    private String content;
    private LocalDateTime addedTime;
    @ManyToOne
    private UserEntity author;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CommentEntity> commentList;

    public void addComment (CommentEntity commentEntity){
        if (commentList == null){
            commentList = new ArrayList<>();
        }
        commentList.add(commentEntity);
    }

}
