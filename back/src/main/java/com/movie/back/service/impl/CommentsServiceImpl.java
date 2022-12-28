package com.movie.back.service.impl;

import com.movie.back.dto.CommentsData;
import com.movie.back.dto.MovieCommentsDTO;
import com.movie.back.entity.MovieComments;
import com.movie.back.repository.MovieCommentRepository;
import com.movie.back.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
        private final MovieCommentRepository movieCommentRepository;

        public CommentsData getDTOList(int page,String title){
            List<MovieCommentsDTO> dtoList = new ArrayList<>();
            Page<MovieComments> pageList =movieCommentRepository
                    .findAllWithRating(PageRequest.of(page,10),title);

            pageList.forEach(
                    movieComments -> {
                        dtoList.add(MovieCommentsDTO.builder()
                                        .id(movieComments.getId())
                                        .email(movieComments.getEmail())
                                        .movieTitle(movieComments.getMovieTitle())
                                        .content(movieComments.getContent())
                                        .rating(movieComments.getRating().getRating().toString())
                                        .spoiler(movieComments.isSpoiler())
                                        .blind(movieComments.isBlind())
                                        .createdAt(movieComments.getCreatedAt())
                                .build());
                    }
            );

            return CommentsData.builder().dtoList(dtoList).totalPage(pageList.getTotalPages()).build();
        }

        @Override
        public void saveComments(MovieCommentsDTO dto,Long id) {
              //평점은 한번만 매길 수 있고 댓글은 여러번 달 수 있도록 수정함
            MovieComments comments =    MovieComments.builder()
                    .content(dto.getContent())
                    .spoiler(dto.isSpoiler())
                    .movieTitle(dto.getMovieTitle())
                    .email(dto.getEmail())
                    .ratingId(id)
                    .build();
            comments.blindNumberInit();
            movieCommentRepository.save(
                    comments
                );

        }


        public boolean modifyComment(MovieCommentsDTO dto){

            MovieComments comments = movieCommentRepository.findById(dto.getId()).get();

            if(dto.getEmail().equals(comments.getEmail())){
                comments.changeComments(dto.getContent(),dto.isSpoiler());
                movieCommentRepository.save(comments);
                return  true;
            }else{
                return false;
            }
        }

    @Override
    public boolean deleteCom(Long id,String email) {
        MovieComments movieComments = movieCommentRepository.findById(id).orElseThrow(RuntimeException::new);
        if(movieComments.getEmail().equals(email)){
            movieCommentRepository.delete(movieComments);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean blindPlus(Long id) {
            movieCommentRepository.findById(id).ifPresent(MovieComments::addBlindNumber);
        return true;
    }


    public boolean blindNumberAdd(Long id){
            MovieComments movieComments =
                    movieCommentRepository.findById(id).orElseThrow(RuntimeException::new);

            if(movieComments.addBlindNumber() > 3){
                movieComments.blindProcessing();
            };

            movieCommentRepository.save(movieComments);

            return true;
        }


}
