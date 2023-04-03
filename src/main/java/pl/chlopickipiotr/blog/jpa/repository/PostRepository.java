package pl.chlopickipiotr.blog.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.chlopickipiotr.blog.jpa.model.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
