import React from "react";
import styled, { css } from "styled-components";
import Slider from "react-slick";
import { useEffect, useState } from "react";
import { Planet } from "react-planet";
import { IoIosArrowForward, IoIosArrowBack } from "react-icons/io";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { PrevArrow, NextArrow } from "../../components/Button";
import { getAccessToken } from "../../storage/Cookie";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const Main = () => {
    const [movie, setMovie] = useState([]);
    const [carouselRotation, setCarouselRotation] = useState(0);
    const [imageIndex, setImageIndex] = useState(0);
    const navigate = useNavigate();

    const handleRotationPlus = () => {
        setCarouselRotation((prev) => prev + 40);
    };

    const handleRotationMinus = () => {
        setCarouselRotation((prev) => prev - 40);
    };

    const CAROUSEL_SETTING = {
        infinite: true,
        lazyLoad: true,
        speed: 300,
        slidesToShow: 3,
        centerMode: true,
        centerPadding: 0,
        nextArrow: <NextArrow />,
        prevArrow: <PrevArrow />,
        beforeChange: (current, next) => setImageIndex(next),
    };

    useEffect(() => {
        axios
            .get("/mvi/box")
            .then((res) => {
                setMovie(res.data);
            })
            .catch((err) => {
                console.log(err);
            });

        axios
            .get("/mvi/like")
            .then((res) => console.log(res))
            .catch((err) => console.log(err));
    }, []);

    const handleImageError = (e) => {
        e.target.src =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/660px-No-Image-Placeholder.svg.png?20200912122019";
    };

    return (
        <MainOutHeightWrapper>
            <MainWrapper>
                <MainTopText>이번 주 Best영화</MainTopText>
                <Slider {...CAROUSEL_SETTING}>
                    {movie &&
                        movie.slice(0, 7).map((props, idx) => (
                            <MainPoster
                                isMain={idx === imageIndex}
                                onClick={() =>
                                    navigate("/detail", { state: props.title })
                                }
                                key={idx}
                            >
                                <img
                                    src={props.postLink}
                                    onError={handleImageError}
                                    alt={props.title}
                                />
                            </MainPoster>
                        ))}
                </Slider>
            </MainWrapper>
            <QuizWrapper>
                <MainTopText>요즘 인기있는 영화 퀴즈</MainTopText>
                <QuizImgWrapper>
                    {movie &&
                        movie.slice(0, 7).map((props, idx) => (
                            <img
                                src={props.postLink}
                                key={idx + "movieQuiz"}
                                alt="quiz imag"
                                onClick={() =>
                                    navigate("/detail", {
                                        state: props.title,
                                    })
                                }
                                onError={handleImageError}
                            />
                        ))}
                </QuizImgWrapper>
            </QuizWrapper>
            <ThisWeek>
                <h2>이번 주에 상영하는 영화</h2>
                <div>
                    <WeekBtn onClick={handleRotationMinus}>
                        <IoIosArrowBack />
                    </WeekBtn>
                    <WeekBtn onClick={handleRotationPlus}>
                        <IoIosArrowForward />
                    </WeekBtn>
                </div>
            </ThisWeek>
            <CarouselWrapperCir>
                <Planet
                    orbitStyle={(defaultStyle) => ({
                        ...defaultStyle,
                        borderWidth: 4,
                        borderStyle: "solid",
                        borderColor: "#03af59",
                    })}
                    open
                    orbitRadius={450}
                    rotation={carouselRotation}
                    bounce={false}
                    tension={50}
                >
                    {movie.slice(0, 9).map((props, idx) => (
                        <div key={idx + "under carousel poster"}>
                            <PostImageWrapper
                                src={props?.postLink}
                                alt="post"
                                onError={handleImageError}
                            />
                        </div>
                    ))}
                </Planet>
            </CarouselWrapperCir>
        </MainOutHeightWrapper>
    );
};

export default Main;

const MainWrapper = styled.section`
    width: 80%;
    height: 30rem;
    margin: 0 auto;
`;

const MainOutHeightWrapper = styled.section`
    display: flex;
    align-items: center;
    justify-content: start;
    flex-direction: column;
    position: relative;
    height: 195vh;
    overflow-y: hidden;
`;
const MainTopText = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 7vh;
    font-size: 1.3rem;
    font-weight: 600;
    color: #03af59;
`;

const PostImageWrapper = styled.img`
    width: 12rem;
    height: 15rem;
`;

const QuizWrapper = styled.section`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 40vh;
    margin-top: 3rem;
`;

const QuizImgWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    img {
        width: 10%;
        height: 100%;
        padding-right: 0.5rem;
    }
`;

const MainPoster = styled.div`
    width: 100%;
    height: 30rem;
    transform: scale(0.7);
    transition: transform 300ms;
    opacity: 0.5;
    ${(props) =>
        props.isMain &&
        css`
            transform: scale(1);
            opacity: 1;
        `}
    img {
        width: 20rem;
        height: 100%;
        margin: 0 auto;
    }
`;

const CarouselWrapperCir = styled.section`
    position: absolute;
    bottom: -20rem;
    display: flex;
    justify-content: center;
    width: 100%;
    height: 30vh;
`;

const ThisWeek = styled.div`
    display: flex;
    justify-content: flex-start;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 8rem;
    margin-top: 3rem;

    h2 {
        font-size: 1.3rem;
        font-weight: 600;
        color: #03af59;
    }
    div {
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 1rem;
    }
`;

const WeekBtn = styled.button`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 3rem;
    height: 3rem;
    border: 1px solid white;
    border-radius: 50%;

    & + & {
        margin-left: 2rem;
    }
    svg {
        width: 1rem;
        height: 1rem;
        color: white;
    }
    :hover {
        border: 1px solid #03af59;
        svg {
            color: #03af59;
        }
    }
`;
