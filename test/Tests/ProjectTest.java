package Tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dataAccessLayer.mappers.BuildingMapper;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;

/**
 *
 * @author Martin
 */
public class ProjectTest {
    
    BuildingMapper bm = new BuildingMapper();
    
    public ProjectTest() {
    }

    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testGetBuildings() throws Exception {
        System.out.println("getBuildings");
        ArrayList<Building> result = bm.getBuildings(1);
        assertTrue("Elements in ArrayList: "+result.size(), result.size()>0);
    }
    
    @Test
    public void testGetAllBuildings() throws Exception {
        System.out.println("getAllBuildings");
        ArrayList<Building> result = bm.getAllBuildings();
        assertTrue("Elements in ArrayList: "+result.size(), result.size()>0);
    }
    
    @Test
    public void testGetAreas() throws Exception {
        System.out.println("getAreas");
        ArrayList<Area> result = bm.getAreas(1);
        assertTrue("Elements in ArrayList: "+result.size(), result.size()>0);
    }
}