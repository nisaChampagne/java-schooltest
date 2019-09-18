package com.lambdaschool.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.CourseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseController.class, secure = false)
class CourseControllerUnitTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private ArrayList<Course> courseList;

    @BeforeEach
    void setUp() throws Exception
    {
        courseList = new ArrayList<>();

        Instructor i1 = new Instructor("Sally");

        Instructor i2 = new Instructor("Lucy");

        Instructor i3 = new Instructor("Charlie");


        Course c1 = new Course("Data Science", i1);
        courseList.add(c1);
        Course c2 = new Course("JavaScript", i1);
        courseList.add(c2);
        Course c3 = new Course("Node.js", i1);
        courseList.add(c3);
        Course c4 = new Course("Java Back End", i2);
        courseList.add(c4);
        Course c5 = new Course("Mobile IOS", i2);
        courseList.add(c5);
        Course c6 = new Course("Mobile Android", i3);
        courseList.add(c6);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void listAllCourses() throws Exception {
        String apiUrl = "/courses/courses";
        //mock up service
        Mockito.when(courseService.findAll()).thenReturn(courseList);

        //build the request
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        //perform and return the request into a result
        MvcResult r = mockMvc.perform(rb).andReturn();

        String tr = r.getResponse().getContentAsString();

        //create object mapper and pick one fasterxml.jackson
        //can put any object in here and it will write it as a string in JSON format
        //puts in string called er(expected results)
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        System.out.println("Expected " + er);
        System.out.println("Actual " + tr);
        assertEquals("Rest API Returns List ", er, tr);
    }

    @Test
    void addNewCourse() throws Exception {
        String apiUrl = "/courses/course/add";

        String courseTitle = "Rust Programming";
        Course createdCourse = new Course(courseTitle, null);
        createdCourse.setCourseid(18_356);
        ObjectMapper mapper = new ObjectMapper();
        String courseString = mapper.writeValueAsString(createdCourse);

        Mockito.when(courseService.save(any(Course.class))).thenReturn(createdCourse);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(courseString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }
}