package com.lcwd.hotel.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
        super(message);
   }
   public ResourceNotFoundException(){
        super("Requested resource not found!!");
   }
}
