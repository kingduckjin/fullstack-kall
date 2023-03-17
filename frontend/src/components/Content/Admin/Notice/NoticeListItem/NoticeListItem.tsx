import React, { useCallback } from 'react';
import { Link } from 'react-router-dom';
import { INotice } from '@/types/tables.types';
import { useCategoryById } from '@/hooks/trueQuery/category';
import { listContentStyle } from './style';

interface INoticeListItemProps {
  item: INotice;
  items: number[];
  setItems: React.Dispatch<React.SetStateAction<number[]>>;
  type: string;
}

export function NoticeListItem({
  item, items, setItems, type,
}: INoticeListItemProps) {
  const category = useCategoryById(item.categoryDTO?.categoryId, {
    enabled: 'noticeId' in item,
  });

  const onChangeItem = useCallback((id: number) => {
    setItems((prev) => (
      prev.includes(id)
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
            name='notice'
            value={item.noticeId}
            onChange={() => onChangeItem(item.noticeId)}
            checked={items.includes(item.noticeId)}
          />
        </div>
        <div>{category.categoryName}</div>
        <div>
          <Link to={`/admin/${type}/${item.noticeId}`}>{item.title}</Link>
        </div>
        <div>{item.cnt}</div>
      </div>
    </>
  );
}
