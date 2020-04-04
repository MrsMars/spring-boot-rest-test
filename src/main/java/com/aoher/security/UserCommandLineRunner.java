package com.aoher.security;

import com.aoher.security.model.User;
import com.aoher.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.aoher.security.util.SecurityConstants.ADMIN_ROLE;
import static com.aoher.security.util.SecurityConstants.USER_ROLE;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserCommandLineRunner.class);

    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... strings) {

        repository.save(new User("Ranga", ADMIN_ROLE));
        repository.save(new User("Ravi", USER_ROLE));
        repository.save(new User("Satish", ADMIN_ROLE));
        repository.save(new User("Raghu", USER_ROLE));

        repository.findAll().forEach(u -> log.info(u.toString()));

        log.info("Admin users are.....");
        log.info("____________________");
        repository.findByRole(ADMIN_ROLE).forEach(u -> log.info(u.toString()));
    }
}
