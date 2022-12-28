package com.movie.back.dto;

import com.movie.back.entity.Member;
import com.movie.back.entity.MemberMovie;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private String email;

    private String password;

    private String birth;

    private String gender;

    private Set<MemberRole> role;



    private List<MemberMovieDTO> memberMovieList;

    @Setter(AccessLevel.NONE)
    private String ageGroup;

    public void setAgeGroup(String birth){
            String year = birth.substring(0,4);

            Integer temp = ((2022 - Integer.parseInt(year))/10)*10;
            this.ageGroup = temp.toString();
    }


    public MemberDTO(String username, String password,Collection<GrantedAuthority> authorities,String birth,String gender,String ageGroup){
        super(username,password,authorities);
        this.email = username;
        this.password = password;
        this.birth = birth;
        this.gender = gender;
        this.role = Set.of(MemberRole.USER);
        this.ageGroup = ageGroup;
    }


    public static Member toEntity(MemberDTO memberDTO){
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .gender(memberDTO.getGender())
                .roleSet(memberDTO.getRole())
                .birth(memberDTO.getBirth())
                .ageGroup(memberDTO.getAgeGroup())
                .build();

        return member ;
    }

    //toDTO를 통해 바꾼 값만 MemberDTO가 그 값을 가지고 있음 !!
    public static MemberDTO toDTO(Member member){   //member 엔티티는 권한 빼고 모든 값이 채워지면 됨 권한은 알아서 넘김
            //    Set<String> roleTypes = Set.of(member.getRole());   //dto는 User를 상속하기에 authorities 변수가 위에 있음

                return new MemberDTO(member.getEmail(), member.getPassword(),
                        member.getRoleSet().stream().map(memberRole ->
                                new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toUnmodifiableSet()),
                        member.getBirth(), member.getGender(),member.getAgeGroup());

    }
}
