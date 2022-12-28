package com.movie.back.security;

import com.movie.back.dto.MemberDTO;
import com.movie.back.entity.Member;
import com.movie.back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.getMemberInfo(username).orElseThrow(() -> new UsernameNotFoundException("없는 이메일임"));

        MemberDTO dto = new MemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getRoleSet().stream().map(memberRole ->
                        new SimpleGrantedAuthority("ROLE_"+memberRole.name()))
                        .collect(Collectors.toList()),
                member.getBirth(),
                member.getGender(),
                member.getAgeGroup()
            );
        return dto;
    }
}
