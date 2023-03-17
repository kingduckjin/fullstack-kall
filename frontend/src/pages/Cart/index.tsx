import React, {
  Fragment, useCallback, useEffect, useState
} from 'react';
import { useCookies } from 'react-cookie';
import { FaAngleRight, FaCheck } from 'react-icons/fa';
import { useNavigate } from 'react-router';
import { AppLayout } from '@/layouts';
import { CartList } from '@/components/Content/Cart';
import { Heading2 } from '@/components/Content';
import {
  buttonsStyle,
  cartListStyle, cartPageStyle, listStatStyle, orderProgressStyle
} from './style';
import { IOrderDetail } from '@/types/tables.types';
import { useCartByUserId, useDeleteCart } from '@/hooks/trueQuery/cart';

export function Cart() {
  const [ cookies, ] = useCookies([ 'id', ]);
  const cartList = useCartByUserId(cookies.id);
  const navigate = useNavigate();

  console.log('cartList >> ', cartList);

  const deleteCart = useDeleteCart(cookies.id);

  const [ selectedItems, setSelectedItems, ] = useState<number[]>([]);
  const [ selectAll, setSelectAll, ] = useState(false);

  const onChangeAllSelect = useCallback(() => {
    if (selectAll) {
      setSelectedItems([]);
    } else {
      setSelectedItems(cartList.map((item) => item.cartId));
    }
    setSelectAll(!selectAll);
  }, [ selectAll, cartList, ]);

  const onChangeItemSelect = useCallback((itemId: number) => {
    if (selectedItems.includes(itemId)) {
      setSelectedItems(selectedItems.filter((id) => id !== itemId));
    } else {
      setSelectedItems([ ...selectedItems, itemId, ]);
    }
  }, [ selectedItems, ]);

  useEffect(() => {
    if (selectedItems.length === cartList?.length) {
      setSelectAll(true);
    } else {
      setSelectAll(false);
    }
  }, [ cartList, selectedItems, ]);

  const totalItemPrice = cartList
    .filter((item) => selectedItems.includes(item.cartId))
    .map((item) => item.price * item.amount)
    .reduce((pre, crr) => pre + crr, 0);

  const deliveryPrice = 3000;

  const totalPrice = totalItemPrice + deliveryPrice;

  const onClickRemove = useCallback(() => {
    deleteCart.mutate(selectedItems);
    console.log('[DELETE /carts]', selectedItems);
  }, [ selectedItems, ]);

  const onClickSelectOrder = useCallback(() => {
    if (selectedItems.length === 0) {
      return;
    }

    // 불러온 카트리스트를 필터링해서 선택한 것만 고름.
    // 아이디를 붙여서 내보냄.
    const orderItems: IOrderDetail[] = cartList.filter(
      (item) => selectedItems.includes(item.cartId)
    ).map((item, index) => {
      // eslint-disable-next-line camelcase
      const { userDTO, ...orderItem } = item;
      return { id: index + 1, ...orderItem, };
    });

    console.log('orderItems >> ', orderItems);

    // 오더로 보내기 위해서 임시로 로컬스토리지에 담아둠. 그리고 오더로 이동.
    localStorage.setItem('cartToOrder', JSON.stringify(orderItems));
    // 나중에 오더가 성공하면 카트의 정보를 지워야 하기 때문에 아이디 배열을 저장해둠.
    localStorage.setItem('ids', JSON.stringify(selectedItems));
    navigate('/order');
  }, [ selectedItems, ]);

  console.log('selectedItems >> ', selectedItems);

  const onClickOrderAll = useCallback(() => {
    localStorage.setItem('cartToOrder', JSON.stringify(cartList));
    navigate('/order');
  }, [ cartList, ]);

  return (
    <>
      <AppLayout title='장바구니'>
        <div id='cart-page' css={cartPageStyle}>
          <Heading2>장바구니</Heading2>
          <div className='order-progress' css={orderProgressStyle}>
            <p className='selected'>01 장바구니</p>
            <p>02 주문 / 결제</p>
            <p>03 주문완료</p>
          </div>
          <div className='cart-list' css={cartListStyle}>
            <div className='list-header'>
              <div>
                <label htmlFor='cart0' className='checkbox'>
                  <input
                    type='checkbox'
                    id='cart0'
                    checked={selectAll}
                    onChange={onChangeAllSelect}
                    hidden
                  />
                  <div>
                    <FaCheck />
                  </div>
                </label>
              </div>
              <div>상품 / 옵션 정보</div>
              <div>상품금액</div>
            </div>
            {cartList.map((item) => (
              <Fragment key={item.cartId}>
                <CartList
                  item={item}
                  selectedItems={selectedItems}
                  onChangeItemSelect={onChangeItemSelect}
                />
              </Fragment>
            ))}
          </div>
          <div className='list-stat' css={listStatStyle}>
            <div className='stat-top'>
              <p>
                상품 총 금액
                <FaAngleRight />
                {totalItemPrice.toLocaleString()}원
              </p>
              <p>
                배송비
                <FaAngleRight />
                {deliveryPrice.toLocaleString()}원
              </p>
            </div>
            <div className='stat-bottom'>
              <p>
                합계
                <FaAngleRight />
                {totalItemPrice ? totalPrice.toLocaleString() : 0}원
              </p>
            </div>
          </div>
          <div className='buttons' css={buttonsStyle}>
            <button onClick={onClickRemove}>선택 항목 삭제</button>
            <button onClick={onClickSelectOrder}>선택 항목 주문</button>
            <button onClick={onClickOrderAll}>모든 항목 주문</button>
          </div>
        </div>
      </AppLayout>
    </>
  );
}
