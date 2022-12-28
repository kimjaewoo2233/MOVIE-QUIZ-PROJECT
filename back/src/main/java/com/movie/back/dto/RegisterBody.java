package com.movie.back.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterBody {
    private String email;

    private String password;

    private String birth;

    private String gender;

    private String role;

    public  MemberDTO toMemberDTO(RegisterBody registerBody){
        Set<String> roleTypes = Set.of(getRole());
        return new MemberDTO(this.email, this.password,
                roleTypes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toUnmodifiableSet()),
                this.birth, this.getGender(),null);
    }
}
