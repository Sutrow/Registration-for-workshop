package io.project.RegistrationForWorkshops;

import io.project.RegistrationForWorkshops.model.SlotContainer;
import io.project.RegistrationForWorkshops.sheets.SlotRecipient;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class RegistrationForWorkshopsApplication {
    @Autowired
    SlotRecipient sheets;
    private final Logger log = LoggerFactory.getLogger(RegistrationForWorkshopsApplication.class);

    @Scheduled(fixedRate = 3_600_000)
    public void updateSlots() {
        try {
            SlotContainer.updateSlots(sheets);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(RegistrationForWorkshopsApplication.class, args);
    }
}
