package com.reign.lofty.space.config;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private WorkRepository workRepository;


    @Override
    public void run(String... args) throws Exception {

        Work w1 = new Work(1L, "1", "1", "1", "1", "1", "1", new byte[1]);
        Work w2 = new Work(2L, "2", "2", "2", "2", "2", "2", new byte[2]);

        workRepository.saveAll(Arrays.asList(w1, w2));
    }
}