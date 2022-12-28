import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import GlobalStyle from './styles/GlobalStyle';
import styled from 'styled-components';

import store from './store';
import { Provider } from 'react-redux';
import { CookiesProvider } from 'react-cookie';

const AppWrapper = styled.section`
  height: 150vh;
`;

const root = ReactDOM.createRoot(
  document.getElementById('root')
);
root.render(
  <CookiesProvider>
    <Provider store={store}>
      <AppWrapper>
        <GlobalStyle />
        <App />
      </AppWrapper>
    </Provider>
  </CookiesProvider>
);
