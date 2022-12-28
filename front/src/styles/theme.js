import baseStyled, {
  css
} from 'styled-components';

const sizes = {
  desktop: 768,
};


const media = {
  desktop: (...args) => undefined,
};

Object.keys(sizes).reduce((acc, label) => {
  switch (label) {
    case 'desktop':
      acc.desktop = (...args) =>
        css`
          @media only screen and (min-width: ${sizes.desktop}px) {
            ${args}
          }
        `;
      break;
    default:
      break;
  }
  return acc;
}, media);

const colors = {
  white: '#ffffff',
  black: '#000000',
};

const theme = {
  colors,
  fontSizes,
  secondaryColors,
  media,
};

export const styled = baseStyled;
export default theme;
