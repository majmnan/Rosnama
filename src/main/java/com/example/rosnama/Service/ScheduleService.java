package com.example.rosnama.Service;
import com.example.rosnama.Repository.ExternalEventRepository;
import com.example.rosnama.Repository.InternalEventRepository;
import com.example.rosnama.Repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ExternalEventRepository externalEventRepository;
    private final InternalEventRepository internalEventRepository;
    private final RegistrationRepository registrationRepository;
    private final NotificationService notificationService;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void dailyChanges(){
        log.info("Daily schedule started...");

        //make events OnGoing
        externalEventRepository.findExternalEventsByStartDate(LocalDate.now())
                .forEach(e ->e.setStatus("OnGoing"));

        internalEventRepository.findInternalEventByStartDate(LocalDate.now())
                .forEach(e -> e.setStatus("OnGoing"));

        //make registration active
        registrationRepository.findRegistrationsByDate(LocalDate.now())
                .forEach(r -> {
                    r.setStatus("Active");

                    //remind users of their active registration
                    notificationService.notifyUser(
                            r.getUser().getEmail(),
                            r.getUser().getPhoneNumber(),
                            "Event Today Reminder",
                            r.getUser().getUsername(),
                            "Reminder: You have an event today. Enjoy your time!"
                    );
                });


        //remind users of their tommowrow registration
        registrationRepository.findRegistrationsByDate(LocalDate.now().plusDays(1))
                .forEach(r -> {

                    // remind users of their active registration
                    notificationService.notifyUser(
                            r.getUser().getEmail(),
                            r.getUser().getPhoneNumber(),
                            "Upcoming Event Reminder",
                            r.getUser().getUsername(),
                            "Reminder: You have an event tomorrow. Don’t forget!"
                    );
                });

        //make registration ended
        registrationRepository
                .findRegistrationsByDateAndStatus(LocalDate.now().minusDays(1),"Active")
                .forEach(r -> {
                    r.setStatus("Expired");

                    //notify users that their registration expired and ended
                    notificationService.notifyUser(
                            r.getUser().getEmail(),
                            r.getUser().getPhoneNumber(),
                            "Registration Expired",
                            r.getUser().getUsername(),
                            "Your event registration has ended."
                    );
                });

        //make events ended
        externalEventRepository.findExternalEventsByEndDate(LocalDate.now().minusDays(1))
                .forEach(e -> e.setStatus("Ended"));
        internalEventRepository.findInternalEventByEndDate(LocalDate.now().minusDays(1))
                .forEach(e -> e.setStatus("Ended"));

        log.info("Daily schedule finished.");
    }
}
