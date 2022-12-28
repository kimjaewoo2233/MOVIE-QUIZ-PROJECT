package com.movie.back.dto;


import com.movie.back.entity.BoxOffice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxStillImageDTO {

     private String stileImage;
}
