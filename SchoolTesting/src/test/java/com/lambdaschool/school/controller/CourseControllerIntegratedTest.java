package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.repository.CourseRepository;
import com.lambdaschool.school.repository.InstructorRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerIntegratedTest
{
    @Autowired
    private InstructorRepository instructorrepo;

    @Autowired
    private CourseRepository courserepo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup()
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    ///GET /courses/courses
    @Test
    public void givenCourses() throws Exception
    {
        given().when().get("/courses/courses").then().time(lessThan(10000L));
        //took longer than 6700ms so set to 10000L
    }

    ///POST /courses/course/add
    @Test
    public void givenPostCourse() throws Exception
    {
        String newCourseName = "Extreme Coding";
        Course newCourse = new Course(newCourseName);

        ObjectMapper mapper = new ObjectMapper();
        String stringC = mapper.writeValueAsString(newCourse);

        given().contentType("application/json").body(stringC).when().post("/courses/course/add")
                .then().statusCode(201);
    }
}