package com.vedant.TransportBookingBackend.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vedant.TransportBookingBackend.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private static final Integer EXPIRE_MINUTES = 5;

    private final EmailService emailService;

    private LoadingCache<String, Integer> otpCache;

    public OtpService(@Autowired EmailService emailService) {
        this.emailService = emailService;
        this.otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, Integer>() {
                            @Override
                            public Integer load(String s) {
                                return 0;
                            }
                        }
                );
    }

    public void generateOTP(User user) throws IOException {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(user.getUsername(), otp);
        emailService.sendOtpViaEmail(user, otp);
    }

    public int getOtp(String username) {
        try{
            return otpCache.get(username);
        }catch (Exception e){
            return 0;
        }
    }

    public boolean isOtpValid(String username, int otp) {
        return otp == getOtp(username);
    }

    public void clearOTP(String key){
        otpCache.invalidate(key);
    }
}
