import React,{lazy} from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
const Login = lazy(() => import('./pages/Login/Login'));
const Navigation = lazy(() => import('./components/Nav/Navigation'));
const Detail = lazy(() => import('./pages/Details/Detail'));
const SignUp = lazy(() => import('./pages/Login/SignUp'));
const Main = lazy(() => import('./pages/Main/Main'));
const Search = lazy(() => import('./pages/Search/Search'));
const Quiz = lazy(() => import('./pages/Quiz'));
const QuizCreate = lazy(() => import('./pages/QuizCreate'));
const UserDetail = lazy(() => import('./pages/UserDetail'));

const Router = () => {
  return (
    <>
      <BrowserRouter>
        <Navigation />
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup/*" element={<SignUp />} />
          <Route path="/detail" element={<Detail />} />
          <Route path='/movieSearch' element={<Search />}/>
          <Route path='/quiz' element={<Quiz/>}/>
          <Route path='/quizCreate' element={<QuizCreate/>}/>
          <Route path='/userDetail' element={<UserDetail />} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;
