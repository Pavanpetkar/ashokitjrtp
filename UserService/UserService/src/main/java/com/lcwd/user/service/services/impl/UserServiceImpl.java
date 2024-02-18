package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User getUser(String userId) {
        //get user from UserRepository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());
        //fetch rating of the above user from RATING-SERVICE
        //http://localhost:8083/ratings/users/70323fcf-a2c4-48ea-813c-c229e4814799
        Rating [] userRatings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating [].class);
        logger.info("",userRatings);

        List<Rating> ratings = Arrays.stream(userRatings).collect(Collectors.toList());

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel.Get the hotels by ratings so we are using hotels api not the getRatingsByHotelId.
            //since we are fetching the hotels in ratings, not ratings from hotel.
            //http://localhost:8082/hotels/6f19fc05-860f-4a96-bddd-0fea3fff5baa
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEl-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            logger.info("Response status code: {} ", forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);

            return rating;

        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
