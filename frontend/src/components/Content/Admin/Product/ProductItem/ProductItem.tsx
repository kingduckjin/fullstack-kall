import React, { useCallback, useState } from 'react';
import { useNavigate } from 'react-router';
import { useQueryClient } from 'react-query';
import { IProduct } from '@/types/tables.types';
import { listItemEditStyle } from './style';
import { useDeleteProduct } from '@/hooks/trueQuery/product';

interface IProductItemProps {
  item: IProduct;
  selectedItems: number[];
  setSelectedItems: React.Dispatch<React.SetStateAction<number[]>>;
}

export function ProductItem({ item, selectedItems, setSelectedItems, }: IProductItemProps) {
  const [ isOpen, setIsOpen, ] = useState(false);

  const navi = useNavigate();
  const qc = useQueryClient();
  const deleteProduct = useDeleteProduct();

  const onClickOpen = useCallback(() => {
    setIsOpen((prev) => !prev);
  }, []);

  const onChangeCheck = useCallback((id: number) => {
    setSelectedItems((prev) => (
      prev.includes(id)
        ? prev.filter((item) => item !== id)
        : [ ...prev, id, ]
    ));
  }, []);

  const onClickDelete = useCallback((id: number) => {
    deleteProduct.mutate(id, {
      onSuccess: () => {
        qc.refetchQueries([ 'getProduts', ]);
      },
    });
    console.log(`[PUT /admin/products/${id}]`);
  }, [ deleteProduct, ]);

  return (
    <>
      <div className='list-content'>
        <div>
          <input
            type='checkbox'
            name='item'
            value={item?.productId}
            onChange={() => onChangeCheck(item.productId)}
            checked={selectedItems.includes(item.productId)}
          />
        </div>
        <div>{item?.categoryDTO?.categoryName}</div>
        <div onClick={onClickOpen}>{item?.name}</div>
        <div>{item?.amount}</div>
        <div>{item?.price.toLocaleString()}원</div>
      </div>
      {isOpen && (
        <div className='list-item-edit' css={listItemEditStyle}>
          <button onClick={() => navi(`/admin/products/${item.productId}/edit`)}>수정</button>
          <button onClick={() => onClickDelete(item.productId)}>삭제</button>
        </div>
      )}
    </>
  );
}
