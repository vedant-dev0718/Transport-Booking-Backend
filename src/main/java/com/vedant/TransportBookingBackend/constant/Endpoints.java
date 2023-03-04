package com.vedant.TransportBookingBackend.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class Endpoints {

    public static final String BASE_URL = "/api/v1";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AuthAPI {

        public static final String AUTH_BASE_URL = "/auth";

        public static final String DEALER_LOGIN = AUTH_BASE_URL + "/dealer/login";

        public static final String DEALER_LOGIN_OTP = AUTH_BASE_URL + "/dealer/login-otp";

        public static final String DEALER_SIGNUP = AUTH_BASE_URL + "/dealer/signup";

        public static final String DRIVER_LOGIN = AUTH_BASE_URL + "/driver/login";

        public static final String DRIVER_LOGIN_OTP = AUTH_BASE_URL + "/driver/login-otp";

        public static final String DRIVER_SIGNUP = AUTH_BASE_URL + "/driver/signup";

        public static final String GET_OTP = AUTH_BASE_URL + "/get-otp";

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DealerAPI {

        public static final String DEALER_BASE_URL = "/dealer";

        public static final String GET_DRIVERS = DEALER_BASE_URL + "/drivers";

        public static final String GET_DRIVERS_BY_STATE = DEALER_BASE_URL + "/drivers-state";

        public static final String GET_DRIVERS_BY_ROUTE = DEALER_BASE_URL + "/drivers-route";

        public static final String BOOK_DRIVER = DEALER_BASE_URL + "/book";

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DriverAPI {

        public static final String DRIVER_BASE_URL = "/driver";

        public static final String GET_BOOKINGS = DRIVER_BASE_URL + "/bookings";

    }
}
