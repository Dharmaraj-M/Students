package com.trustrace.Students.controller;

import com.trustrace.Students.bos.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StudentServiceController {
    private static final Map<String, Student> studentMap = new HashMap<>();
    static {
        Student dharmaraj = new Student();
        dharmaraj.setStudentName("Dharmaraj");
        dharmaraj.setStudentRollNumber("171CS134");
        dharmaraj.setStudentDepartment("cse");
        studentMap.put(dharmaraj.getStudentRollNumber(), dharmaraj);

        Student deepak = new Student();
        deepak.setStudentName("Deepak");
        deepak.setStudentRollNumber("171CS130");
        deepak.setStudentDepartment("cse");
        studentMap.put(deepak.getStudentRollNumber(), deepak);
    }
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllStudents(@RequestParam(value = "limit", defaultValue = "5") int limit) {
        List<Student> myList = studentMap.values().stream()
                .limit(limit)
                .collect(Collectors.toList());
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{rollNumber}", method = RequestMethod.GET)
    public ResponseEntity<Object> getStudent(@PathVariable("rollNumber") String rollNumber) {
        return new ResponseEntity<>(studentMap.get(rollNumber), HttpStatus.OK);
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        studentMap.put(student.getStudentRollNumber(), student);
        return new ResponseEntity<>("Student is created successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/students/{rollNumber}", method = RequestMethod.PUT)
    public ResponseEntity<Object> replaceStudent(@PathVariable("rollNumber") String rollNumber, @RequestBody Student student) {
        studentMap.remove(rollNumber);
        student.setStudentRollNumber(rollNumber);
        studentMap.put(rollNumber, student);
        return new ResponseEntity<>("Student is replaced successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{rollNumber}", method = RequestMethod.PATCH)
    public ResponseEntity<Object> updateStudent(@PathVariable("rollNumber") String rollNumber, @RequestBody Student student) {
        student.setStudentRollNumber(rollNumber);
        student.setStudentDepartment(studentMap.get(rollNumber).getStudentDepartment());
        studentMap.put(rollNumber, student);
        return new ResponseEntity<>("Student Name is updated successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{rollNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteStudent(@PathVariable("rollNumber") String rollNumber) {
        studentMap.remove(rollNumber);
        return new ResponseEntity<>("Student is deleted successfully", HttpStatus.OK);
    }
}
