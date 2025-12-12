package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.ExternalEventDTOIn;
import com.example.rosnama.DTO.ExternalEventDTOOut;
import com.example.rosnama.Model.*;
import com.example.rosnama.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.core.*;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalEventService {

    // connect to database
    private final AdminRepository adminRepository;
    private final ExternalEventRepository externalEventRepository;
    private final ExternalEventRequestRepository externalEventRequestRepository;
    private final EventOwnerRepository eventOwnerRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationService notificationService;

    // get all external events (By admin)
    public List<ExternalEvent> getAllExternalEvents(Integer adminId) {
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null){
            throw new ApiException("admin not found");
        }
        return externalEventRepository.findAll();
    }


    // add a new external event (By admin)
    public  void addExternalEventByAdmin(Integer adminId, ExternalEventDTOIn externalEventDTO) {
        // check admin exists
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");

        // check category exists
        Category category = categoryRepository.findCategoryById(externalEventDTO.getCategoryId());
        if (category == null)
            throw new ApiException("Category not found");

        // add the external event information to the database and save it
        ExternalEvent event = new ExternalEvent(
                null, externalEventDTO.getTitle(), externalEventDTO.getOrganizationName(), externalEventDTO.getDescription(),
                externalEventDTO.getCity(), externalEventDTO.getStartDate(), externalEventDTO.getEndDate(),
                externalEventDTO.getStartTime(), externalEventDTO.getEndTime(), externalEventDTO.getUrl(),
                "InActive", externalEventDTO.getType(), null, null, category);

        // set category
        // save

         }

    // update external event (By admin)
    public void updateExternalEventByAdmin(Integer adminId, Integer id, ExternalEventDTOIn externalEventDTO) {
        // check admin exists
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null) throw new ApiException("admin not found");
        // check external event exists
        ExternalEvent old = externalEventRepository.findExternalEventById(id);
        if (old == null) throw new ApiException("external event not found");
        // update from DTO to old
        old.setTitle(externalEventDTO.getTitle());
        old.setDescription(externalEventDTO.getDescription());
        old.setCity(externalEventDTO.getCity());
        old.setStartDate(externalEventDTO.getStartDate());
        old.setEndDate(externalEventDTO.getEndDate());
        old.setStartTime(externalEventDTO.getStartTime());
        old.setEndTime(externalEventDTO.getEndTime());
        old.setUrl(externalEventDTO.getUrl());
        old.setType(externalEventDTO.getType());
        // update category
        Category category = categoryRepository.findCategoryById(externalEventDTO.getCategoryId());
        if (category == null) throw new ApiException("Category not found");
        old.setCategory(category);
        // save
        externalEventRepository.save(old);
    }

    // delete external event from the database by admin
    public  void deleteExternalEvent(Integer adminId, Integer id) {
       // check admin exists
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null) throw new ApiException("admin not found");
        // check external event exists
        ExternalEvent externalEvent = externalEventRepository.findExternalEventById(id);
        if (externalEvent == null) throw new ApiException("external event not found");
        // delete
        externalEventRepository.delete(externalEvent);
    }


    /// extra endpoints

    // add event by event owner
    public void requestEventByOwner (ExternalEventDTOIn externalEventDTO){
        // check first if owner exist
        EventOwner  owner = eventOwnerRepository.findEventOwnerById(externalEventDTO.getOwnerId());
        if (owner == null ) throw new ApiException("event owner not found !");
        // check category exists
        Category category = categoryRepository.findCategoryById(externalEventDTO.getCategoryId());
        if (category == null) throw new ApiException("Category not found");
        // save
        ExternalEvent event = new ExternalEvent(
                null, externalEventDTO.getTitle(), externalEventDTO.getOrganizationName(), externalEventDTO.getDescription(),
                externalEventDTO.getCity(), externalEventDTO.getStartDate(), externalEventDTO.getEndDate(),
                externalEventDTO.getStartTime(), externalEventDTO.getEndTime(), externalEventDTO.getUrl(),
                "InActive", externalEventDTO.getType(), null, owner, category);

        externalEventRepository.save(event);
        ExternalEventRequest request = new ExternalEventRequest(null, "Requested", null, event);
        externalEventRequestRepository.save(request);

        //notify admin of the request
        notificationService.notify(
                "admin@email.com",
                "9665xxxxxxxx",
                "Event Price Negotiation",
                "Admin",
                " New external event request has been submited by " + owner.getUsername()
        );
    }


    // update event by event owner
    public void updateEventByOwner(Integer ownerId, Integer eventId, ExternalEventDTOIn externalEventDTO){

        // check owner exists
        EventOwner owner = eventOwnerRepository.findEventOwnerById(ownerId);
        if(owner == null) throw new ApiException("event owner not found !");

        // check event exists
        ExternalEvent old = externalEventRepository.findExternalEventById(eventId);
        if(old == null) throw new ApiException("event not found !");

        // check if the owner who want to update it owns this event
        if (!old.getEventOwner().getId().equals(ownerId)) throw new ApiException("You do not own this event");

        // update
        old.setTitle(externalEventDTO.getTitle());
        old.setDescription(externalEventDTO.getDescription());
        old.setCity(externalEventDTO.getCity());
        old.setStartDate(externalEventDTO.getStartDate());
        old.setEndDate(externalEventDTO.getEndDate());
        old.setStartTime(externalEventDTO.getStartTime());
        old.setEndTime(externalEventDTO.getEndTime());
        old.setUrl(externalEventDTO.getUrl());
        old.setType(externalEventDTO.getType());
        // check category exists and update
        Category category = categoryRepository.findCategoryById(externalEventDTO.getCategoryId());
        if (category == null) throw new ApiException("Category not found");
        old.setCategory(category);
        // save
        externalEventRepository.save(old);
    }

    // delete  event by event owner
    public void deleteEventByOwner(Integer ownerId, Integer eventId){
        // check owner exists
        EventOwner owner = eventOwnerRepository.findEventOwnerById(ownerId);
        if(owner == null)
            throw new ApiException("event owner not found !");

        // check event exists
        ExternalEvent event = externalEventRepository.findExternalEventById(eventId);
        if(event == null)
            throw new ApiException("event not found !");

        // check if the owner who want to update it owns this event
        if(!event.getEventOwner().getId().equals(ownerId))
            throw new ApiException("you do not own this event to update its information!");

        // delete
        externalEventRepository.delete(event);
    }

    //get OnGoing Events
    public List<ExternalEventDTOOut> getOnGoingExternalEvents(){
        return convertToDtoOut(externalEventRepository.findExternalEventsByStatusOrderByEndDateAsc("OnGoing"));
    }

    // get all active events to show to users
    public List<ExternalEventDTOOut> getUpcomingExternalEvents() {
        return convertToDtoOut(externalEventRepository.findExternalEventsByStatusOrderByStartDateAsc("Upcoming"));
    }

    // get events between two dates
    public List<ExternalEventDTOOut> getEventsOnGoingBetween(LocalDate after, LocalDate before){
        return convertToDtoOut(externalEventRepository.findExternalEventsByDateBetween(after, before));
    }

    // get events by type
    public List<ExternalEventDTOOut> getEventsByType(String type) {
        return convertToDtoOut(externalEventRepository.findExternalEventsByType(type));
    }

    // get events by city
    public List<ExternalEventDTOOut> getEventsByCity(String city) {
        return convertToDtoOut(externalEventRepository.findExternalEventsByCity(city));
    }

    // get OnGoing events by category
    public List<ExternalEventDTOOut> getOngoingByCategory(Integer categoryId){
        Category category = categoryRepository.findCategoryById(categoryId);
        if(category == null)
            throw new ApiException("Category not found");

        return convertToDtoOut(externalEventRepository.findExternalEventsByStatusAndCategoryOrderByEndDateAsc("OnGoing",category));
    }



    public List<ExternalEventDTOOut> convertToDtoOut(List<ExternalEvent> events){
        return events.stream().map(event -> new ExternalEventDTOOut(
                event.getTitle(), event.getOrganizationName(),
                event.getDescription(), event.getCity(),
                event.getStartDate(), event.getEndDate(),
                event.getStartTime(), event.getEndTime(),
                event.getUrl(), event.getType(),
                event.getCategory().getName()
        )).toList();
    } 
}
