package com.example.rosnama.Service;
import com.example.rosnama.DTO.ExternalEventDTOIn;
import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Repository.ExternalEventRepository;
import com.example.rosnama.Repository.InternalEventRepository;
import com.example.rosnama.Repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ExternalEventRepository externalEventRepository;
    private final InternalEventRepository internalEventRepository;
    private final RegistrationRepository registrationRepository;
    private final NotificationService notificationService;
    private final SerpService serpService;
    private final ExternalEventService externalEventService;

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
                .forEach(registration -> {
                    registration.setStatus("Active");

                    InternalEvent event = registration.getInternalEvent();
                    //remind users of their active registration
                    notificationService.notify(
                            registration.getUser().getEmail(),
                            registration.getUser().getPhoneNumber(),
                            "Event Today Reminder",
                            registration.getUser().getUsername(),
                            """
                                    Reminder: You have an event today:
                                    title: %s
                                    location: %s
                                    startTime: %s
                                    endTime: %s
                                    """
                                    .formatted(event.getTitle(), event.getLocation(), event.getStartTime(), event.getEndTime())
                    );
                });


        //remind users of their tomorrow registration
        registrationRepository.findRegistrationsByDate(LocalDate.now().plusDays(1))
                .forEach(r -> {
                    InternalEvent event = r.getInternalEvent();
                    //remind users of their active registration
                    notificationService.notify(
                            r.getUser().getEmail(),
                            r.getUser().getPhoneNumber(),
                            "Tomorrow Event Reminder",
                            r.getUser().getUsername(),
                            """
                                    Reminder: You have an event tomorrow:
                                    title: %s
                                    location: %s
                                    startTime: %s
                                    endTime: %s
                                    Don't Forget!
                                    """
                                    .formatted(event.getTitle(), event.getLocation(), event.getStartTime(), event.getEndTime())
                    );
                });

        //make registration ended
        registrationRepository
                .findRegistrationsByDateAndStatus(LocalDate.now().minusDays(1),"Active")
                .forEach(r -> {
                    r.setStatus("Expired");

                    InternalEvent event = r.getInternalEvent();
                    //notify users that their registration expired and ended
                    notificationService.notify(
                            r.getUser().getEmail(),
                            r.getUser().getPhoneNumber(),
                            "Registration Expired",
                            r.getUser().getUsername(),
                            "Your registration of event:\n"+ event.getTitle() +"\nhas expired."
                    );
                });
        //make events ended
        externalEventRepository.findExternalEventsByEndDate(LocalDate.now().minusDays(1))
                .forEach(e -> e.setStatus("Ended"));
        internalEventRepository.findInternalEventByEndDate(LocalDate.now().minusDays(1))
                .forEach(e -> e.setStatus("Ended"));

        log.info("Daily schedule finished.");
    }


    @Scheduled(cron = "0 0 0 ? * WED")
    public void fetchSunToTue() {
        LocalDate sunday = LocalDate.now().with(DayOfWeek.SUNDAY);
        LocalDate tuesday = sunday.plusDays(2);

        List<ExternalEventDTOIn> events =
                serpService.fetchExternalEvents(sunday, tuesday);

        events.forEach(eDTOIn -> externalEventService.addExternalEventByAdmin(1,eDTOIn));
    }

    @Scheduled(cron = "0 0 9 ? * SUN")
    public void fetchWedToSat() {
        LocalDate wednesday = LocalDate.now().with(DayOfWeek.WEDNESDAY);
        LocalDate saturday = wednesday.plusDays(3);

        List<ExternalEventDTOIn> events =
                serpService.fetchExternalEvents(wednesday, saturday);

        events.forEach(eDTOIn -> externalEventService.addExternalEventByAdmin(1,eDTOIn));
    }
}
