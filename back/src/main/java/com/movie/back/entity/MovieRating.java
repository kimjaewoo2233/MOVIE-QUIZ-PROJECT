package com.movie.back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "email",insertable = false,updatable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "boxoffcie_id",insertable = false,updatable = false)
    private BoxOffice boxOffice;

    @Column(name = "email")
    private String email;

    @Column(name = "boxoffcie_id")
    private String movieTitle;

    public void setRating(double rating){
        this.rating = rating;
    }

}
