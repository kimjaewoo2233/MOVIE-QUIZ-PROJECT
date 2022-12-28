package com.movie.back.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boxoffice_id",insertable = false,updatable = false)
    @ToString.Exclude
    private BoxOffice boxOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "email",insertable = false,updatable = false)
    @ToString.Exclude
    private Member member;

    @Column(name = "boxoffice_id")
    private String movieTitle;      //위에거랑 맞춰주면 굳이 영속성으로 등록 안해도 문자열로 저장가능

    @Column(name ="email")
    private String email;


    private String title;


    @Builder.Default
    @OneToMany(mappedBy ="quiz", cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<QuizItems> quizItems = new HashSet<>();


    public void addQuizItem(String item,String key,boolean correct){
        quizItems.add(
                QuizItems.builder()
                        .itemTitle(item)
                         .correct(correct)
                        .keyNumber(key)
                        .quiz(this)
                .build());
    }

}
