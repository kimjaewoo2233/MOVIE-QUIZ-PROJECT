import styled from "styled-components";
import { Rating, Switch, FormControlLabel } from "@mui/material";
import { useState } from "react";
import { AiOutlineStar, AiFillStar } from "react-icons/ai";
import axios from "axios";
import { getAccessToken } from "../storage/Cookie";

const ReviewPostCard = ({ title }) => {
    const [value, setValue] = useState(0);
    const [checked, setChecked] = useState(false);
    const [desc, setDesc] = useState("");
    const movieTitle = title;

    const postConfig = {
      method: "post",
      url: `/comments/save`,
      headers: {
          Authorization: `Bearer ${getAccessToken()}`,
      },
      data:{
        content:desc,
        movieTitle:movieTitle,
        spoiler:checked,
        rating:value
      }
    };

    const ratingConfig = {
      method: "post",
      url: `/rating/save/${movieTitle}/${value}`,
      headers: {
          Authorization: `Bearer ${getAccessToken()}`,
      },
    }

    const handleCheck = () => {
        setChecked((prev) => !prev);
        console.log(checked);
    };

    const handleDesc = (e) => {
        setDesc(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios(postConfig)
            .then((res) => {
                axios(ratingConfig)
                    .then((res) => {
                      alert("리뷰 등록!");
                      setDesc('');
                      setChecked(false);
                      setValue(0);
                    })
                    .catch((err) => console.log(err));
            })
            .catch((err) => console.log(err));
    };
    return (
        <CardWrapper onSubmit={handleSubmit}>
            <Rating
                name="simple-controlled"
                value={value}
                icon={<Star fontSize="inherit" />}
                onChange={(event, newValue) => {
                    setValue(newValue);
                }}
            />
            <InputContent
                rows={7}
                placeholder="후기를 작성하세요"
                value={desc}
                onChange={handleDesc}
            />
            <FormControlLabel
                control={
                    <Switch
                        checked={checked}
                        onChange={handleCheck}
                        color="success"
                        size="small"
                    />
                }
                label="스포일러가 포함되어 있나요?"
            />
            <SubmitBtn type="submit">리뷰 쓰기</SubmitBtn>
        </CardWrapper>
    );
};

export default ReviewPostCard;

const CardWrapper = styled.form`
    display: flex;
    flex-direction: column;
    width: 90%;
    height: 10rem;
    border-radius: 1rem;
    margin-bottom: 1rem;
    padding-left: 1rem;
    padding-top: 0.5rem;
    background-color: white;
`;

const Star = styled(AiFillStar)`
    color: #03af59;
`;

const InputContent = styled.textarea`
    width: 90%;
    height: 3rem;
    color: #535353;
    opacity: 0.8;
    font-size: 0.8rem;
    font-weight: 600;
    border: none;
    outline: none;
    resize: none;
`;

const SubmitBtn = styled.button`
    width: 5rem;
    height: 2rem;
    background-color: #03af59;
    margin: 0.5rem 0 0.5rem 0;
    border-radius: 0.5rem;
    color: white;
`;
