package com.movie.back.service;


import com.movie.back.data.cdata.Actor;
import com.movie.back.data.cdata.MovieCode;
import com.movie.back.dto.ActorDTO;
import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.dto.SearchMovieData;
import com.movie.back.entity.ActorEntity;
import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.BoxStillImage;
import com.movie.back.entity.MovieRating;
import com.movie.back.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.io.IOException;
import java.nio.DoubleBuffer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoxOfficeService {

    private final BoxOfficeRepository boxOfficeRepository;
    private final BoxStillImageRepository boxStillImageRepository;
    private final ActorRepository actorRepository;

    private final LikeRepository likeRepository;

    private final RatingRepository ratingRepository;
    private final ScrapperService scrapperService;

    public void saveBoxMovie() throws IOException, KobisScrapper.NotScrappedDateException {
        for (BoxOfficeDTO boxOfficeDTO : scrapperService.latestBoxOffice()) {
            BoxOffice boxOffice = BoxOffice.builder()
                    .title(boxOfficeDTO.getTitle())
                    .ranking(boxOfficeDTO.getRank())
                    .synopsis(boxOfficeDTO.getSynopsis())
                    .date(boxOfficeDTO.getDate())
                    .posterLink(boxOfficeDTO.getPostLink())
                    .build();
            boxOfficeRepository.save(boxOffice);    //연관관계 없는 것들 먼저 저장한다.

            boxOfficeDTO.getStillImage().forEach(image ->{  //불러온 이미지들을 위에 저장한 정보에 연관관계로 저장
                boxStillImageRepository.save(BoxStillImage.builder()
                        .boxOfficeId(boxOffice)
                        .imageLink(image)
                        .build());
            });
            boxOfficeDTO.getActorList().forEach(actor -> {
                actorRepository.save(ActorEntity.builder()
                                .actorName(actor.getActorName())
                                .actorRole(actor.getActorRole())
                                .boxOfficeId(boxOffice)
                        .build());
            });
        }
    }

    @Transactional(readOnly = true)
    public List<BoxOfficeDTO> getBoxList(){
            List<BoxOfficeDTO> dtoList = new ArrayList<>();


            boxOfficeRepository.getBoxOfficeList().forEach(boxOffice -> {


                dtoList.add(BoxOfficeDTO.builder()
                                .title(boxOffice.getTitle())
                                .rank(boxOffice.getRanking())
                                .postLink(boxOffice.getPosterLink())
                                .date(boxOffice.getDate())
                                .synopsis(boxOffice.getSynopsis())
                        .build());

            });
            return dtoList;

    }

    public BoxOfficeDTO getReadMovie(String title){
            BoxOffice boxOffice = boxOfficeRepository.getMovieRead(title);
            List<Double> ratings = ratingRepository.getRatingByTitle(title);
            double resultRating = 0.0;
            for(double rating : ratings){
                resultRating += rating;
            }

            resultRating = resultRating/ratings.size();

        List<String> resultStill = null;
            List<String> still = boxOffice
                    .getStillImage().stream().map(boxStillImage -> boxStillImage.getImageLink())
                    .collect(Collectors.toList());
            if(!still.isEmpty()){
                resultStill = still.subList(0, still.size());
            }
        return BoxOfficeDTO.builder()
                .title(boxOffice.getTitle())
                .date(boxOffice.getDate())
                .synopsis(boxOffice.getSynopsis())
                .stillImage(resultStill)
                .postLink(boxOffice.getPosterLink())
                .rating(resultRating)
                .rank(boxOffice.getRanking())
                .actorList(boxOffice.getActorList().stream().map(actorEntity -> ActorDTO.builder()
                        .actorName(actorEntity.getActorName())
                        .actorRole(actorEntity.getActorRole())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void saveSearchBoxOffice(int startYear,int endYear){ //크롤링한 검색을 위한 데이터들을 저장하는 곳이다.
        IntStream.rangeClosed(1,34).forEach(page -> {
            MovieCode[] movieCodes = new MovieCode[0];
            String synopsis = "";
            String posterLink = "";
           try{
               movieCodes =KobisScrapper.searchUserMovCdList(startYear,endYear,page);
           }catch (NullPointerException nullPointerException){
               log.info("페이지를 불러오는데 실패함");
           }

            for(MovieCode movie : movieCodes){
                try {
                    synopsis =  Optional.of(KobisScrapper.getSynopsisByCode(movie.getCode())).orElse("없음");
                    posterLink = KobisScrapper.getMainPosterByCode(movie.getCode());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                BoxOffice boxOffice = BoxOffice.builder().title(movie.getTitle()).posterLink(posterLink).synopsis(synopsis).build();
                boxOfficeRepository.save(boxOffice);//날짜


                try {
                    String[] stills = KobisScrapper
                            .getImageUrlsByCode(movie.getCode(), KobisScrapper.ImageType.STILL_CUT, true);
                          for(int i=0; i< stills.length;i++){
                              boxStillImageRepository.save(BoxStillImage.builder().imageLink(stills[i]).boxOfficeId(boxOffice).build());
                              if(i > 8) break;  //10개까지만 받음
                          }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    KobisScrapper.getActorList(movie.getCode()).forEach(actor -> {
                        actorRepository.save(ActorEntity.builder().boxOfficeId(boxOffice).actorName(actor.getActorName()).actorRole(actor.getCharacterName()).build());
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        });
    }

    public BoxOfficeDTO getSerachMovie(String title){   //자세히보기
           BoxOffice boxOffice =boxOfficeRepository.getSerachMovie(title);

             if(boxOffice != null){
                 return   BoxOfficeDTO.builder().title(boxOffice.getTitle())
                         .postLink(boxOffice.getPosterLink())
                         .stillImage(boxOffice.getStillImage().stream().map(boxStillImage -> boxStillImage.getImageLink()).collect(Collectors.toList()))
                         .synopsis(boxOffice.getSynopsis())
                         .actorList(boxOffice.getActorList().stream().map(actorEntity -> ActorDTO.builder().actorRole(actorEntity.getActorRole())
                                   .actorName(actorEntity.getActorName()).build()).collect(Collectors.toList())).build();
            }else{
                System.out.println("없는 값을 입력함");
                return BoxOfficeDTO.builder().build();
            }

    }

    @Transactional
    public SearchMovieData getSearchMovieList(String title,int page){ //제목이 있으면 그거와 비슷한 요소들 없으면 전체

        List<BoxOfficeDTO> dtoList = new ArrayList<>();
        Page<BoxOffice> boxOffices;
        if(title != null){
            boxOffices = boxOfficeRepository.getMovieList(title, PageRequest.of(page,10));

            boxOffices.forEach(boxOffice -> {
                dtoList.add(BoxOfficeDTO.builder()
                        .title(boxOffice.getTitle())
                        .synopsis(boxOffice.getSynopsis())
                        .postLink(boxOffice.getPosterLink())
                        .actorList(boxOffice.getActorList().stream().map(actorEntity ->
                                ActorDTO.builder().actorName(actorEntity.getActorName())
                                        .actorRole(actorEntity.getActorRole()).build()).collect(Collectors.toList()))
                        .build());
            });
        }else{
            boxOffices = boxOfficeRepository.findAll(PageRequest.of(page,10));

            boxOffices.stream().collect(Collectors.toList()).forEach(boxOffice -> {
                dtoList.add(BoxOfficeDTO.builder()
                        .title(boxOffice.getTitle())
                        .synopsis(boxOffice.getSynopsis())
                        .postLink(boxOffice.getPosterLink())
                        .actorList(boxOffice.getActorList().stream().map(actorEntity ->
                                ActorDTO.builder().actorName(actorEntity.getActorName())
                                        .actorRole(actorEntity.getActorRole()).build()).collect(Collectors.toList()))
                        .build());
            });
        }

        SearchMovieData searchMovieData = SearchMovieData.builder()
                .items(dtoList)
                .totalPage(boxOffices.getTotalPages())
                .build();
        return searchMovieData;
    }

    public List<BoxOfficeDTO> likeOrderByAgeGroup(String ageGroup){
        Set<BoxOfficeDTO> set = new HashSet<>();
       // //TODO: 열개로 할 것인가 말 것인가 그것이 고민이다 최대 10개

        likeRepository.likeGoodAgeGroup(ageGroup).forEach(likeGood -> {
            set.add(BoxOfficeDTO.builder()
                    .title(likeGood.getBoxOffice().getTitle())
                    .postLink(likeGood.getBoxOffice().getPosterLink())
                    .build());
        });
        return new ArrayList<>(set)
                .subList(0,Math.min(set.size(),10));
    }

    public Long setMovieRating(String title,String email,double rating){
        MovieRating existRating = ratingRepository.getRating(email,title);
        if(existRating != null){
            existRating.setRating(rating);
              return ratingRepository.save(existRating).getId();
        }else{
            return ratingRepository.save(MovieRating.builder()
                    .email(email)
                    .movieTitle(title)
                    .rating(rating)
                    .build()).getId();
        }
    }

    public List<BoxOfficeDTO> getListOrderBy(){
        List<BoxOfficeDTO> dtoList = new ArrayList<>();

        boxOfficeRepository.getLikeList(PageRequest.of(0,10)).forEach(boxOffice -> {
                dtoList.add(BoxOfficeDTO.builder()
                        .title(boxOffice.getTitle())
                                .postLink(boxOffice.getPosterLink())
                        .build());
        });
        return dtoList;
    }
}
