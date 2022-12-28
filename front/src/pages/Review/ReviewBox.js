import { useEffect, useState } from "react";
import styled from "styled-components";
import { RxDoubleArrowUp, RxDoubleArrowDown } from "react-icons/rx";
import ReviewCard from "../../components/ReviewCard";
import ReviewPostCard from "../../components/ReviewPostCard";
import axios from "axios";
import { getAccessToken } from "../../storage/Cookie";

const ReviewBox = ({
    userEmail,
    title,
    isOpen,
    writeReview,
    handleIsOpen,
    setWriteReview,
}) => {
    const [reviewList, setReviewList] = useState([]);

    const listConfig = {
        method: "get",
        url: `/mvi/comments/list?title=${title}`
    };
    
    console.log(reviewList);
    useEffect(() => {
        axios(listConfig)
            .then((res) => setReviewList(res.data.dtoList))
            .catch((err) => console.log(err));
    }, []);

    const handleDownArrow = () => {
        handleIsOpen();
        setWriteReview(false);
    };

    return (
        <ReviewWrapper>
            <ReviewTitleWrapper>
                <ReviewTitle>
                    <h2>리뷰보기</h2>
                    <p>총 {reviewList.length}건</p>
                </ReviewTitle>
                {isOpen ? (
                    <DownArrow onClick={handleDownArrow} />
                ) : (
                    <UpArrow onClick={handleIsOpen} />
                )}
            </ReviewTitleWrapper>
            <ReviewContent isOpen={isOpen}>
                {writeReview && <ReviewPostCard title={title} />}
                {reviewList?.map((props) => (
                    <ReviewCard key={props.id} {...props} userEmail={userEmail}/>
                ))}
            </ReviewContent>
        </ReviewWrapper>
    );
};

export default ReviewBox;

const ReviewWrapper = styled.div`
    position: fixed;
    display: flex;
    justify-content: space-around;
    align-items: center;
    flex-direction: column;
    bottom: 0;
    right: 0;
    width: 25rem;
    border-radius: 2rem 0 0 0;
    background-color: #f3f4f8;
`;

const ReviewTitle = styled.div`
    display: flex;
    align-items: center;
    justify-content: flex-start;
    width: 100%;
    padding-left: 2rem;
    h2 {
        font-size: 1.4rem;
        color: #535353;
    }
    p {
        font-size: 1rem;
        color: #535353;
        opacity: 0.8;
        margin-left: 0.6rem;
        margin-top: 0.2rem;
    }
`;
const UpArrow = styled(RxDoubleArrowUp)`
    margin-right: 1rem;
    font-size: 1.6rem;
    color: #03af59;
`;

const DownArrow = styled(RxDoubleArrowDown)`
    margin-right: 1rem;
    font-size: 1.6rem;
    color: #03af59;
`;

const ReviewContent = styled.div`
    display: flex;
    align-items: flex-start;
    justify-content: center;
    flex-wrap: wrap;
    padding-top: ${(props) => (props.isOpen ? "1rem" : "0")};
    width: 100%;
    height: ${(props) => (props.isOpen ? "20rem" : "0")};
    transition: all 0.5s ease-in-out;
    overflow-y: scroll;
`;

const ReviewTitleWrapper = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-around;
    width: 100%;
    height: 4rem;
`;