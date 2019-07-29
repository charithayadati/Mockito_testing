package com.stackroute.repository;


import com.stackroute.domain.Track;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;
    Track track;

    @Before
    public void setUp()
    {
       track = new Track();
        track.setName("John");
        track.setId(101);
        track.setComment("Jenny");
    }

    @After
    public void tearDown(){

       trackRepository.deleteAll();
    }


    @Test
    public void testSaveTrack(){
     trackRepository.save(track);
        Track fetchUser = trackRepository.findById(track.getId()).get();
        Assert.assertEquals(101,fetchUser.getId());

    }

    @Test
    public void testSaveTrackFailure(){
        Track testUser = new Track(34,"Harry123","sthg");
       trackRepository.save(track);
        Track fetchUser = trackRepository.findById(track.getId()).get();
        Assert.assertNotSame(testUser,track);
    }

    @Test
    public void testGetAllTracks() {
        Track u = new Track(10, "Johny", "sthg");
        Track u1 = new Track(11, "Harry", "nthg");
        trackRepository.save(u);
        trackRepository.save(u1);
        List<Track> list = trackRepository.findAll();
        Assert.assertEquals("Johny", list.get(0).getName());
        Assert.assertEquals("Harry",list.get(1).getName());
    }

    @Test
    public void updateTrack() throws Exception {
        track.setName("cherry");
       trackRepository.save(track);
       Assert.assertEquals(101,track.getId());
        Assert.assertEquals("cherry",track.getName());

    }
    @Test
    public void deleteTrack() {
        Track newTrack=new Track(22,"cherry","nthg");
        trackRepository.save(newTrack);
         trackRepository.deleteById(22);
         Boolean result=trackRepository.existsById(22);
        Assert.assertEquals(false, result);
    }

}

