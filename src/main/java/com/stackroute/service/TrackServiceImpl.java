package com.stackroute.service;

import com.stackroute.Exception.TrackAlreadyExistException;
import com.stackroute.domain.Track;
import com.stackroute.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    TrackRepository trackRepository;

    // Providing implementation for all methods of track
    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }


    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistException
    {
        if (trackRepository.existsById(track.getId())) {
            throw new TrackAlreadyExistException("Track Already exist");
        }
        Track savetrack = trackRepository.save(track);

        if (savetrack == null) {
            throw new TrackAlreadyExistException("Track already present");
        }
        return savetrack;
    }

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public Track updateTrack(Track track) throws Exception {
        Track trackobj=new Track();
        if (trackRepository.existsById(track.getId())) {
           trackobj.setComment(track.getComment());
           trackobj.setName(track.getName());
            trackRepository.save(trackobj);
            return trackobj;
        } else {
            throw new Exception("Track not found");
        }
    }

    @Override
    public boolean deleteTrack(int id)  {
        Track track = new Track();
        if (trackRepository.existsById(track.getId())) {
            trackRepository.deleteById(id);
            return true;
        }
        return  false;
    }

}
