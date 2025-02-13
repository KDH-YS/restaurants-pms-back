package com.mysite.restaurant.js.mapper;

import com.mysite.restaurant.js.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ReviewMapper {
	
    List<Reviews> selectRestaurantReviews(Long restaurantId);
    List<Reviews> selectMyReviews(Long userId);
    List<Restaurants> selectMyRestaurants(Long userId);
    List<ReviewImg> selectReviewImg (Long reviewid);
    Long insertReview(Reviews review);
    int insertReviewImg(ReviewImg reviewImg);
    int updateReview(Reviews review);
    int updateReviewImage(ReviewImg reviewImg);
    void deleteReview(Long reviewId);
    void deleteReviewReports(Long reviewId);
    int deleteReviewImg(Long reviewImgId);

    List<Replies> selectReplies(Long replyId);
    int insertReplie(Replies replies);
    int updateReplies(Replies replies);
    int deleteReply(Long replyId);

    int insertReport(Reports reports);
    List<Map<String, Object>> selectMyReport(@Param("user_id") Long userId);
    List<Map<String, Object>> selectReportDetails(@Param("restaurant_id") Long restaurantId);
    void deleteReport(Long reportId);

    int insertHelpful(Helpful helpful);
    void deleteHelpful(@Param("reviewId") Long reviewId, @Param("userId") Long userId);
    Boolean isHelpfulExist(@Param("reviewId") Long reviewId, @Param("userId") Long userId);
    int selectHelpfulCount(@Param("reviewId") Long reviewId);

    Restaurants selectShop(Long restaurantId);
    List<RestaurantImg> selectShopImg(Long restaurantId);
    Reservation selectReservation(Long reservationId);
    User selectUser(Long userId);
    List<User> selectAllUser(@Param("restaurantId") Long restaurantId);

}
