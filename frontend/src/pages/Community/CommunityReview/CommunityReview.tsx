import React from 'react';
import { useCookies } from 'react-cookie';
import { AppLayout, CommunityLayout } from '@/layouts';
import { Heading2 } from '@/components/Content';
import { ReviewItem } from '@/components/Content/Community';
import { communityReviewListStyle } from './style';
import { useReviews } from '@/hooks/trueQuery/review';

export function CommunityReview() {
  const [ { role, }, ] = useCookies([ 'role', ]);
  const reviews = useReviews(role as string);

  return (
    <>
      <AppLayout title='커뮤니티 - 리뷰'>
        <CommunityLayout pageId='community-review-page'>
          <Heading2>리뷰</Heading2>
          <div css={communityReviewListStyle}>
            {reviews.map((item) => (
              <ReviewItem key={item.reviewId} item={item} />
            ))}
          </div>
        </CommunityLayout>
      </AppLayout>
    </>
  );
}
