package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.InternalEventDTOIn;
import com.example.rosnama.Model.*;
import com.example.rosnama.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.ObjLongConsumer;

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


        InternalEvent internalEvent = new InternalEvent(null,internalEventDTOIn.getTitle(),internalEventDTOIn.getCity(),internalEventDTOIn.getLocation(),internalEventDTOIn.getDescription(),internalEventDTOIn.getStartDate(),internalEventDTOIn.getEndDate(),internalEventDTOIn.getStartTime(),internalEventDTOIn.getEndTime(), "InActive", internalEventDTOIn.getPrice(), null , eventOwner , null);
        internalEventRepository.save(internalEvent);
        internalEventRequestRepository.save(new InternalEventRequest(null, internalEvent, "Requested", internalEvent.getPrice()));
    }


    public void updateInternalEventByOwner(Integer owner_id , Integer event_id, InternalEventDTOIn internalEventDTOIn ){
        EventOwner eventOwner = eventOwnerRepository.findEventOwnerById(owner_id);
        InternalEvent oldInternalEvent = internalEventRepository.findInternalEventById(event_id);

        if(eventOwner == null){
            throw new ApiException("Owner not found");
        }

        if(oldInternalEvent == null){
            throw new ApiException("InternalEvent not found");
        }

        oldInternalEvent.setTitle(internalEventDTOIn.getTitle());
        oldInternalEvent.setStart_time(internalEventDTOIn.getStartTime());
        oldInternalEvent.setEnd_time(internalEventDTOIn.getEndTime());
        oldInternalEvent.setStart_date(internalEventDTOIn.getStartDate());
        oldInternalEvent.setEnd_date(internalEventDTOIn.getEndDate());
        oldInternalEvent.setPrice(internalEventDTOIn.getPrice());
        oldInternalEvent.setLocation(internalEventDTOIn.getLocation());
        oldInternalEvent.setDescription(internalEventDTOIn.getDescription());
        oldInternalEvent.setCity(internalEventDTOIn.getCity());
        internalEventRepository.save(oldInternalEvent);

    }


    public void deleteInternalEventByOwner(Integer owner_id ){
        InternalEvent oldInternalEvent =  internalEventRepository.findInternalEventById(owner_id);

        if(oldInternalEvent == null){
            throw new ApiException("InternalEvent Not found");
        }
        internalEventRepository.delete(oldInternalEvent);

    }





}
