package com.movie.back.service;

import com.movie.back.dto.CommentsData;
import com.movie.back.dto.MovieCommentsDTO;

import java.util.List;

public interface CommentsService {
    public CommentsData getDTOList(int page,String title);

    public void saveComments(MovieCommentsDTO dto,Long id) ;

    public boolean modifyComment(MovieCommentsDTO dto);

    public boolean deleteCom(Long id,String email);

    public boolean blindPlus(Long id);
    public boolean blindNumberAdd(Long id);
}
