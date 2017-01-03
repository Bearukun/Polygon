package Tests;

import dataAccessLayer.mappers.BuildingMapper;
import dataAccessLayer.mappers.UserMapper;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;

public class ProjectTest {
    
    BuildingMapper bm = new BuildingMapper();
    UserMapper um = new UserMapper();
    
    public ProjectTest() {
    }

//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
    
    @Test
    public void testGetUserBuildings() throws Exception {
        System.out.println("getUserBuildings");
        ArrayList<Building> result = bm.getBuildings(3);
        assertTrue("Elements in ArrayList: "+result.size(), result.size()>0);

    }
    
    @Test
    public void testGetAllBuildings() throws Exception {
        System.out.println("getAllBuildings");
        ArrayList<Building> result = bm.getAllBuildings();
        assertTrue("Elements in ArrayList: "+result.size(), result.size()>0);
    }
    
    @Test
    public void testGetBuildingAreas() throws Exception {
        System.out.println("getBuildingAreas");
        ArrayList<Area> result = bm.getAreas(1);
        assertTrue("Elements in ArrayList: "+result.size(), result.size()>0);
    }
    
    @Test
    public void testGetAreaRooms() throws Exception {
        System.out.println("getAreaRooms");
        ArrayList<Room> result = bm.getRooms(1);
        assertTrue("Elements in ArrayList: "+result.size(), result.size()>0);
    }
    
    @Test
    public void testGetAllUsers() throws Exception {
        System.out.println("getAllUsers");
        ArrayList<User> result = um.getUsers();
        assertTrue("Elements in ArrayList: "+result.size(), result.size()>0);
    }
    
    
}