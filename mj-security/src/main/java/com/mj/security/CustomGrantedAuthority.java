package com.mj.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author anyang
 * @CreateTime 2023/1/4
 * @Des
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;
}
