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

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void dailyChanges(){
        log.info("Daily schedule started...");

        //make events OnGoing
        externalEventRepository.findExternalEventsByStartDate(LocalDate.now())
                .forEach(e -> e.setStatus("OnGoing"));
        internalEventRepository.findInternalEventByStartDate(LocalDate.now())
                .forEach(e -> e.setStatus("OnGoing"));

        //make registration active
        registrationRepository.findRegistrationsByDate(LocalDate.now())
                .forEach(r -> r.setStatus("Active"));
        //remind users of their active registration

        //remind users of their TOMORROW registration


        //make registration ended
        registrationRepository.findRegistrationsByDateAndStatus(LocalDate.now().minusDays(1),"Active")
                .forEach( r -> r.setStatus("Expired"));

        //make events ended
        externalEventRepository.findExternalEventsByEndDate(LocalDate.now().minusDays(1))
                .forEach(e -> e.setStatus("Ended"));
        internalEventRepository.findInternalEventByEndDate(LocalDate.now().minusDays(1))
                .forEach(e -> e.setStatus("Ended"));

        log.info("Daily schedule finished.");
    }
}
