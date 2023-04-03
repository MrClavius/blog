package pl.chlopickipiotr.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.chlopickipiotr.blog.dto.SignUpForm;
import pl.chlopickipiotr.blog.jpa.model.RoleEntity;
import pl.chlopickipiotr.blog.jpa.model.UserEntity;
import pl.chlopickipiotr.blog.jpa.repository.RoleRepository;
import pl.chlopickipiotr.blog.jpa.repository.UserRepository;
import pl.chlopickipiotr.blog.utils.Roles;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignUpForm signUpForm) {
        UserEntity user = UserEntity.builder()
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        if (signUpForm.getIsAdmin()) {
            setRole(user, Roles.ADMIN);
        }
        setRole(user, Roles.USER);
        userRepository.save(user);
    }

    private void setRole(UserEntity user, Roles roleName) {
        RoleEntity roleEntity = roleRepository
                .findByRoleName(roleName.getRoleName())
                .orElseGet(() -> roleRepository
                        .save(RoleEntity.builder().roleName(roleName.getRoleName()).build()));
        user.addRoleToUser(roleEntity);

    }
}
