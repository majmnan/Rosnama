package com.example.rosnama.Service;

import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Repository.InternalEventRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalEventRequestService {
    private final InternalEventRequestRepository internalEventRequestRepository;



    public List<InternalEvent>getAllEventsRequest(){
        
    }
}
