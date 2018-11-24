package com.twm.casino;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import static java.lang.Thread.sleep;

@SpringBootApplication
public class Main {
    public static void main(String args[]) {

        SpringApplication.run(Main.class, args);
    }
}
