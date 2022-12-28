import React from "react";
import styled, { css } from "styled-components";
import { BiUser } from "react-icons/bi";
import { HiMagnifyingGlass } from "react-icons/hi2";
import { useNavigate, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import { getAccessToken } from '../../storage/Cookie';

const Navigation = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [path] = useState(location.pathname);
    const [navHidden, setNavHidden] = useState(false);
    const [input, setInput] = useState('');

    useEffect(() => {
        if (path === "/login" || path === "/signup") {
            setNavHidden(true);
        }
    }, [location]);

    const handleSearch = (e) => {
        setInput(e.target.value);
    };

    const handleSearchSubmit = (e) => {
        e.preventDefault();
        navigate('/movieSearch', {state: input});
    }

    const handleNavUser = () => {
        if(getAccessToken()){
            navigate('/userDetail');
        } else {
            navigate('/login');
        }
    }

    return (
        <NavWrapper>
            <h1 onClick={() => navigate('/')}>Logo</h1>
            <NavSearchWrapper onSubmit={handleSearchSubmit} hidden={navHidden}>
                <NavSearchIcon />
                <NavSearch onChange={handleSearch}/>
            </NavSearchWrapper>
            <NavUserIcon onClick={handleNavUser}/>
        </NavWrapper>
    );
};

export default Navigation;

const NavWrapper = styled.div`
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 100vw;
    height: 10vh;
    h1 {
        color: #03af59;
        font-size: 2rem;
    }
`;

const NavSearchWrapper = styled.form`
    ${(props) =>
        props.hidden &&
        css`
            visibility: hidden;
        `}
    position: relative;
    display: flex;
    align-items: center;
    width: 25%;
    height: 100%;
    margin-right: 1rem;
`;

const NavSearch = styled.input`
    width: 100%;
    height: 40%;
    padding-left: 2.5rem;
    border: 1px solid #03af59;
    border-radius: 1rem;
    color: white;
    text-indent: 1rem;
`;

const NavSearchIcon = styled(HiMagnifyingGlass)`
    position: absolute;
    left: 6%;
    color: #03af59;
`;

const NavUserIcon = styled(BiUser)`
    font-size: 2rem;
    height: 40%;
    color: #03af59;
`;
