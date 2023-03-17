import tw, { css } from 'twin.macro';

export const itemStyle = css`
  ${tw` border-[2px] border-solid border-black-base/10 p-[10px] text-center `}
  ${tw` hover:border-point-h-base `}

  & div.item-image {
    ${tw` text-white text-[2rem] text-center leading-[350px] bg-black-base mb-[10px] `}

    & a {
      ${tw` block text-white `}
    }

    & img {
      ${tw` block w-full h-[350px] `}
    }
  }

  & p {
    ${tw` m-[10px_0] nth-last-1:my-0 `}

    &.item-name {
      & a {
        ${tw` text-point-link `}
        ${tw` hover:text-point-h-link hover:underline `}

        & strong {
          ${tw` font-[900] text-[1.5rem] `}
        }
      }
    }
  }
`;
