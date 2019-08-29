package com.example.zdroa.myapplication.exec;

import java.util.Random;

/**
 * Created by zdroa on 03/05/2017.
 */

public class RandomIntegerClass {
    public static int randomInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
