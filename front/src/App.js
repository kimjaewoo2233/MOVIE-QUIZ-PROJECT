import React,{Suspense} from 'react';
import Router from './Router';
import {CircularProgress} from '@mui/material';

function App() {
  return (
    <Suspense fallback={<CircularProgress color='success'/>}>
      <Router />
    </Suspense>
  );
}

export default App;
