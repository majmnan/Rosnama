package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.InternalEventDTOIn;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Model.User;
import com.example.rosnama.Repository.AdminRepository;
import com.example.rosnama.Repository.EventOwnerRepository;
import com.example.rosnama.Repository.InternalEventRepository;
import com.example.rosnama.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalEventService  {

    private final InternalEventRepository internalEventRepository;
   private final EventOwnerRepository eventOwnerRepository;


    public List<InternalEvent> getAllAllInternalEvents(){
        return internalEventRepository.findAll();

    }


    public void addInternalEventByOwner(Integer event_owner_id , InternalEventDTOIn internalEventDTOIn) {

        EventOwner eventOwner = eventOwnerRepository.findEventOwnerById(event_owner_id);
        if (eventOwner == null) {
            throw new ApiException("EventOwner not found");
        }

        InternalEvent internalEvent = new InternalEvent();

        internalEvent.setTitle(internalEventDTOIn.getTitle());
        internalEvent.setDescription(internalEventDTOIn.getDescription());
        internalEvent.setCity(internalEventDTOIn.getCity());
        internalEvent.setStart_date(internalEventDTOIn.getStart_date());
        internalEvent.setEnd_time(internalEventDTOIn.getEnd_time());
        internalEvent.setStart_time(internalEventDTOIn.getStart_time());
        internalEvent.setEnd_time(internalEventDTOIn.getEnd_time());

        internalEvent.setStatus("InActive");

        internalEvent.setEventOwner(eventOwner);
        internalEventRepository.save(internalEvent);
    }



}
