import React from "react";
import styled from "styled-components";
import { BsHeartFill } from "react-icons/bs";

const Like = () => {
    return (
        <LikeWrapper>
            <BsHeartFill />
        </LikeWrapper>
    );
};

export default Like;

const LikeWrapper = styled.div`
    width: 5rem;
    height: 5rem;
    border: 1px solid white;
    svg {
        width: 100%;
        height: 100%;
        color :red;
    }
`;
