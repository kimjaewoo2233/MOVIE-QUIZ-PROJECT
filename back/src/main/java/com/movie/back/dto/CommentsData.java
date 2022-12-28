package com.movie.back.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CommentsData {
        private int totalPage;
        private List<MovieCommentsDTO> dtoList;
}
