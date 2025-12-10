package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.InternalEventDTOIn;
import com.example.rosnama.Model.*;
import com.example.rosnama.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalEventService  {

    private final InternalEventRepository internalEventRepository;
    private final InternalEventRequestRepository internalEventRequestRepository;
   private final EventOwnerRepository eventOwnerRepository;


    public List<InternalEvent> getAllAllInternalEvents(){
        return internalEventRepository.findAll();

    }


    public void addInternalEventByOwner(Integer event_owner_id , InternalEventDTOIn internalEventDTOIn) {

        EventOwner eventOwner = eventOwnerRepository.findEventOwnerById(event_owner_id);
        if (eventOwner == null) {
            throw new ApiException("EventOwner not found");
        }

        InternalEvent internalEvent = new InternalEvent(null,internalEventDTOIn.getTitle(), internalEventDTOIn.getCity(), internalEventDTOIn.getLocation(), internalEventDTOIn.getDescription(), internalEventDTOIn.getStartDate(), internalEventDTOIn.getEndDate(), internalEventDTOIn.getStartTime(), internalEventDTOIn.getEndTime(), "InActive", eventOwner);
        internalEventRequestRepository.save(new InternalEventRequest(null, internalEvent, "Requested", null));

        internalEvent.setEventOwner(eventOwner);
        internalEventRepository.save(internalEvent);
    }



}
