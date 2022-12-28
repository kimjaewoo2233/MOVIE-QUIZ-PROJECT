import styled from 'styled-components';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SignUp = () => {
  const [isValid, setIsValid] = useState(false);
  const [confirmPw, setConfirmPw] = useState('');
  const [userInfo, setUserInfo] = useState({
    email: '',
    password: '',
    gender: '',
    birth: '',
    role: 'ROLE_USER',
  });
  const navigate = useNavigate();

  const handleInput = e => {
    const { name, value } = e.target;
    setUserInfo(prev => {
      const currentUserInfo = { ...prev };
      if (name === 'email') {
        if (value.includes('@') && value.includes('.com')) {
          currentUserInfo[name] = value;
        } else {
          console.log('test');
        }
      } else {
        currentUserInfo[name] = value;
      }
      return currentUserInfo;
    });
    console.log(userInfo);
  };

  const hadleConfirmPw = e => {
    setConfirmPw(e.target.value);
  };

  const onClickSignUp = () => {
    axios
      .post('/register', userInfo)
      .then(res => {
        navigate('/');
      })
      .catch(err => console.log(err));
  };

  useEffect(() => {
    if (
      userInfo.email &&
      userInfo.password &&
      userInfo.password === confirmPw &&
      userInfo.birth &&
      userInfo.gender
    ) {
      setIsValid(true);
    } else {
      setIsValid(false);
    }
  }, [userInfo, confirmPw]);

  return (
    <LonginWrapper>
      <LoginContent>
        <LoginLabel>Email</LoginLabel>
        <LoginInput
          placeholder="email@example.com"
          name="email"
          onChange={handleInput}
        />
        <LoginLabel>비밀번호</LoginLabel>
        <LoginInput
          type="password"
          placeholder="● ● ● ● ●"
          onChange={handleInput}
          name="password"
        />
        <LoginLabel>비밀번호 확인</LoginLabel>
        <LoginInput
          type="password"
          placeholder="● ● ● ● ●"
          onChange={hadleConfirmPw}
        />
        <UserInfoBox>
          <div>
            <LoginLabel>성별</LoginLabel>
            <SelectedBox onChange={handleInput} name="gender">
              <option value="default">-- 선택 하세요 --</option>
              <option value="man">남성</option>
              <option value="woman">여성</option>
            </SelectedBox>
          </div>
          <div>
            <LoginLabel>생년월일</LoginLabel>
            <LoginInput
              type="date"
              max="2022-01-01"
              onChange={handleInput}
              name="birth"
            />
          </div>
        </UserInfoBox>
        <LoginBtn disabled={!isValid} onClick={onClickSignUp}>
          계정 만들기
        </LoginBtn>
      </LoginContent>
    </LonginWrapper>
  );
};

export default SignUp;

const LonginWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 60vh;
  margin-top: auto;
`;

const LoginContent = styled.div`
  width: 25rem;
  height: 20rem;
  margin: auto;
`;

const LoginInput = styled.input`
  width: 100%;
  height: 15%;
  margin-bottom: 1rem;
  padding-left: 1rem;
  border: 1px solid #03af59;
  border-radius: 0.5rem;
  font-size: 1.2rem;
  color: #f3f4f8;
`;

const LoginLabel = styled.h3`
  font-size: 1rem;
  color: white;
  margin-top: 1rem;
  margin-bottom: 0.5rem;
`;

const LoginBtn = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 15%;
  margin-top: 1rem;
  border: 1px solid black;
  border-radius: 0.5rem;
  font-size: 1.2rem;
  color: white;
  background-color: #03af59;
  cursor: pointer;

  :disabled {
    color: #858585;
    background-color: #535353;
  }
`;

const UserInfoBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  div {
    width: 100%;
    height: 8em;
    /* padding: 10px; */
  }

  input {
    width: 100%;
    height: 3rem;
    margin-left: 10px;
    color-scheme: dark;
  }
`;

const SelectedBox = styled.select`
  width: 100%;
  height: 3rem;
  padding-left: 1rem;
  margin-right: 10px;
  color: white;
  border: 1px solid #03af59;
  background-color: transparent;
  border-radius: 0.5rem;
`;
