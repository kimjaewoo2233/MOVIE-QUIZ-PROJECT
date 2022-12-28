package com.movie.back.entity;


import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class BoxOffice {

    @Id
    private String title;

    private Integer ranking;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    private String posterLink;

    private String date;


    @OneToMany(mappedBy = "boxOfficeId")
    @Builder.Default
    private List<BoxStillImage> stillImage =new ArrayList<>();
    //단방향이어도 되지만 FK는 상대 테이블이 가지게 되어있기때문에 그냥 양방향함


    @BatchSize(size = 200)
    @OneToMany(mappedBy = "boxOfficeId")
    @Builder.Default
    private List<ActorEntity> actorList = new ArrayList<>();

    @BatchSize(size = 200)
    @OneToMany(mappedBy = "boxOffice")
    @Builder.Default
    private Set<LikeGood> likeGoods = new HashSet<>();


    @OneToMany(mappedBy = "boxOffice",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Quiz> quizList = new ArrayList<>();

    @OneToMany(mappedBy = "boxOffice",orphanRemoval = true
            ,cascade = CascadeType.ALL)
    @Builder.Default
    private List<MovieRating> ratings = new ArrayList<>();

    public void addRating(MovieRating rating){
            ratings.add(rating);
    }

}
