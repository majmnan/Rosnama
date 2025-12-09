package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.ExternalEventDTOIn;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Repository.AdminRepository;
import com.example.rosnama.Repository.EventOwnerRepository;
import com.example.rosnama.Repository.ExternalEventRepository;
import com.example.rosnama.Repository.ExternalEventRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalEventService {

    // connect to database
    private final AdminRepository adminRepository;
    private final ExternalEventRepository externalEventRepository;
    private final ExternalEventRequestRepository externalEventRequestRepository;
    private final EventOwnerRepository eventOwnerRepository;

    public List<ExternalEvent> getAllExternalEvents(Integer adminId) {
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");
        return externalEventRepository.findAll();
    }


    // add a new external event (By admin)
    public  void addExternalEventByAdmin(Integer adminId, ExternalEvent externalEvent) {
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");
        externalEvent.setStatus("active");
        externalEventRepository.save(externalEvent);
    }


    public void updateExternalEventByAdmin(Integer adminId, Integer id, ExternalEvent externalEvent) {
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");

        ExternalEvent old = externalEventRepository.findExternalEventById(id);
        if (old == null) {
            throw new ApiException("external event not found");
        }

        old.setTitle(externalEvent.getTitle());
        old.setDescription(externalEvent.getDescription());
        old.setCity(externalEvent.getCity());
        old.setStart_date(externalEvent.getStart_date());
        old.setEnd_date(externalEvent.getEnd_date());
        old.setStart_time(externalEvent.getStart_time());
        old.setEnd_time(externalEvent.getEnd_time());
        old.setUrl(externalEvent.getUrl());
        old.setStatus("active");

        externalEventRepository.save(old);
    }

    // delete external event from the database
    public  void deleteExternalEvent(Integer adminId, Integer id) {

        ExternalEvent externalEvent = externalEventRepository.findExternalEventById(id);

        // check if the external event exists
        if (externalEvent == null) {
            throw new ApiException("external event not found");
        }

        externalEventRepository.delete(externalEvent);
    }

    ///  extra endpoints :

    //add event by event owner
    //event created with status "InActive"
    //external event request created and saved to database with status "Requested"

    // add event by event owner
    public void addEventByOwner (ExternalEventDTOIn externalEventDTOIn){

        // check first if owner exist
        EventOwner  owner = eventOwnerRepository.findEventOwnerById(externalEventDTOIn.getOwnerId());
        if (owner == null ){
            throw new ApiException("event owner not found !");
        }


        // save
        externalEventRepository.save(new ExternalEvent(null, externalEventDTOIn.getTitle(), externalEventDTOIn.getOrganizationName(),
                                                        externalEventDTOIn.getDescription(), externalEventDTOIn.getCity(),
                                                        externalEventDTOIn.getStart_date(), externalEventDTOIn.getEnd_date(),
                                                        externalEventDTOIn.getStart_time() , externalEventDTOIn.getEnd_time(),
                                                        externalEventDTOIn.getUrl() , null , null , null ));


        // create an event request
        ExternalEventRequest request 


    }



}
