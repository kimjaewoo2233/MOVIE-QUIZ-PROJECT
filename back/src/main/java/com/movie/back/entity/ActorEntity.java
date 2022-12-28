package com.movie.back.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ActorEntity {

    @Id
    private String actorName;

    private String actorRole;

    @ManyToOne
    @JoinColumn(name = "boxOffice_id")
    @ToString.Exclude
    private BoxOffice boxOfficeId;
}
