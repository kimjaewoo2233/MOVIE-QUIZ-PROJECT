package com.movie.back.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class LikeGood {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_id",insertable = false,updatable = false)
        @ToString.Exclude
        private BoxOffice boxOffice;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "email",insertable = false,updatable = false)
        @ToString.Exclude
        private Member member;


        @Column(name = "movie_id")
        private String movieTitle;
        @Column(name = "email")
        private String email;

}
