package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Repository.EventOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventOwnerService {
    private final EventOwnerRepository eventOwnerRepository;

    public List<EventOwner> getEventOwners(){
        return eventOwnerRepository.findAll();
    }

    public void addEventOwner(EventOwner eventOwner){
        eventOwnerRepository.save(eventOwner);
    }

    public void updateEventOwner(Integer id, EventOwner eventOwner){
        EventOwner old = eventOwnerRepository.findEventOwnerById(id);
        if(old == null)
            throw new ApiException("event owner not found");

        old.setEmail(eventOwner.getEmail());
        old.setUsername(eventOwner.getUsername());
        old.setPassword(eventOwner.getPassword());
        old.setUrl(eventOwner.getUrl());
        eventOwnerRepository.save(old);
    }

    public void deleteEventOwner(Integer id){
        EventOwner eventOwner = eventOwnerRepository.findEventOwnerById(id);
        if(eventOwner == null)
            throw new ApiException("event owner not found");

        eventOwnerRepository.delete(eventOwner);
    }
}
