package com.stackroute.controller;

import com.stackroute.Exception.TrackAlreadyExistException;
import com.stackroute.domain.Track;
import com.stackroute.repository.TrackRepository;
import com.stackroute.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1")
public class TrackController
{

    private TrackRepository trackRepository;

    // Declaration
    TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService)
    {
        this.trackService = trackService;
    }


    // Implementing POST method
    @PostMapping("savetrack")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) throws TrackAlreadyExistException
    {
        ResponseEntity responseEntity;
        try{
            trackService.saveTrack(track);
            responseEntity=new ResponseEntity("Successfully created", HttpStatus.CREATED);
        }
        catch (TrackAlreadyExistException e)
        {
            responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;

    }

    // Implementing GET method
    @GetMapping("gettrack")
    public ResponseEntity<?> getAllTracks()
    {
        return new ResponseEntity<List<Track>>(trackService.getAllTracks(),HttpStatus.OK);
    }

    // Implementing PUT method
    @PutMapping("updatetrack")
    public ResponseEntity<?> updateTrack(@RequestBody Track track)
    {
        ResponseEntity responseEntity;
        try{
            trackService.updateTrack(track);
            responseEntity=new ResponseEntity("Successfully updated", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }


    // Implementing DELETE method
    @DeleteMapping("deletetrack/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable("id") int id)
    {
        ResponseEntity responseEntity;
        try {
            trackService.deleteTrack(id);
            responseEntity = new ResponseEntity("Successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }


}
