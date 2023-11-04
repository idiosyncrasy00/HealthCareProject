package com.example.HealthCareProject.config;

public class Utils {
    public static void doLongRunningTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
