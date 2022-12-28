package com.movie.back.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {

        private String email;
        private String password;
        private String birth;
        private String gender;
        private String nickName;
}
