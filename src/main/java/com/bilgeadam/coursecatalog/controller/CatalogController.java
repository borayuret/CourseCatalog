package com.bilgeadam.coursecatalog.controller;

import com.bilgeadam.coursecatalog.pojo.Course;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private EurekaClient client;

    @GetMapping
    public String getCatalogHome()
    {
        String courseAppMessage = "";
        //String courseAppUrl = "http://localhost:8081";

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instanceInfo =  client.getNextServerFromEureka("boost-course-app", false);
        String courseAppUrl = instanceInfo.getHomePageUrl();
        courseAppMessage = restTemplate.getForObject(courseAppUrl, String.class);

        return "CourseApp den gelen mesaj:" + courseAppMessage;

    }


    @GetMapping("/courses")
    public String getCourseCatalog()
    {
        String courseAppMessage = "";

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instanceInfo =  client.getNextServerFromEureka("boost-course-app", false);
        String courseAppUrl = instanceInfo.getHomePageUrl();
        courseAppUrl = courseAppUrl + "/courses";
        String courses = restTemplate.getForObject(courseAppUrl, String.class);

        return "CourseApp den gelen kurslar:" + courses;

    }

    @GetMapping("/coursesList")
    public List<Course> getCourseCatalogList()
    {
        String courseAppMessage = "";

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instanceInfo =  client.getNextServerFromEureka("boost-course-app", false);
        String courseAppUrl = instanceInfo.getHomePageUrl();
        courseAppUrl = courseAppUrl + "/courses";
        List<Course> courseList = restTemplate.getForObject(courseAppUrl, List.class);

        return courseList;

    }

    @GetMapping("/courses/{courseId}")
    public Course getACourse(@PathVariable("courseId") String courseId)
    {
        Course course = null;

        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instanceInfo =  client.getNextServerFromEureka("boost-course-app", false);
        String courseAppUrl = instanceInfo.getHomePageUrl();
        courseAppUrl = courseAppUrl + "/courses/" + courseId;
        course = restTemplate.getForObject(courseAppUrl, Course.class);

        return course;

    }

}
