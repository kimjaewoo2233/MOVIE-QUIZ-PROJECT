package com.movie.back.service.impl;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.LikeGood;
import com.movie.back.entity.Member;
import com.movie.back.repository.BoxOfficeRepository;
import com.movie.back.repository.LikeRepository;
import com.movie.back.repository.MemberRepository;
import com.movie.back.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeServiceImpl implements LikeService {


        private final LikeRepository likeRepository;


        public void saveLike(String email,String title){

            likeRepository.save(LikeGood.builder()
                            .movieTitle(title)
                            .email(email)
                    .build());

            log.info("{} 가 {} 에 좋아요를 눌렀음",email,title);
        }

        public boolean existByLike(String email,String title){  //true이면 delete 버튼이 보이게 하면 될 듯함
                  return likeRepository.exists(BoxOffice.builder().title(title).build()
                        , Member.builder().email(email).build());
        }

        @Override
        public void deleteLike(String email, String title) {
            boolean exist = likeRepository.likeElement(BoxOffice.builder()
                            .title(title).build(), Member.builder().email(email).build())
                    .isPresent();

            if(exist){
                likeRepository.deleteById(likeRepository.likeElement(BoxOffice.builder()
                        .title(title).build(), Member.builder().email(email).build()).get().getId());
            }
        }

}
