package com.kotlinkoalas.koalamarket.utils;
import java.util.Random;


public class ClientHelper {
    public static String createRandomDni() {
        Random random = new Random();
        String numbers = String.format("%08d", random.nextInt(100000000));
        char letter = (char) (random.nextInt(26) + 'A');
        return numbers + letter;
    }
}
