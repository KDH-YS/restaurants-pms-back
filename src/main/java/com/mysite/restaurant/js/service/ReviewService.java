package com.mysite.restaurant.js.service;

import com.mysite.restaurant.js.model.Helpful;
import com.mysite.restaurant.js.model.Replies;
import com.mysite.restaurant.js.model.Reports;
import com.mysite.restaurant.js.model.ReviewImg;
import com.mysite.restaurant.js.model.Reviews;
import com.mysite.restaurant.js.mapper.ReviewMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    public List<Reviews> getReviewById(Long reviewId) {
        return reviewMapper.getReviewById(reviewId);
    }
    public int insertReview(Reviews review) {
        return reviewMapper.insertReview(review);
    }
    public int insertReviewImage(ReviewImg reviewImg) {
        return reviewMapper.insertReviewImg(reviewImg);
    }
    public int updateReview(Reviews review) {
        return reviewMapper.updateReview(review);
    }
    public int updateReviewImage(ReviewImg reviewImg) {
        return reviewMapper.updateReviewImage(reviewImg);
    }
    public int deleteReview(Long reviewId) {
        return reviewMapper.deleteReview(reviewId);
    }
    public int deleteReviewImg(Long reviewImgId) {
        return reviewMapper.deleteReviewImg(reviewImgId);
    }
    
    public List<Replies> selectReplies(Long replyId) {
        return reviewMapper.selectReplies(replyId);
    }
    public int insertReplie(Replies replies) {
        return reviewMapper.insertReplie(replies);
    }
    public int updateReplies(Replies replies) {
        return reviewMapper.updateReplies(replies);
    }
    public int deleteReply(Long replyId) {
        return reviewMapper.deleteReply(replyId);
    }

    public int insertReport(Reports reports) {
        return reviewMapper.insertReport(reports);
    }
    public List<Reports> selectReports(Long reportId) {
        return reviewMapper.selectReports(reportId);
    }
    public int deleteReport(Long reportId) {
        return reviewMapper.deleteReport(reportId);
    }

    
    public int insertHelpful(Helpful helpful) {
        return reviewMapper.insertHelpful(helpful);
    }
    public int deleteHelpful(Long voteId) {
        return reviewMapper.deleteHelpful(voteId);
    }

    
    public List<Reviews> selectMyReviews(Long userId) {
    	return reviewMapper.selectMyReviews(userId);
    }
}
