package com.stackroute.service;

import com.stackroute.Exception.TrackAlreadyExistException;
import com.stackroute.domain.Track;
import com.stackroute.repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrackServiceTest {

    Track track;

    //Create a mock for UserRepository
    @Mock
    TrackRepository trackRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    TrackServiceImpl trackService;
    List<Track> list = null;


    @Before
    public void setUp() {
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setName("John");
        track.setId(101);
        track.setComment("Jenny");
        list = new ArrayList<>();
        list.add(track);

    }
    @Test
    public void deleteTrack() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenReturn(true);
        Boolean result = trackService.deleteTrack(22);
        Assert.assertEquals(true, result);
    }

    @Test
    public void saveTrackTestSuccess() throws TrackAlreadyExistException {

        when(trackRepository.save((Track) any())).thenReturn(track);
        Track savedUser = trackService.saveTrack(track);
        Assert.assertEquals(track, savedUser);

        //verify here verifies that userRepository save method is only called once
        verify(trackRepository, times(1)).save(track);

    }

    @Test(expected = TrackAlreadyExistException.class)
    public void saveTrackTestFailure() throws TrackAlreadyExistException {
        when(trackRepository.save((Track) any())).thenReturn(null);
        Track savedUser = trackService.saveTrack(track);
        System.out.println("savedUser" + savedUser);
        //Assert.assertEquals(user,savedUser);

       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       userService.saveUser(user);*/

    }

    @Test
    public void updateTrack() throws Exception {
        when(trackRepository.existsById(track.getId())).thenReturn(true);
        track.setName("charitha");
        Track savedTrack = trackService.updateTrack(track);
        when(trackRepository.save((Track)any())).thenReturn(savedTrack);
        Assert.assertEquals("charitha",savedTrack.getName());
    }

    @Test
    public void getAllUser() {

        trackRepository.save(track);
        //stubbing the mock to return specific data
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> userlist = trackService.getAllTracks();
        Assert.assertEquals(list, userlist);
    }

}

