package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.ExternalEventDTOIn;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Model.ExternalEventRequest;
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
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");
        ExternalEvent externalEvent = externalEventRepository.findExternalEventById(id);

        // check if the external event exists
        if (externalEvent == null) {
            throw new ApiException("external event not found");
        }

        externalEventRepository.delete(externalEvent);
    }


    // add event by event owner
    public void requestEventByOwner (ExternalEventDTOIn externalEventDTOIn){

        // check first if owner exist
        EventOwner  owner = eventOwnerRepository.findEventOwnerById(externalEventDTOIn.getOwnerId());
        if (owner == null ){
            throw new ApiException("event owner not found !");
        }


        // save
        ExternalEvent externalEvent =  externalEventRepository.save(new ExternalEvent(null, externalEventDTOIn.getTitle(), externalEventDTOIn.getOrganizationName(),
                                                        externalEventDTOIn.getDescription(), externalEventDTOIn.getCity(),
                                                        externalEventDTOIn.getStartDate(), externalEventDTOIn.getEndDate(),
                                                        externalEventDTOIn.getStartTime() , externalEventDTOIn.getEndTime(),
                                                        externalEventDTOIn.getUrl() , "InActive" , null , owner ));


        // create an event request
        ExternalEventRequest request = new ExternalEventRequest(null, "Requested", null, externalEvent);

        // save
        externalEventRequestRepository.save(request);


    }


    // update event by event owner
    public void updateEventByOwner(Integer ownerId, Integer eventId, ExternalEventDTOIn dto){

        // check owner exists
        EventOwner owner = eventOwnerRepository.findEventOwnerById(ownerId);
        if(owner == null){
            throw new ApiException("event owner not found !");
        }

        // check event exists
        ExternalEvent event = externalEventRepository.findExternalEventById(eventId);
        if(event == null){
            throw new ApiException("event not found !");
        }

        // check if the owner who want to update it owns this event
        if(!event.getEventOwner().getId().equals(ownerId)){
            throw new ApiException("you do not own this event to update its information!");
        }

        // update
        event.setTitle(dto.getTitle());
        event.setOrganizationName(dto.getOrganizationName());
        event.setDescription(dto.getDescription());
        event.setCity(dto.getCity());
        event.setStart_date(dto.getStartDate());
        event.setEnd_date(dto.getEndDate());
        event.setStart_time(dto.getStartTime());
        event.setEnd_time(dto.getEndTime());
        event.setUrl(dto.getUrl());


        // save
        externalEventRepository.save(event);
    }


    public void deleteEventByOwner(Integer ownerId, Integer eventId){

        // check owner exists
        EventOwner owner = eventOwnerRepository.findEventOwnerById(ownerId);
        if(owner == null){
            throw new ApiException("event owner not found !");
        }

        // check event exists
        ExternalEvent event = externalEventRepository.findExternalEventById(eventId);
        if(event == null){
            throw new ApiException("event not found !");
        }

        // check if the owner who want to update it owns this event
        if(!event.getEventOwner().getId().equals(ownerId)){
            throw new ApiException("you do not own this event to update its information!");
        }

        // check if the request to this event to delete it
        if(event.getExternalEventRequest() != null){
            externalEventRequestRepository.delete(event.getExternalEventRequest());
        }

        externalEventRepository.delete(event);
    }

}
