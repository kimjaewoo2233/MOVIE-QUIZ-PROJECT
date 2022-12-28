package com.movie.back.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MemberMovie { //찜한영화

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id",insertable = false,updatable = false)
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @JoinColumn(name="boxOffice_id",insertable = false,updatable = false)
    private BoxOffice boxOfficeId;  //이건 단방향 영화정보가 이 정보를 알 필요가 없음

    @Column(name = "member_id")
    private String email;

    @Column(name="boxOffice_id")
    private String title;       //이 값을 가져오면 join

    public void changeMember(Member member){
        this.member = member;
    }

}
