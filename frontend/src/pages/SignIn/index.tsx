import React, {
  FormEvent, useCallback, useRef, useState
} from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Global } from '@emotion/react';
import tw, { css } from 'twin.macro';
import { useCookies } from 'react-cookie';
import { AxiosError } from 'axios';
import { AppLayout } from '@/layouts';
import { Heading2, Heading3 } from '@/components/Content';
import {
  buttonStyle, checksStyle, inputsStyle, kakaoSigninStyle, signInPageStyle, signInLinkStyle, signinQuestionStyle
} from './style';
import kakaoLogin from '@/images/kakao_login_large_wide.png';
import { useInput } from '@/hooks';
import { useUserById } from '@/hooks/trueQuery/users';
import { kallInstance } from '@/data/axios.data';
import { passwordCheckExp } from '@/data/regexp';
import { ILoginResponse } from '@/types/other.types';
import { IUser } from '@/types/tables.types';

export function SIgnIn() {
  const [ loginError, setLoginError, ] = useState(false);
  const [ errorMessage, setErrorMessage, ] = useState('');

  const navi = useNavigate();
  const idRef = useRef<HTMLInputElement>();
  const passwordRef = useRef<HTMLInputElement>();

  const id = useInput(idRef, 'id');
  const password = useInput(passwordRef, 'password');
  const isSaveCheckRef = useRef<HTMLInputElement>();
  const autoLoginCheckRef = useRef<HTMLInputElement>();

  const [ cookies, setCookie, ] = useCookies([ 'id', 'role', 'idSave', ]);
  const userData = useUserById(cookies.id, {
    enabled: !!cookies.id,
  });

  const passLengthError = password.data.value.length <= 6;
  console.log(passLengthError);

  const onSubmitSignInForm = useCallback((event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (passLengthError) {
      return;
    }

    const newData: IUser = {
      userId: id.data.value,
      password: password.data.value,
    };

    console.log('로그인 데이터 >> ', newData);

    kallInstance.post<ILoginResponse>('/users/login', newData)
      .then((res) => {
        console.log('로그인 여부 >> ', res);
        const time = new Date();
        time.setFullYear(time.getFullYear() + 1);

        if (res.data) {
          const { user, token, } = res.data;

          localStorage.setItem('token', JSON.stringify(token));
          localStorage.setItem('user', JSON.stringify(user));

          if (isSaveCheckRef.current) {
            setCookie('idSave', true);
          }

          if (autoLoginCheckRef.current) {
            const exp = new Date();
            exp.setFullYear(exp.getFullYear() + 1);

            setCookie(
              'id',
              user.userId,
              { path: '/', expires: exp, }
            );

            setCookie(
              'role',
              user.role,
              { path: '/', expires: exp, }
            );
          }
          navi('/');
        }
      })
      .catch((error: AxiosError<{message: string}>) => {
        console.error(error);

        setLoginError(true);
        setErrorMessage(error.response.data.message);
      });
  }, [ id, password, userData, kallInstance, passwordCheckExp, isSaveCheckRef, ]);

  return (
    <>
      <Global
        styles={css`
          main {
            ${tw` !w-[600px] `}
          }
        `}
      />
      <AppLayout title='로그인'>
        <div id='signin-page' css={signInPageStyle}>
          <div className='signin'>
            <Heading2>로그인</Heading2>
            <form onSubmit={onSubmitSignInForm}>
              <div className='form-input' css={inputsStyle}>
                <input
                  type='text'
                  placeholder='아이디를 입력하세요.'
                  required
                  ref={idRef}
                  {...id.data}
                />
                <input
                  type='password'
                  placeholder='7자리 이상의 비밀번호를 입력하세요.'
                  required
                  ref={passwordRef}
                  {...password.data}
                />
              </div>
              {loginError && (<p>{errorMessage}</p>)}
              {passLengthError && (<p>비밀번호는 공백이 없는 7자리 이상이어야 합니다.</p>)}
              <div className='form-check' css={checksStyle}>
                <label htmlFor='id-save'>
                  <input type='checkbox' id='id-save' ref={isSaveCheckRef} /> 아이디 저장
                </label>
                <label htmlFor='auto-signin'>
                  <input type='checkbox' id='auto-signin' ref={autoLoginCheckRef} /> 자동 로그인
                </label>
              </div>
              <button css={buttonStyle}>로그인</button>
            </form>
            <div css={signInLinkStyle}>
              <Link to='/find/id'>아이디 찾기</Link>
              <Link to='/find/password'>비밀번호 찾기</Link>
              <Link to='/signup'>회원가입</Link>
            </div>
          </div>

          <div className='social-signin'>
            <Heading3>SNS로 간편하게 로그인하세요.</Heading3>
            <button className='kakao-signin' css={kakaoSigninStyle}>
              <img src={kakaoLogin} alt='카카오 로그인' />
            </button>
          </div>

          <div className='signin-question' css={signinQuestionStyle}>
            <Link to='/community/notice'>로그인에 문제가 있으신가요?</Link>
          </div>
        </div>
      </AppLayout>
    </>
  );
}
