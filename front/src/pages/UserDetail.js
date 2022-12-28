import { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import { getAccessToken } from "../storage/Cookie";
import { newAccessToken } from "../module/refreshToken";
import { FaHeart } from "react-icons/fa";
import ReviewCard from "../components/ReviewCard";

const UserDetail = () => {
    const [userInfo, setUserInfo] = useState({});
    const userInfoConfig = {
        method: "get",
        url: "/user/detail",
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    useEffect(() => {
        axios(userInfoConfig)
            .then((res) => setUserInfo(res.data))
            .catch((err) => {
                newAccessToken(err);
                console.log(err);
            });
    }, []);

    const handleDeleteMyMovie = (title) => {
        const removeConfig = {
            method: "delete",
            url: `/my/remove?title=${title}`,
            headers: {
                Authorization: `Bearer ${getAccessToken()}`,
            },
        };

        axios(removeConfig)
            .then((res) => {
                window.location.reload();
                console.log(res)
            })
            .catch((err) => console.log(err));
    };

    console.log(userInfo);
    return (
        <DetailWrapper>
            <DetailContent>
                <UserFix>
                    <p>수정하기</p>
                </UserFix>
                <UserTitle>
                    <UserEmail>{userInfo.email} 님 어서오세요!</UserEmail>
                    <UserBirth>
                        {userInfo.birth} | {userInfo.gender}
                    </UserBirth>
                </UserTitle>
                <UserLikeMovie>
                    <UserLikeTitle>내가 지금까지 찜한 영화</UserLikeTitle>
                    <LikeMovieList>
                        {userInfo?.myMovieDataList?.map((props, idx) => (
                            <MovieCard key={idx}>
                                <img src={props.posterLink} alt={props.title} />
                                <p>{props.title}</p>
                                <LikeHeart
                                    onClick={() =>
                                        handleDeleteMyMovie(props.title)
                                    }
                                />
                            </MovieCard>
                        ))}
                    </LikeMovieList>
                </UserLikeMovie>
                <UserReview>
                    <UserLikeTitle>지금까지 남긴 리뷰 보기</UserLikeTitle>
                    <UserReviewCardWrapper>
                        {userInfo?.commentsDTOList?.map((props, idx) => (
                            <UserReviewCard key={idx}>
                                <ReviewCard
                                    content={props.content}
                                    rating={props.rating}
                                ></ReviewCard>
                            </UserReviewCard>
                        ))}
                    </UserReviewCardWrapper>
                </UserReview>
                <UserQuiz>
                    <UserLikeTitle>지금까지 만든 퀴즈</UserLikeTitle>
                    {userInfo?.quizDTOList?.map((props) => (
                        <>
                            <QuizTitle>
                                <h2>{props.movieTitle}</h2>
                                <p>삭제하기</p>
                            </QuizTitle>
                            <QuizDesc>{props.quizName}</QuizDesc>
                        </>
                    ))}
                </UserQuiz>
            </DetailContent>
        </DetailWrapper>
    );
};

export default UserDetail;

const DetailWrapper = styled.section`
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100vw;
    height: 100vh;
    margin-top: 2rem;
`;

const DetailContent = styled.div`
    width: 40%;
    height: 100%;
`;

const UserFix = styled.div`
    display: flex;
    align-items: center;
    justify-content: flex-end;
    width: 100%;

    p {
        color: #03af59;
        text-decoration: underline;
    }
`;

const UserTitle = styled.div`
    display: flex;
    align-items: flex-start;
    justify-content: center;
    flex-direction: column;
    width: 100%;
    height: 5rem;
`;

const UserEmail = styled.h1`
    color: #03af59;
    font-size: 1.3rem;
    font-weight: 600;
`;

const UserBirth = styled.p`
    color: white;
`;

const UserLikeMovie = styled.div`
    display: flex;
    align-items: flex-start;
    justify-content: center;
    flex-direction: column;
    width: 100%;
    margin-top: 2rem;
`;

const UserLikeTitle = styled.h1`
    width: 100%;
    color: #03af59;
    font-size: 1.4rem;
    font-weight: 600;
`;
const LikeMovieList = styled.div`
    display: flex;
    align-items: center;
    justify-content: flex-start;
    flex-wrap: wrap;
    width: 120%;
    margin-top: 2rem;
`;
const MovieCard = styled.div`
    display: flex;
    align-items: flex-start;
    justify-content: center;
    flex-direction: column;
    width: 9rem;
    height:18rem;
    margin-right: 1rem;

    img {
        width: 100%;
        height: 15rem;
        margin-bottom: 0.5rem;
    }

    p {
        color: white;
        margin-bottom: 0.5rem;
    }
`;

const LikeHeart = styled(FaHeart)`
    color: #e8ac4b;
`;

const UserReview = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    margin-top: 2rem;
`;

const UserReviewCardWrapper = styled.div`
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    margin-top: 2rem;
`;

const UserReviewCard = styled.div`
    width: 18rem;
`;

const UserQuiz = styled.div`
    display: flex;
    align-items: flex-start;
    flex-direction: column;
    justify-content: flex-start;
    width: 100%;
    margin-top: 2rem;
`;
const QuizTitle = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    margin-top: 2rem;

    h2 {
        color: white;
        font-size: 1.1rem;
    }
    p {
        color: #03af59;
        text-decoration: underline;
    }
`;

const QuizDesc = styled.h3`
    margin-top: 1rem;
    margin-left: 1rem;
    color: #f3f4f8;
`;
