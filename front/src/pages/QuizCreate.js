import styled from "styled-components";
import { AiOutlineCheck } from "react-icons/ai";
import { GrAdd } from "react-icons/gr";
import { useState,useEffect } from "react";
import { useLocation } from "react-router-dom";
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import axios from "axios";
import { getAccessToken } from "../storage/Cookie";
import { newAccessToken } from "../module/refreshToken";

const QuizCreate = () => {
    const location = useLocation();
    const [QuizTitle, setQuizTitle] = useState("");
    const [correct, setCorrect] = useState("1");
    const [firstQuiz, setFirstQuiz] = useState('');
    const [secondQuiz, setSecondQuiz] = useState('');
    const [thirdQuiz, setThirdQuiz] = useState('');
    const [fourthQuiz, setFourthQuiz] = useState('');
    const [quizAmount, setQuizAmount] = useState([1]);
    const title = location.state;

    useEffect(() => {
      
    },[quizAmount])

    
    const handleTitle = (e) => {
        const { value } = e.target;
        setQuizTitle(value);
    };

    const handleFirstInput = (e) => {
      setFirstQuiz(e.target.value);
    }
    
    const handleSecondInput = (e) => {
      setSecondQuiz(e.target.value);
    }

    const handleThirdInput = (e) => {
      setThirdQuiz(e.target.value);
    }

    const handleFourthInput = (e) => {
      setFourthQuiz(e.target.value);
    }

    const handleCorrect = (e) => {
      const { value } = e.target;
      setCorrect(value);
    }

    const handleAddQuiz = () => {
      const tmp = [...quizAmount];
      tmp.push(1);
      setQuizAmount(tmp);
    }

    const handleSubmit = () => {
      const postBody = {
        "quizTitle": QuizTitle,
        "movieTitle": title,
        "quizItems":[
          {"item": firstQuiz,"key":"1"},
          {"item": secondQuiz,"key":"2"},
          {"item": thirdQuiz,"key":"3"},
          {"item": fourthQuiz,"key":"4"},
        ],
        "correct":correct
      };

      const QuizCreateConfig = {
        method: 'post',
        url:'/quiz/save',
        headers:{
          Authorization: `Bearer ${getAccessToken()}`,
        },
        data: postBody
      };

      axios(QuizCreateConfig).then(res => console.log(res)).catch(err => {
        newAccessToken(err);
      });
    }
    return (
        <QuizCreateWrapper>
            <InnerContent>
                <CreateTitle>
                    <SubmitBtn onClick={handleSubmit}>출제하기</SubmitBtn>
                </CreateTitle>
                  <Create>
                  {quizAmount.map(() => (
                    <QuizWrapper>
                        <LeftSide>
                            <LeftTitle>
                                <CreateBadge>
                                    <AiOutlineCheck />
                                </CreateBadge>
                                <InputTitle onChange={handleTitle} />
                            </LeftTitle>
                            <LineWhite />
                            <AddBtn onClick={handleAddQuiz}>
                                <GrAdd />
                            </AddBtn>
                        </LeftSide>
                        <BtnWrapper>
                            <QuizBtn>
                                <BtnInput onChange={handleFirstInput}/>
                            </QuizBtn>
                            <QuizBtn>
                                <BtnInput onChange={handleSecondInput}/>
                            </QuizBtn>
                            <QuizBtn>
                                <BtnInput onChange={handleThirdInput}/>
                            </QuizBtn>
                            <QuizBtn>
                                <BtnInput onChange={handleFourthInput}/>
                            </QuizBtn>
                            <QuizBtn correct="correct">
                                <CorrectWord>답</CorrectWord>
                                <Select
                                    variant="standard"
                                    value={correct}
                                    label="Age"
                                    onChange={handleCorrect}
                                >
                                    <MenuItem value={1}>1번</MenuItem>
                                    <MenuItem value={2}>2번</MenuItem>
                                    <MenuItem value={3}>3번</MenuItem>
                                    <MenuItem value={4}>4번</MenuItem>
                                </Select>
                            </QuizBtn>
                        </BtnWrapper>
                    </QuizWrapper>
                    ))}
                </Create>
            </InnerContent>
        </QuizCreateWrapper>
    );
};

export default QuizCreate;

const QuizCreateWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100vw;
    height: 100vh;
`;

const InnerContent = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 60%;
    height: 100%;
`;

const CreateTitle = styled.div`
    display: flex;
    align-items: center;
    justify-content: flex-end;
    width: 100%;
    height: 5rem;
    margin-bottom: 4rem;
`;

const SubmitBtn = styled.button`
    width: 9rem;
    height: 2.5rem;
    border-radius: 0.3rem;
    color: white;
    background-color: #03af59;
`;

const Create = styled.div`
    display: flex;
    align-items: center;
    justify-content: flex-start;
    flex-direction: column;
    flex-wrap: nowrap;  
    width: 100%;
    height: 100%;
`;

const CreateBadge = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 1.5rem;
    height: 1.5rem;
    border-radius: 50%;
    background-color: orange;
    color: white;
`;

const QuizWrapper = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 30%;
    margin-bottom: 2rem;
`;

const BtnWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 40%;
    height: 100%;
`;

const LeftSide = styled.div`
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    flex-direction: column;
    width: 40%;
    height: 100%;
`;

const LeftTitle = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 3rem;
`;

const InputTitle = styled.input`
    width: 90%;
    height: 3rem;
    color: white;
    font-size: 1rem;
    border-bottom: 1px solid white;
`;

const LineWhite = styled.div`
    height: 80%;
    border: 0.5px solid white;
    margin-left: 0.75rem;
`;

const QuizBtn = styled.button`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 20%;
    margin-bottom: 0.5rem;
    border-radius: 1rem;
    background-color: ${props => props.correct === 'correct' ? "#03af59" : "white"};
`;
const AddBtn = styled.button`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 1.5rem;
    height: 1.5rem;
    border-radius: 50%;
    background-color: white;
    margin-top: 1rem;
    color: black;
`;

const BtnInput = styled.input`
    width: 50%;
    height: 100%;
    font-weight: 600;
    font-size: 1rem;
    color: black;
`;

const CorrectWord = styled.h1`
  font-size: 1rem;
  color: white;
  margin-right: 1rem;
`