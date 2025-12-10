package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.ExternalEventDTO;
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

    // get all external events (By admin)
    public List<ExternalEvent> getAllExternalEvents(Integer adminId) {
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null){
            throw new ApiException("admin not found");
        }
        return externalEventRepository.findAll();
    }


    // add a new external event (By admin)
    public  void addExternalEventByAdmin(Integer adminId, ExternalEventDTO externalEventDTO) {
        // check
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null){
            throw new ApiException("admin not found");
        }
        // add the external event information to the database and save it
       externalEventRepository.save(new ExternalEvent(null, externalEventDTO.getTitle(), externalEventDTO.getOrganizationName(),
                                                        externalEventDTO.getDescription(), externalEventDTO.getCity(),
                                                        externalEventDTO.getStartDate(), externalEventDTO.getEndDate(),
                                                        externalEventDTO.getStartTime() , externalEventDTO.getEndTime(),
                                                        externalEventDTO.getUrl() , "Active" , null , null ));
    }

    // update external event (By admin)
    public void updateExternalEventByAdmin(Integer adminId, Integer id, ExternalEventDTO externalEventDTO) {
        // check admin exists
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null) {
            throw new ApiException("admin not found");
        }
        // check external event exists
        ExternalEvent old = externalEventRepository.findExternalEventById(id);
        if (old == null) {
            throw new ApiException("external event not found");
        }
        // update from DTO to old
        old.setTitle(externalEventDTO.getTitle());
        old.setDescription(externalEventDTO.getDescription());
        old.setCity(externalEventDTO.getCity());
        old.setStart_date(externalEventDTO.getStartDate());
        old.setEnd_date(externalEventDTO.getEndDate());
        old.setStart_time(externalEventDTO.getStartTime());
        old.setEnd_time(externalEventDTO.getEndTime());
        old.setUrl(externalEventDTO.getUrl());

        externalEventRepository.save(old);
    }

    // delete external event from the database
    public  void deleteExternalEvent(Integer adminId, Integer id) {
       // check admin exists
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null){
            throw new ApiException("admin not found"); }
        // check external event exists
        ExternalEvent externalEvent = externalEventRepository.findExternalEventById(id);
        if (externalEvent == null) {
            throw new ApiException("external event not found");
        }
        // delete
        externalEventRepository.delete(externalEvent);
    }


    /// extra endpoints

    // add event by event owner
    public void requestEventByOwner (ExternalEventDTO externalEventDTO){
        // check first if owner exist
        EventOwner  owner = eventOwnerRepository.findEventOwnerById(externalEventDTO.getOwnerId());
        if (owner == null ){
            throw new ApiException("event owner not found !");
        }
        // save
        ExternalEvent externalEvent =  externalEventRepository.save(new ExternalEvent(null, externalEventDTO.getTitle(), externalEventDTO.getOrganizationName(),
                                                        externalEventDTO.getDescription(), externalEventDTO.getCity(),
                                                        externalEventDTO.getStartDate(), externalEventDTO.getEndDate(),
                                                        externalEventDTO.getStartTime() , externalEventDTO.getEndTime(),
                                                        externalEventDTO.getUrl() , "InActive" , null , owner ));
        // create an event request
        ExternalEventRequest request = new ExternalEventRequest(null, "Requested", null, externalEvent);
        // save
        externalEventRequestRepository.save(request);
    }


    // update event by event owner
    public void updateEventByOwner(Integer ownerId, Integer eventId, ExternalEventDTO externalEventDTO){
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
        event.setTitle(externalEventDTO.getTitle());
        event.setOrganizationName(externalEventDTO.getOrganizationName());
        event.setDescription(externalEventDTO.getDescription());
        event.setCity(externalEventDTO.getCity());
        event.setStart_date(externalEventDTO.getStartDate());
        event.setEnd_date(externalEventDTO.getEndDate());
        event.setStart_time(externalEventDTO.getStartTime());
        event.setEnd_time(externalEventDTO.getEndTime());
        event.setUrl(externalEventDTO.getUrl());
        // save
        externalEventRepository.save(event);
    }

    // delete  event by event owner
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


    // get all active events to show to users
    public List<ExternalEventDTO> getAllActiveExternalEvents() {
        // only get events that are active
        List<ExternalEvent> events = externalEventRepository.findExternalEventsByStatus("Active");
        // map regular event to DTO and return it
        return events.stream().map(event -> new ExternalEventDTO(
                event.getTitle(),
                event.getOrganizationName(),
                event.getDescription(),
                event.getCity(),
                event.getStart_date(),
                event.getEnd_date(),
                event.getStart_time(),
                event.getEnd_time(),
                event.getUrl(),
                event.getEventOwner() != null ? event.getEventOwner().getId() : null
        )).toList();
    }

    // get all active events to show to users
    public List<ExternalEventDTO> getAllInActiveExternalEvents() {
        // only get events that are active
        List<ExternalEvent> events = externalEventRepository.findExternalEventsByStatus("InActive");
        // map regular event to DTO and return it
        return events.stream().map(event -> new ExternalEventDTO(
                event.getTitle(),
                event.getOrganizationName(),
                event.getDescription(),
                event.getCity(),
                event.getStart_date(),
                event.getEnd_date(),
                event.getStart_time(),
                event.getEnd_time(),
                event.getUrl(),
                event.getEventOwner() != null ? event.getEventOwner().getId() : null
        )).toList();
    }



}
