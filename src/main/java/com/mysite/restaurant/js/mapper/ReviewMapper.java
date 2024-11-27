package com.mysite.restaurant.js.mapper;

import com.mysite.restaurant.js.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
	
    List<Reviews> selectRestaurantReviews(Long restaurantId);
    List<Reviews> selectMyReviews(Long restaurantId);
    int insertReview(Reviews review);
    int insertReviewImg(ReviewImg reviewImg);
    int updateReview(Reviews review);
    int updateReviewImage(ReviewImg reviewImg);
    int deleteReview(Long reviewId);
    int deleteReviewImg(Long reviewImgId);

    List<Replies> selectReplies(Long replyId);
    int insertReplie(Replies replies);
    int updateReplies(Replies replies);
    int deleteReply(Long replyId);

    int insertReport(Reports reports);
    List<Reports> selectReports(Long reportId);
    int deleteReport(Long reportId);

    int insertHelpful(Helpful helpful);
    int deleteHelpful(Long voteId);

    Restaurants selectShop(Long restaurantId);
    Reservation selectReservation(Long reservationId);
    User selectUser(Long userId);

}
