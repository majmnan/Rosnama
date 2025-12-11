package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.RegistrationDTOIn;
import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Model.Registration;
import com.example.rosnama.Model.User;
import com.example.rosnama.Repository.InternalEventRepository;
import com.example.rosnama.Repository.RegistrationRepository;
import com.example.rosnama.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final InternalEventRepository internalEventRepository;

    public void addRegistration(RegistrationDTOIn registrationDTOIn){
        User user = userRepository.findUserById(registrationDTOIn.getUserId());
        if(user == null)
            throw new ApiException("user not found");

        InternalEvent event = internalEventRepository.findInternalEventById(registrationDTOIn.getEventId());
        if(event == null)
            throw new ApiException("event not found");

        if(registrationDTOIn.getDate().isBefore(LocalDate.now()))
            throw new ApiException("can't register in past");

        if(event.getStatus().equals("InActive") || event.getStatus().equals("ended"))
            throw new ApiException("event not active");

        if(!registrationDTOIn.getDate().isBefore(event.getEndDate()) && registrationDTOIn.getDate().isAfter(event.getStartDate()))
            throw new ApiException("enter date is out of event date");

        //if(internalEvent.getRegistrations().size() >= capacity) throw new ApiException(event is full)
        if(event.getRegistrations().size()>= event.getDailyCapacity()){
            throw new ApiException("Event is full");
        }
        registrationRepository.save(new Registration(
                null,
                user,
                event,
                registrationDTOIn.getDate(),
                "Registered"
        ));

    }

    public void useRegistration(Integer registrationId){
        Registration registration = registrationRepository.findRegistrationById(registrationId);
        if(registration == null)
            throw new ApiException("registration not found");
        if(!registration.getDate().equals(LocalDate.now()))
            throw new ApiException("registration is not for today");
        if(registration.getStatus().equals("Used"))
            throw new ApiException("registration already used");

        registration.setStatus("Used");
        registrationRepository.save(registration);


    }

    public List<Registration> findRegistrationByInternalEventAndDate(InternalEvent internalEvent, LocalDate date){
        List <Registration> registrations = registrationRepository.findRegistrationByInternalEventAndDate(internalEvent,date);

        if(registrations.isEmpty()){
            throw new ApiException("No Registration found");
        }
        return registrations;
    }
}
