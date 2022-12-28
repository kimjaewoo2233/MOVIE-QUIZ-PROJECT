package com.movie.back.service.impl;

import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.dto.MyMovieData;
import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.Member;
import com.movie.back.entity.MemberMovie;
import com.movie.back.repository.MemberMovieRepository;
import com.movie.back.service.MovieMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieMemberServiceImpl implements MovieMemberService {


    private final MemberMovieRepository memberMovieRepository;

    public void saveMovieMember(String email,String title){

            memberMovieRepository.save(MemberMovie.builder()
                            .email(email)
                            .title(title)
                    .build());
    }


    public MyMovieData getDtoList(String email, int page){

        List<BoxOfficeDTO> dtoList = new ArrayList<>();
        Page<BoxOffice> pageList = memberMovieRepository.memberMyMovie(email, PageRequest.of(page,10));
        //없을 경우 빈값이 넘아감 그래서 예외처리 안함

        pageList.forEach(boxOffice -> {
                dtoList.add(BoxOfficeDTO.builder().
                                title(boxOffice.getTitle())
                                .postLink(boxOffice.getPosterLink())
                                .synopsis(boxOffice.getSynopsis())
                        .build());
            });
        MyMovieData data = MyMovieData.builder()
                .totalPage(pageList.getTotalPages())
                .items(dtoList)
                .build();
        return  data;
    }


    @Transactional
    public void deleteMyMovie(String email,String title){
            memberMovieRepository.deleteMyMovie(title,email);
            log.info("{}가 찜한 {} 삭제 완료",email,title);
    }

    @Transactional(readOnly = true)
    public boolean exists(String title,String email){
          return  memberMovieRepository.existysMyMovie(title,email);
    }
}
