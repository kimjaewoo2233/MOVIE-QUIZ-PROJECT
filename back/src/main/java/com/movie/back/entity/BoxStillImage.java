package com.movie.back.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class BoxStillImage {

    @Id
    private String imageLink;

    @ManyToOne
    @JoinColumn(name = "boxOffice_id")
    @ToString.Exclude
    private BoxOffice boxOfficeId;
}
