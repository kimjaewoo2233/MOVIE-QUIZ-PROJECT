package com.movie.back.data.cdata;

import java.util.Objects;

public  class MovieCode {
    private String title;
    private int code;

    public MovieCode(String title, int code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public int getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCode movieCode = (MovieCode) o;
        return title.equals(movieCode.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "MovieCode{" +
                "title='" + title + '\'' +
                ", code=" + code +
                '}';
    }
}