package com.radon.domain;

import java.security.SecureRandom;

public class OrderNumberGenerator {

    private static final String PREFIX = "ORD-";

    private static final String CHARACTERS = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";

    private static final int RANDOM_LENGTH = 12;

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateOrderNumber() {
        StringBuilder orderNumber = new StringBuilder(PREFIX);
        for(int i = 0; i < RANDOM_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            orderNumber.append(CHARACTERS.charAt(index));
        }
        return orderNumber.toString();
    }

}
