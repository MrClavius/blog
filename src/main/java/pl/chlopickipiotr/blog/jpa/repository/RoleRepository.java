package pl.chlopickipiotr.blog.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.chlopickipiotr.blog.jpa.model.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

     Optional<RoleEntity> findByRoleName(String roleName);
}
