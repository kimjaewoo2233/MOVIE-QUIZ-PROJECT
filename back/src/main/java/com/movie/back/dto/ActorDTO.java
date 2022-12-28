package com.movie.back.dto;


import com.movie.back.entity.BoxOffice;
import lombok.*;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorDTO {

    private String actorName;

    private String actorRole;


}
