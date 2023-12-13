package com.harun.liveSlide.global;

import com.harun.liveSlide.enums.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class GlobalVariables {
    public static final String USER_ID = String.valueOf(UUID.randomUUID());
    public static String SESSION_ID;
    public static UserType userType;
    public static String SESSION_CREATION_TIME;
}
