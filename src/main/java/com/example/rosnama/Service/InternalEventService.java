package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.InternalEventDTO;
import com.example.rosnama.Model.*;
import com.example.rosnama.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalEventService  {

    private final InternalEventRepository internalEventRepository;
    private final InternalEventRequestRepository internalEventRequestRepository;
    private final EventOwnerRepository eventOwnerRepository;
    private final CategoryRepository categoryRepository;


    public List<InternalEvent> getAllAllInternalEvents(){
        return internalEventRepository.findAll();

    }


    public void addInternalEventByOwner(Integer ownerId, InternalEventDTO internalEventDTO) {

        EventOwner eventOwner = eventOwnerRepository.findEventOwnerById(ownerId);
        if (eventOwner == null) {
            throw new ApiException("Event owner not found");
        }

        Category category = categoryRepository.findCategoryById(internalEventDTO.getCategoryId());
        if (category == null) {
            throw new ApiException("Category not found");
        }

        InternalEvent internalEvent = new InternalEvent(
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
                null
        );
        internalEventRepository.save(internalEvent);
        internalEventRequestRepository.save(new InternalEventRequest(null, "Requested", internalEvent.getPrice() , internalEvent));
    }


    public void updateInternalEventByOwner(Integer ownerId, Integer eventId, InternalEventDTO internalEventDTOIn ){
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

        oldInternalEvent.setTitle(internalEventDTOIn.getTitle());
        oldInternalEvent.setCity(internalEventDTOIn.getCity());
        oldInternalEvent.setDescription(internalEventDTOIn.getDescription());
        oldInternalEvent.setLocation(internalEventDTOIn.getLocation());
        oldInternalEvent.setStartDate(internalEventDTOIn.getStartDate());
        oldInternalEvent.setEndDate(internalEventDTOIn.getEndDate());
        oldInternalEvent.setStartTime(internalEventDTOIn.getStartTime());
        oldInternalEvent.setEndTime(internalEventDTOIn.getEndTime());
        oldInternalEvent.setPrice(internalEventDTOIn.getPrice());

        Category category = categoryRepository.findCategoryById(internalEventDTOIn.getCategoryId());
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

    public List<InternalEvent> getInternalEventsByType(String type) {
        return internalEventRepository.findInternalEventsByType(type);
    }

    public List<InternalEvent> getInternalEventsByCity(String city) {
        return internalEventRepository.findInternalEventsByCity(city);
    }


}
