package com.movie.back.service;

import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.dto.MemberDTO;


public interface LikeService {

    public void saveLike(String email, String title);


    public boolean existByLike(String email,String title);


    public void deleteLike(String email,String title);

}
