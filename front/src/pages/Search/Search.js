import styled from "styled-components";
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

const Search = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [searchResult, setSearchResult] = useState([]);
    console.log(location.state);

    useEffect(() => {
        axios
            .get(`/search/list?title=${location.state}`)
            .then((res) => setSearchResult(res.data.items))
            .catch((err) => console.log(err));
    }, [location.state]);

    const handleImageError = (e) => {
      e.target.src = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/660px-No-Image-Placeholder.svg.png?20200912122019";
    }

    const handleNavigateDetail = (title) => {
      navigate('/detail', {state: title})
    }


    return (
        <SearchWrapper>
            <SearchResult>
                <SearchTitle>검색 결과</SearchTitle>
                <SearchImageWrapper>
                {searchResult.map((props, idx) => (
                  <PosterCard key={idx} onClick={() => handleNavigateDetail(props.title)}>
                        <PostImage src={props.postLink} onError={handleImageError}></PostImage>
                        <PostTitle>{props.title}</PostTitle>
                    </PosterCard>
                ))}
                </SearchImageWrapper>
            </SearchResult>
        </SearchWrapper>
    );
};

export default Search;

const SearchWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 50rem;
`;

const SearchResult = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 80%;
    height: 100%;
`;

const SearchTitle = styled.h2`
    display: flex;
    align-items: center;
    width: 100%;
    height: 4rem;
    color: white;
    font-size: 1.5rem;
`;

const SearchImageWrapper = styled.div`
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    height: 90%;
`;

const PosterCard = styled.div`
    width: 15rem;
    height: 23rem;
    margin: 1rem;
`;
const PostImage = styled.img`
    width: 100%;
    height: 20rem;
`;

const PostTitle = styled.h2`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 3rem;
    color: white;
`;
