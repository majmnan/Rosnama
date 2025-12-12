package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.InternalEventDTOIn;
import com.example.rosnama.DTO.InternalEventDTOOut;
import com.example.rosnama.DTO.InternalEventDTOOut;
import com.example.rosnama.Model.*;
import com.example.rosnama.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InternalEventService  {

    private final InternalEventRepository internalEventRepository;
    private final InternalEventRequestRepository internalEventRequestRepository;
    private final EventOwnerRepository eventOwnerRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    private final NotificationService notificationService;
    private final AdminRepository adminRepository;

    public List<InternalEvent> getAllAllInternalEvents(){
        return internalEventRepository.findAll();

    }

    public List<InternalEventDTOOut> convertToDtoOut(List<InternalEvent> events){
        return events.stream().map(event -> new InternalEventDTOOut(
                event.getTitle(),
                event.getDescription(),
                event.getCity(),
                event.getLocation(),
                event.getStartDate(),
                event.getEndDate(),
                event.getStartTime(),
                event.getEndTime(),
                event.getPrice(),
                event.getType(),
                event.getCategory().getName(),
                event.getDailyCapacity()
        )).toList();
    }

    public void addInternalEventByOwner(Integer ownerId, InternalEventDTOIn internalEventDTO , Integer adminId){

        EventOwner eventOwner = eventOwnerRepository.findEventOwnerById(ownerId);
        if (eventOwner == null) {
            throw new ApiException("Event owner not found");
        }

        Category category = categoryRepository.findCategoryById(internalEventDTO.getCategoryId());
        if (category == null) {
            throw new ApiException("Category not found");
        }

        InternalEvent event = new InternalEvent(
                null,
                internalEventDTO.getTitle(),
                internalEventDTO.getCity(),
                internalEventDTO.getLocation(),
                internalEventDTO.getDescription(),
                internalEventDTO.getStartDate(),
                internalEventDTO.getEndDate(),
                internalEventDTO.getStartTime(),
                internalEventDTO.getEndTime(),
                "InActive",
                internalEventDTO.getPrice(),
                internalEventDTO.getType(),
                null,
                eventOwner,
                category,
                null,
                internalEventDTO.getDailyCapacity()
        );
        internalEventRepository.save(event);
        internalEventRequestRepository.save(new InternalEventRequest(null, "Requested", null , event));


        //notify admin of the request
        List<Admin> admins = adminRepository.findAll();
        admins.forEach(admin ->
        notificationService.notify(
                admin.getEmail(),
                admin.getPhoneNumber(),
                "Event Price Negotiation",
                admin.getUsername(),
                " New external event:\n" + event.getTitle() + "request has been submitted by " + eventOwner.getUsername()
        ));
    }


    public void updateInternalEventByOwner(Integer ownerId, Integer eventId, InternalEventDTOIn internalEventDTO){
        EventOwner eventOwner = eventOwnerRepository.findEventOwnerById(ownerId);
        InternalEvent oldInternalEvent = internalEventRepository.findInternalEventById(eventId);

        if(eventOwner == null){
            throw new ApiException("Owner not found");
        }

        if(oldInternalEvent == null){
            throw new ApiException("InternalEvent not found");
        }

        if (!oldInternalEvent.getEventOwner().getId().equals(ownerId)) {
            throw new ApiException("You don't own this event");
        }

        oldInternalEvent.setTitle(internalEventDTO.getTitle());
        oldInternalEvent.setCity(internalEventDTO.getCity());
        oldInternalEvent.setDescription(internalEventDTO.getDescription());
        oldInternalEvent.setLocation(internalEventDTO.getLocation());
        oldInternalEvent.setStartDate(internalEventDTO.getStartDate());
        oldInternalEvent.setEndDate(internalEventDTO.getEndDate());
        oldInternalEvent.setStartTime(internalEventDTO.getStartTime());
        oldInternalEvent.setEndTime(internalEventDTO.getEndTime());
        oldInternalEvent.setPrice(internalEventDTO.getPrice());

        Category category = categoryRepository.findCategoryById(internalEventDTO.getCategoryId());
        if (category == null) throw new ApiException("Category not found");
        oldInternalEvent.setCategory(category);
        internalEventRepository.save(oldInternalEvent);

    }


    public void deleteInternalEventByOwner(Integer ownerId , Integer internalEventId){
        InternalEvent internalEvent =  internalEventRepository.findInternalEventById(internalEventId);
        if(internalEvent == null){
            throw new ApiException("InternalEvent Not found");
        }
        if (!internalEvent.getEventOwner().getId().equals(ownerId)) {
            throw new ApiException("You don't own this event");
        }

        internalEventRepository.delete(internalEvent);

    }

    public List<InternalEventDTOOut> getInternalEventsByType(String type) {
        return convertToDtoOut(internalEventRepository.findInternalEventsByType(type));
    }

    public List<InternalEventDTOOut> getInternalEventsByCity(String city) {
        return convertToDtoOut(internalEventRepository.findInternalEventsByCity(city));
    }

    public List<InternalEventDTOOut>getInternalEventByDateBetween(LocalDate endDateAfter, LocalDate endDateBefore){
        return convertToDtoOut(internalEventRepository.findInternalEventsByDateBetween(endDateAfter, endDateBefore));
    }

    public List<InternalEventDTOOut> getOngoingByCategory(Integer categoryId){
        Category category = categoryRepository.findCategoryById(categoryId);
        if(category == null)
            throw new ApiException("Category not found");

        return convertToDtoOut(internalEventRepository.findInternalEventsByStatusAndCategoryOrderByEndDateAsc("OnGoing",category));
    }






}
