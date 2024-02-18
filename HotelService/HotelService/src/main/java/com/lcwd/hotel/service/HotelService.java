package com.lcwd.hotel.service;

import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {
    //create a hotel
    Hotel saveHotel(Hotel hotel);

    //get a hotel
    Hotel getHotel(String hotelId);

    //get all hotels
    List<Hotel> getAllHotels();
}
