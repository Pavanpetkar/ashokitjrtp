package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    //create
    Rating createRating(Rating rating);

    //get all ratings
    List<Rating> getAllRatings();

    //find ratings by user id
    List<Rating> findByUserId(String userId);

    //find ratings by hotel id
    List<Rating> findByHotelId(String hotelId);

}
