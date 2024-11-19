package com.mysite.restaurant.js.mapper;

import com.mysite.restaurant.js.model.Reviews;
import com.mysite.restaurant.js.model.Replies;
import com.mysite.restaurant.js.model.Reports;
import com.mysite.restaurant.js.model.Helpful;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
	
    List<Reviews> getReviewById(Long reviewId);

    int insertReview(Reviews review);

    int insertReviewImg(Reviews review);

    int insertReplie(Replies replies);

    int insertReport(Reports reports);

    int insertHelpful(Helpful helpful);

    int updateReview(Reviews review);

    int updateReviewImage(Reviews review);

    int deleteReview(Long reviewId);

    int deleteReport(Long reportId);

    List<Reviews> selectMyReviews(Long userId);

    List<Reports> selectReport(Long reviewId);
}
