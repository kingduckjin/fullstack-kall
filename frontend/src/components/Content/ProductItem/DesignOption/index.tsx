import React, {
  ChangeEvent, FormEvent, useCallback, useEffect, useRef, useState
} from 'react';
import { useLocation } from 'react-router';
import { useCookies } from 'react-cookie';
import { useInput } from '@/hooks';
import { sizeData } from '@/data/select.data';
import { ISelect } from '@/types/product.select.types';
import { SelectItem } from '../SelectItem';
import {
  inputStyle, radioStyle, selectButton, selectedItemStyle
} from './style';
import { useUserById } from '@/hooks/trueQuery/users';
import { useProductById } from '@/hooks/trueQuery/product';

interface IDesignOptionProps {
  name: string;
  price: number;
  id: number;
  items: ISelect[];
  setItems: React.Dispatch<React.SetStateAction<ISelect[]>>;
}

export function DesignOption({
  name, price, id, items, setItems,
}: IDesignOptionProps) {
  const [ size, setSize, ] = useState(sizeData[0].value);
  const [ sizeLabel, setSizeLabel, ] = useState(sizeData[0].label);

  const { pathname, } = useLocation();
  const [ cookies, ] = useCookies([ 'id', ]);

  const idRef = useRef(1);
  const sizeRef = useRef<HTMLInputElement[]>([]);

  const requestRef = useRef<HTMLInputElement>();
  const request = useInput(requestRef, 'request');

  const user = useUserById(cookies.id);
  const product = useProductById(id);

  const onChangeSheet = useCallback((event: ChangeEvent<HTMLInputElement>) => {
    setSize(event.target.value);
    setSizeLabel(event.target.id);
  }, []);

  const getTotalPrice = () => {
    const getPrices = items.map((item) => item.amount * item.price);
    const totalPrice = getPrices.reduce((pre, crr) => pre + crr, 0);

    return totalPrice;
  };

  const onSubmitForm = useCallback((event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setItems((prev) => [ ...prev, {
      selectId: idRef.current++,
      userDTO: user,
      productDTO: product,
      // userId: cookies.id,
      name,
      // productId: id,
      option_size: size,
      request: request.data.value,
      price,
      amount: 1,
    }, ]);
  }, [ name, price, sizeLabel, request.data.value, ]);

  useEffect(() => {
    setSize(sizeData[0].value);
    setSizeLabel(sizeData[0].label);
    requestRef.current.value = '';
    idRef.current = 1;
  }, [ pathname, ]);

  return (
    <>
      <div>
        <form onSubmit={onSubmitForm}>
          <div css={radioStyle}>
            <p>크기</p>
            <div>
              {sizeData.map((item, index) => (
                <label key={item.value} htmlFor={item.label}>
                  <input
                    type='radio'
                    name='size'
                    id={item.label}
                    value={item.value}
                    onChange={onChangeSheet}
                    ref={(element) => { sizeRef.current[index] = element; }}
                    checked={item.value === size}
                  />
                  <span>{item.label}</span>
                </label>
              ))}
            </div>
          </div>
          <div css={inputStyle}>
            <label htmlFor={request.data.id}>
              <span>추가 요청사항</span>
              <input
                type='text'
                ref={requestRef}
                {...request.data}
              />
            </label>
          </div>
          <p className='text-red-500 font-[900]'>
            선택하지 않을 시 기본값(각 항목의 첫번째 값)으로 주문됩니다.
          </p>
          <button css={selectButton}>선택 완료</button>
        </form>
        <div className='items' css={selectedItemStyle}>
          <p className='count'>선택된 상품 총 {items.length}개</p>
          {items.map((item) => (
            <SelectItem
              key={item.selectId}
              id={item.selectId}
              item={item}
              items={items}
              setItems={setItems}
            />
          ))}
          {items && (
            <p className='total-price'>
              결제 총액(배송비 미포함): {getTotalPrice().toLocaleString()}원
            </p>
          )}
        </div>
      </div>
    </>
  );
}
