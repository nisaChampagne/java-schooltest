package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.model.Course;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
public class CourseServiceImplUnitTest
{

    @Autowired
    private CourseService courseService;

    @BeforeEach
    public void  setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void  tearDown() {
    }

    @Test
    public void  findAll()
    {
        assertEquals(12, courseService.findAll().size());
    }

    @Test
    public void  findCourseById()
    {
        assertEquals("TEST Java Back End", courseService.findCourseById(26).getCoursename());
    }

    @Test
    public void  save()
    {
        Course course = new Course("Not getting burnt out");
        Course addCourse = courseService.save(course);
        assertNotNull(addCourse);

        Course foundCourse = courseService.findCourseById(addCourse.getCourseid());
        assertEquals(addCourse.getCoursename(), foundCourse.getCoursename());
    }

    @Test
    public void  delete()
    {
        courseService.delete(26);
        assertEquals(11, courseService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotFound()
    {
        courseService.delete(5000);
        assertEquals(12, courseService.findAll().size());
    }
}