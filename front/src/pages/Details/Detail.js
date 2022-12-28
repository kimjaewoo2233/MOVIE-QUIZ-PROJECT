import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import styled from "styled-components";
import { useState, useEffect } from "react";
import { getAccessToken } from "../../storage/Cookie";
import axios from "axios";
import { newAccessToken } from "../../module/refreshToken";
import { AiOutlineHeart, AiFillHeart } from "react-icons/ai";
import ReviewBox from "../Review/ReviewBox";
import Video from "../../components/Video";

const Detail = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [movieData, setMovieData] = useState([]);
    const [isLike, setIsLike] = useState(false);
    const [myMovie, setMyMovie] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [writeReview, setWriteReview] = useState(false);
    const [preview, setPreview] = useState([]);
    const [userEmail, setUserEmail] = useState("");
    const movieTitle = location.state;

    const searchMovie = movieTitle + " 예고편";

    const URL = `https://www.googleapis.com/youtube/v3/search?q=${encodeURI(
        searchMovie
    )}&part=snippet&key=${process.env.REACT_APP_youtubeKey}&type=video&regionCode=KR&maxResults=3`;

    const readConfig = {
        method: "get",
        url: `/like/read?title=${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const emailConfig = {
        method: "get",
        url: "/user",
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    useEffect(() => {
        axios
            .get(`/mvi/read?title=${movieTitle}`)
            .then((res) => {
                setMovieData(res.data);
                axios(readConfig)
                    .then((res) => {
                        console.log(res);
                        setIsLike(res.data);
                    })
                    .catch((err) => {
                        newAccessToken(err);
                    });
            })
            .catch((err) => console.log(err));

        axios
            .get(URL)
            .then((res) => setPreview(res.data.items))
            .catch((err) => console.log(err));

        axios(emailConfig)
            .then((res) => {
                console.log(res);
                setUserEmail(res.data);
            })
            .catch((err) => console.log(err));

        axios
            .get(`/my/exists?title=${movieTitle}`,{
                headers:{
                    Authorization:`Bearer ${getAccessToken()}`
                }
            })
            .then((res) => setMyMovie(res.data))
            .catch((err) => console.log(err));
    }, []);

    const LikeConfig = {
        method: "post",
        url: `/like/${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const deleteLikeConfig = {
        method: "delete",
        url: `/like/delete?title=${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const myMovieConfig = {
        method: "post",
        url: `/my/save/${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const deleteMyMovieConfig = {
        method: "delete",
        url: `/my/remove?title=${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const handleIsOpen = () => {
        setIsOpen((prev) => !prev);
    };

    const handleLikeButton = () => {
        if (!isLike) {
            axios(LikeConfig)
                .then((res) => {
                    setIsLike(res.data);
                })
                .catch((err) => {
                    newAccessToken(err);
                    axios(LikeConfig)
                        .then((res) => {
                            setIsLike(res.data);
                        })
                        .catch((err) => console.log(err));
                });
        } else {
            axios(deleteLikeConfig)
                .then((res) => {
                    setIsLike(res.data);
                })
                .catch((err) => {
                    newAccessToken(err);
                    axios(deleteLikeConfig)
                        .then((res) => {
                            setIsLike(res.data);
                        })
                        .catch((err) => console.log(err));
                });
        }
    };

    //찜하기
    const handleMyMovieButton = () => {
        if (!myMovie) {
            axios(myMovieConfig)
                .then((res) => {
                    setMyMovie(res.data);
                })
                .catch((err) => {
                    newAccessToken(err);
                    axios(myMovieConfig)
                        .then((res) => {
                            setMyMovie(res.data);
                        })
                        .catch((err) => console.log(err));
                });
        } else {
            axios(deleteMyMovieConfig)
                .then((res) => {
                    setMyMovie(res.data);
                })
                .catch((err) => {
                    newAccessToken(err);
                    axios(deleteMyMovieConfig)
                        .then((res) => {
                            setMyMovie(res.data);
                        })
                        .catch((err) => console.log(err));
                });
        }
    };

    const handleQuiz = () => {
        navigate("/quiz", { state: movieTitle });
    };

    const handleImageError = (e) => {
        e.target.src =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/660px-No-Image-Placeholder.svg.png?20200912122019";
    };

    const handleWriteRevuew = () => {
        setWriteReview(true);
        setIsOpen(true);
    };
    return (
        <TotalWrapper>
            {movieData && (
                <>
                    <DetailWrapper>
                        <ImageWrapper
                            src={movieData.postLink}
                            onError={handleImageError}
                        />
                        <MovieInfo>
                            <TitleLike>
                                <Title>
                                    <h2>{movieTitle}</h2>
                                </Title>
                                <ButtonWrapper>
                                    <LikeBtn onClick={handleLikeButton}>
                                        {isLike ? <FullHeart /> : <Heart />}
                                        <p>좋아요</p>
                                    </LikeBtn>
                                    <LikeBtn onClick={handleMyMovieButton}>
                                        {myMovie ? <LikeHeart /> : <Heart />}
                                        <p>찜하기</p>
                                    </LikeBtn>
                                </ButtonWrapper>
                            </TitleLike>
                            <MovieDetailInfo>
                                <p>12세 이상 관람가 . 10월 12일 . 2시간20분</p>
                                <p>#모험 #스릴러 #판타지</p>
                            </MovieDetailInfo>
                            <Synopsis>
                                <p>{movieData?.synopsis}</p>
                            </Synopsis>
                            <ActorAndReview>
                                <p>
                                    출연 |{" "}
                                    {movieData?.actorList
                                        ?.slice(0, 4)
                                        .map((props) => props.actorName + " ")}
                                </p>
                                <ReviewBtnWrapper>
                                    <ReviewBtn onClick={handleWriteRevuew}>
                                        리뷰 쓰기 &#62;
                                    </ReviewBtn>
                                    <ReviewBtn onClick={handleQuiz}>
                                        퀴즈 풀기 &#62;
                                    </ReviewBtn>
                                </ReviewBtnWrapper>
                            </ActorAndReview>
                        </MovieInfo>
                    </DetailWrapper>
                    <UnderContentWrapper>
                        {/* <LikeGraph>
                            <TestGraph />
                        </LikeGraph> */}
                        <VideoWrapper>
                            <VideoTitle>예고편</VideoTitle>
                            <VideoOutLine>
                                {preview?.slice(0, 3).map((props, idx) => (
                                    <Video
                                        videoId={props.id.videoId}
                                        key={idx}
                                    />
                                ))}
                            </VideoOutLine>
                        </VideoWrapper>
                        <GreenLine />
                        <VideoWrapper>
                            <StillImageTitle>
                                <p>Photo</p>
                                <h3>더보기 &#62;</h3>
                            </StillImageTitle>
                            <StillImageWrapper>
                                {movieData.length !== 0 ? (
                                    movieData.stillImage
                                        ?.slice(0, 6)
                                        .map((props, idx) => (
                                            <img
                                                src={props}
                                                key={idx}
                                                alt="still"
                                                onError={handleImageError}
                                            />
                                        ))
                                ) : (
                                    <p>스틸이미지가 존재하지 않습니다.</p>
                                )}
                            </StillImageWrapper>
                        </VideoWrapper>
                    </UnderContentWrapper>
                    <ReviewBox
                        userEmail={userEmail}
                        isOpen={isOpen}
                        writeReview={writeReview}
                        setWriteReview={setWriteReview}
                        title={movieTitle}
                        handleIsOpen={handleIsOpen}
                    />
                </>
            )}
        </TotalWrapper>
    );
};

export default Detail;

const TotalWrapper = styled.div`
    position: relative;
    width: 100vw;
`;

const DetailWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 80vh;
`;

const ImageWrapper = styled.img`
    width: 25rem;
    height: 33rem;
`;

const MovieInfo = styled.div`
    width: 25rem;
    height: 33rem;
    margin-left: 2rem;
`;
const TitleLike = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
`;

const LikeBtn = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 5rem;
    height: 2.5rem;
    margin-bottom: 0.5rem;
    border-radius: 0.3rem;
    background-color: #03af59;
    p {
        color: ${(props) => (props.isLike ? "red" : "white")};
        font-size: 0.8rem;
    }
`;

const Title = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 1.3rem;
    h2 {
        font-size: 1.5rem;
        font-weight: 700;
        color: white;
    }
`;

// const TitleSmall = styled.div`
//     display: flex;
//     justify-content: flex-end;
//     align-items: center;
//     flex-direction: column;
//     width: 2.4rem;
//     height: 100%;
//     overflow: auto;
//     p {
//         font-size: 0.5rem;
//         color: white;
//     }
// `;

const Heart = styled(AiOutlineHeart)`
    width: 1.2rem;
    height: 1.2rem;
    color: white;
    margin-right: 0.3rem;
`;

const FullHeart = styled(AiFillHeart)`
    width: 1.2rem;
    height: 1.2rem;
    color: red;
    margin-right: 0.3rem;
`;

const LikeHeart = styled(AiFillHeart)`
    width: 1.2rem;
    height: 1.2rem;
    color: #e8ac4b;
    margin-right: 0.3rem;
`;

const MovieDetailInfo = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 5rem;
    padding-top: 0.8rem;
    border-bottom: 1px solid #03af59;
    p {
        margin-bottom: 0.3rem;
        font-size: 0.9rem;
        color: white;
    }
`;

const Synopsis = styled.div`
    width: 100%;
    height: 15rem;
    padding-top: 1rem;
    padding-bottom: 1rem;
    border-bottom: 1px solid #03af59;

    p {
        font-size: 0.9rem;
        color: white;
    }
`;

const ActorAndReview = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 100%;
    height: 10.5rem;
    padding-top: 1rem;
    p {
        color: white;
    }
`;

const ReviewBtn = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 25%;
    height: 2.5rem;
    border-radius: 1rem;
    margin: 0 1rem 0 1rem;
    background-color: #e8ac4b;
    color: white;
`;

const ReviewBtnWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
    align-items: center;
    width: 100%;
`;

const UnderContentWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
`;
const TestGraph = styled.div`
    width: 90%;
    height: 90%;
    background-color: #535353;
    margin: 0 auto;
`;

const LikeGraph = styled.div`
    width: 50%;
    height: 20rem;
    margin-top: 5rem;
    border-bottom: 1px solid #03af59;
`;
const VideoWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    width: 60%;
    height: 20rem;
    margin-top: 2rem;
`;

const GreenLine = styled.div`
    width: 30rem;
    margin-top: 2rem;
    margin-bottom: 2rem;
    border: 0.5px solid #03af03;
`;

const VideoTitle = styled.h2`
    width: 100%;
    font-size: 1.5rem;
    font-weight: 600;
    color: white;
`;

const VideoOutLine = styled.div`
    display: flex;
    justify-content: center;
    width: 100%;
`;

const StillImageWrapper = styled.div`
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    margin-left: 7rem;
    img {
        width: 15rem;
        height: 13rem;
        margin-right: 1rem;
        margin-top: 1rem;
    }
`;

const StillImageTitle = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 3rem;
    padding-left: 1rem;
    padding-right: 1rem;

    p {
        font-size: 1.5rem;
        font-weight: 600;
        color: white;
    }

    h3 {
        font-size: 1.3rem;
        font-weight: 600;
        color: #03af03;
    }
`;

const ButtonWrapper = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
`;
