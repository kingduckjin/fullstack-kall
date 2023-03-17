import tw, { css } from 'twin.macro';

export const listContentStyle = css`
  ${tw` flex items-center gap-[2px] `}

  & > p {
    ${tw` flex items-center justify-center p-[10px] border border-solid border-black-200 bg-black-50 `}
    ${tw` nth-1:shrink-0 nth-1:w-[70px] `}
    ${tw` nth-2:shrink-0 nth-2:w-[150px] `}
    ${tw` nth-3:flex-[1] nth-3:justify-start nth-3:hover:border-point-h-base nth-3:hover:bg-point-base/40 nth-3:cursor-pointer `}
    ${tw` nth-4:shrink-0 nth-4:w-[300px] `}
  }
`;

export const listDetailStyle = css`
  ${tw` border border-solid border-black-200 bg-black-50 p-[10px] flex flex-col gap-[10px] `}

  & > div {
    ${tw` flex items-center gap-[2px] `}

    &:nth-of-type(1) {
      & > p {
        ${tw` font-[900] `}
      }
    }

    & > p {
      ${tw` flex-[1] flex items-center justify-center `}
    }
  }
`;
