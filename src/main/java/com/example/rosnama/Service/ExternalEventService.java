package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Repository.AdminRepository;
import com.example.rosnama.Repository.ExternalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalEventService {

    // connect to database
    private final AdminRepository adminRepository;
    private final ExternalEventRepository externalEventRepository;


    // get all external events from the database
    public List<ExternalEvent> getAllExternalEvents() {
        return externalEventRepository.findAll();
    }


    // add a new external event to the database
    public  void addExternalEvent(ExternalEvent externalEvent) {

        // set status to not active
        externalEvent.setStatus("not active");

        externalEventRepository.save(externalEvent);
    }


    // update external event in the database
    public void updateExternalEvent(Integer id, ExternalEvent externalEvent) {


        ExternalEvent old = externalEventRepository.findExternalEventById(id);

        // check if the old external event exists
        if (old == null) {
            throw new ApiException("external event not found");
        }

        // update
        old.setTitle(externalEvent.getTitle());
        old.setOwner_id(externalEvent.getOwner_id());
        old.setOrganizer_name(externalEvent.getOrganizer_name());
        old.setDescription(externalEvent.getDescription());
        old.setCity(externalEvent.getCity());
        old.setStart_date(externalEvent.getStart_date());
        old.setEnd_date(externalEvent.getEnd_date());
        old.setStart_time(externalEvent.getStart_time());
        old.setEnd_time(externalEvent.getEnd_time());
        old.setUrl(externalEvent.getUrl());

        old.setStatus("not active");

        // save
        externalEventRepository.save(old);
    }

    // delete external event from the database
    public  void deleteExternalEvent(Integer id) {

        ExternalEvent externalEvent = externalEventRepository.findExternalEventById(id);

        // check if the external event exists
        if (externalEvent == null) {
            throw new ApiException("external event not found");
        }

        externalEventRepository.delete(externalEvent);
    }

    ///  extra endpoint

    // addmin add external event to the system
    public void addExternalEventByAdmin(Integer admin_id, ExternalEvent externalEvent) {

        Admin admin = adminRepository.findAdminById(admin_id);
        // check if the admin exists
        if (admin == null) {
            throw new ApiException("admin not found");
        }

        externalEvent.setStatus("not active ");
        externalEventRepository.save(externalEvent);

    }

    // admin publesh the event to user
    public  void publishExternalEvent(Integer admin_id, Integer event_id) {

        Admin admin = adminRepository.findAdminById(admin_id);
        if(admin == null){
            throw new ApiException("admen not found");
        }

        ExternalEvent externalEvent = externalEventRepository.findExternalEventById(eventId);
        if(externalEvent == null){
            throw new ApiException("extaernal event not foand");
        }

        externalEvent.setStatus("actave");
        externalEventRepository.save(externalEvent);

    }


}
