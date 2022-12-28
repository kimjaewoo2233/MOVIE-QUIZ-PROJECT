import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styled from "styled-components";
import LinearProgress from "@mui/material/LinearProgress";
import Box from "@mui/material/Box";
import axios from "axios";
import { getAccessToken } from "../storage/Cookie";

const Quiz = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const title = location.state;
    const [quizStart, setQuizStart] = useState(false);
    const [current, setCurrent] = useState(0);
    const [propgessValue, setProgressValue] = useState(0);
    const [quizList, setQuizList] = useState([]);
    const [userAnswerList, setUserAnswerList] = useState([]);

    const quizConfig = {
        method: "get",
        url: `/mvi/problem?title=${title}`,
        header: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };
    

    useEffect(() => {
        axios(quizConfig)
            .then((res) => {
              setQuizList(res.data);
              setProgressValue((current /res.data.length) * 100);
            })
            .catch((err) => console.log(err));
    },[]);

    const handleQuizStart = () => {
        setQuizStart(true);
    };

    const handleAnswer = (e) => {
      const {value} = e.target;
      const answer = [...userAnswerList];
      answer.push(value);
      setUserAnswerList(answer);

      let newCu = current + 1;
      setCurrent(newCu);
      setProgressValue(newCu /quizList.length * 100);

      if(current === quizList.length - 1){
        console.log(answer);
        navigate('/');
      }
       
    }
    return (
        <QuizWrapper>
            {!quizStart && (
                <>
                    <MovieTitle>
                        <strong>{title}</strong>에 대한 퀴즈를 풀어보시겠습니까?
                    </MovieTitle>
                    <QuizBtn onClick={handleQuizStart}>시작하기</QuizBtn>
                    <CreateQuiz
                        onClick={() =>
                            navigate("/quizCreate", { state: title })
                        }
                    >
                        퀴즈 만들러 가기 &gt;
                    </CreateQuiz>
                </>
            )}
            {quizStart && (
                <QuizListWrapper>
                    <Box sx={{ width: "50rem" }}>
                        <LinearProgress
                            variant="determinate"
                            color="warning"
                            value={propgessValue}
                        />
                    </Box>
                    <QuizTitle>
                        <p>{quizList[current].quizTitle}</p>
                    </QuizTitle>
                    <AnswerBtnWrapper>
                    {quizList[current]?.quizItems?.map((props,idx) => (
                      <AnswerBtn onClick={handleAnswer} value={props.correct} key={idx}>{props.item}</AnswerBtn>
                      ))}
                    </AnswerBtnWrapper>
                </QuizListWrapper>
            )}
        </QuizWrapper>
    );
};

export default Quiz;

const QuizWrapper = styled.section`
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100vw;
    height: 90vh;
`;

const MovieTitle = styled.h2`
    color: white;

    strong {
        font-weight: 700;
    }
`;

const QuizBtn = styled.button`
    width: 10rem;
    height: 3rem;
    margin-top: 3rem;
    background-color: #03af59;
    color: white;
    border-radius: 0.5rem;
`;
const QuizListWrapper = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-around;
    flex-direction: column;
    width: 100%;
    height: 100%;
    /* margin-top: 5rem; */
`;

const TestBtn = styled.button`
    width: 5rem;
    height: 2rem;
    background-color: blue;
`;

const CreateQuiz = styled.p`
    color: #03af59;
    text-decoration: underline;
    margin-top: 1rem;
    cursor: pointer;
`;

const QuizTitle = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 25rem;
    height: 6rem;
    color: white;
    background-color: #03af59;
    border-radius: 1rem;
`;

const AnswerBtnWrapper = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 17rem;
`;

const AnswerBtn = styled.button`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 3rem;
    margin-bottom: 1rem;
    background-color: white;
    border-radius: 1rem;

    :hover {
        background-color: #e8ac4b;
    }
`;
