package com.movie.back.data.cdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

public class BoxOfficeData {
    /**
     * 박스오피스 순위
     */
    private final int rank;

    /**
     * 영화 제목
     */
    private final String title;

    /**
     * 영화정보통합관리 표준코드(FIMS코드)
     */
    private final int code;

    private final LocalDate date;

    public BoxOfficeData(int rank, String title, int code, LocalDate date) {
        this.rank = rank;
        this.title = title;
        this.code = code;
        this.date = date;
    }

    public int getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public int getCode() {
        return code;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "BoxOfficeData{" +
                "rank=" + rank +
                ", title='" + title + '\'' +
                ", code=" + code +
                ", date=" + date +
                '}';
    }

}
