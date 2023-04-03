package pl.chlopickipiotr.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Roles {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String roleName;
}
