package com.movie.back.service;

import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.dto.MyMovieData;

import java.util.List;

public interface MovieMemberService {

    public void saveMovieMember(String email,String title);
    public MyMovieData getDtoList(String email, int page);
    public void deleteMyMovie(String email,String title);
    public boolean exists(String title,String email);
}
