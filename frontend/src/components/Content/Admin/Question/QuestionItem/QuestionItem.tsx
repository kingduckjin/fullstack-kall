import React, { useCallback } from 'react';
import { Link } from 'react-router-dom';
import { IQuestion } from '@/types/tables.types';
import { listContentStyle } from './style';
import { setDate } from '@/utils/setDate';

interface IQuestionItemProps {
  item: IQuestion;
  items: number[];
  setItems: React.Dispatch<React.SetStateAction<number[]>>;
}

export function QuestionItem({ item, items, setItems, }: IQuestionItemProps) {
  const onChangeItem = useCallback((id: number) => {
    setItems((prev) => (prev.includes(id)
      ? prev.filter((item) => item !== id)
      : [ ...prev, id, ]
    ));
  }, []);

  return (
    <>
      <div className='list-content' css={listContentStyle}>
        <div>
          <input
            type='checkbox'
            name='question'
            value={item.productQId}
            onChange={() => onChangeItem(item.productQId)}
            checked={items.includes(item.productQId)}
          />
        </div>
        <div>
          <Link to={`/admin/question/${item.productQId}`}>{item.title}</Link>
        </div>
        <div>{item.userDTO?.userId}</div>
        <div>{setDate(item.date1)}</div>
      </div>
    </>
  );
}
