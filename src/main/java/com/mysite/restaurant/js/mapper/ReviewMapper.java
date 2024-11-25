package com.mysite.restaurant.js.mapper;

import com.mysite.restaurant.js.model.Reviews;
import com.mysite.restaurant.js.model.Replies;
import com.mysite.restaurant.js.model.Reports;
import com.mysite.restaurant.js.model.Restaurants;
import com.mysite.restaurant.js.model.ReviewImg;
import com.mysite.restaurant.js.model.Helpful;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
	
    List<Reviews> getReviewById(Long restaurantId);
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

    List<Reviews> selectMyReviews(Long restaurantId);
    Restaurants selectShop(Long restaurantId);

}
