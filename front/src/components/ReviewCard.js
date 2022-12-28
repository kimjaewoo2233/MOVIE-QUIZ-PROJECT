import { useState } from "react";
import styled from "styled-components";
import { Rating } from "@mui/material";
import { BiDotsVerticalRounded } from "react-icons/bi";
import { MdAutoFixNormal } from "react-icons/md";
import { AiFillStar } from "react-icons/ai";
import { BsTrash } from "react-icons/bs";
import { getAccessToken } from "../storage/Cookie";
import axios from "axios";

const ReviewCard = ({ id,spoiler, content, rating, userEmail, email, blind }) => {
    const [isSpoiler, setIsSpoiler] = useState(false);
    const [isOpen, setIsOpen] = useState(false);

    const handleSpoiler = () => {
        setIsSpoiler(false);
    };

    const handleOnClick = () => {
        setIsOpen((prev) => !prev);
    };

    const reportConfig = {
      method: 'post',
      url: `/comments/denger/${id}`,
      headers:{
        Authorization: `Bearer ${getAccessToken()}`,
      }
    }

    const handleReport = () => {
      axios(reportConfig).then(res => console.log(res)).catch(err => console.log(err));
    };

    return (
        <CardWrapper>
            <CardTitle>
                <Rating
                    name="simple-controlled"
                    value={rating}
                    icon={<Star fontSize="inherit" />}
                    readOnly
                    size="small"
                />
                <BiDotsVerticalRounded onClick={handleOnClick} />
                {isOpen && (
                    <FixDeleteBox>
                        <h2>
                            수정하기 <MdAutoFixNormal />
                        </h2>
                        <h4>
                            삭제하기 <BsTrash />
                        </h4>
                        {userEmail !== email && <h3 onClick={handleReport}>신고하기</h3>}
                    </FixDeleteBox>
                )}
            </CardTitle>
            <h1>{content}</h1>
            {isSpoiler && (
                <Spoiler>
                    <h2>
                        해당 리뷰에는 <strong>스포일러</strong>가 포함되어
                        있습니다
                    </h2>
                    <h2>그래도 확인하시겠습니까?</h2>
                    <MoreBtn onClick={handleSpoiler}>더보기</MoreBtn>
                </Spoiler>
            )}
            {blind && (
                <Spoiler>
                    <h2>
                        해당 리뷰는 <strong>블라인드 </strong>처리 되었습니다.
                    </h2>
                </Spoiler>
            )}
        </CardWrapper>
    );
};

export default ReviewCard;

const CardWrapper = styled.div`
    position: relative;
    display: flex;
    justify-content: flex-start;
    flex-direction: column;
    align-items: flex-start;
    width: 90%;
    height: 10rem;
    padding-left: 2rem;
    border-radius: 1rem;
    margin-bottom: 1rem;
    background-color: white;
`;

const Spoiler = styled.div`
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    backdrop-filter: blur(5px);
    border-radius: 1rem;

    strong {
        font-weight: 700;
    }
`;

const MoreBtn = styled.button`
    width: 3rem;
    height: 2rem;
    margin-top: 1rem;
    border-radius: 0.5rem;
    color: white;
    background-color: #03af59;
`;
const CardTitle = styled.div`
    position: relative;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    width: 100%;
    height: 2rem;
`;

const FixDeleteBox = styled.div`
    position: absolute;
    top: 2rem;
    right: 0;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    width: 10rem;
    height: 5rem;
    box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
    background-color: white;

    h4 {
        color: red;
    }

    h3 {
        color: orange;
    }
`;
const Star = styled(AiFillStar)`
    color: #03af59;
`;
