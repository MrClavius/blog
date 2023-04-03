package pl.chlopickipiotr.blog.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name="AUSER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @ManyToMany
    @JoinTable(name="USER_ROLE")
    private Set<RoleEntity> roleSet;
    @OneToMany(mappedBy = "author")
    private List<PostEntity> postList;

    public void addRoleToUser(RoleEntity roleEntity){
        if (roleSet == null){
            roleSet = new HashSet<>();
        }
        roleSet.add(roleEntity);
    }
}
