package com.ibik.academic.academicservices.student;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibik.academic.academicservices.dto.ResponseData;
import com.ibik.academic.academicservices.dto.SearchData;

import io.micrometer.core.instrument.search.Search;


@RestController
@RequestMapping("/api/student")
public class StudentController {
    
    @Autowired
    private StudentServices studentServices;

    @PostMapping("/search")
    public ResponseEntity<ResponseData<Student>> getStudentByName(@RequestBody SearchData searchData) {
        ResponseData<Student> responseData = new ResponseData<>();
        try {
            Iterable<Student> values = studentServices.findStudentByName(searchData.getSearchKey());
            responseData.setResult(true);
            responseData.setMessage(null);
            responseData.setData(values);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            List<String> message = new ArrayList<>();
            message.add(e.getMessage());
            responseData.setData(null);
            responseData.setResult(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
    }

    @PostMapping()
    // public Student postStudent(@Valid @RequestBody Student student, Errors errors){
    public ResponseEntity<ResponseData<Student>> postStudent (@Valid @RequestBody Student student, Errors errors) {
        ResponseData<Student> responseData = new ResponseData<>();
        
        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                //System.out.println(error.getDefaultMessage());
                responseData.getMessage().add(error.getDefaultMessage());
            }

            responseData.setResult(false);
            responseData.setData(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            //throw new RuntimeException("Validation Error");
        }

        responseData.setResult(true);
        List<Student> value = new ArrayList<>();
        value.add(studentServices.save(student));
        responseData.setData(value);
        return ResponseEntity.ok(responseData);
        //return studentServices.save(student);
    }//untuk memasukan data

    @GetMapping
    //public Iterable<Student> fetchPrograms(){
    public ResponseEntity<ResponseData<Student>> fetchStudent() {
        ResponseData<Student> responseData = new ResponseData<>();
        try {
            responseData.setResult(true);
            List<Student> value = (List<Student>) studentServices.findAll();
            responseData.setData(value);

            return ResponseEntity.ok(responseData);
        } catch (Exception ex) {
            responseData.setResult(false);
            responseData.getMessage().add(ex.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
    }//mengambil seluruh data

    @GetMapping("/{id}")
    //public Student fetchProgramsById(@PathVariable("id") int id){
    public ResponseEntity<ResponseData<Student>> fetchStudentById(@PathVariable("id") int id) {
        ResponseData<Student> responseData = new ResponseData<>();
        try {
            responseData.setResult(true);
            List<Student> value = new ArrayList<>();
            value.add(studentServices.findOne(id));
            responseData.setData(value);

            return ResponseEntity.ok(responseData);
        } catch (Exception ex) {
            responseData.setResult(false);
            responseData.getMessage().add(ex.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
    }//mengambil data by id

    @PutMapping
    public ResponseEntity<ResponseData<Student>> updateStudent(@Valid @RequestBody Student students, Errors errors) {

        ResponseData<Student> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setResult(false);
            responseData.setData(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        try {
            responseData.setResult(true);
            List<Student> value = new ArrayList<>();
            value.add(studentServices.update(students));
            responseData.setData(value);

            return ResponseEntity.ok(responseData);
        } catch (Exception ex) {
            responseData.getMessage().add("ID is Required");
            responseData.setResult(false);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
    }//update programs


    @DeleteMapping("/{id}")
    //public void deleteProgramsById(@PathVariable("id") int id){
    public ResponseEntity<ResponseData<Void>> deleteStudentById(@PathVariable("id") int id) {
        ResponseData<Void> responseData = new ResponseData<>();
        try {
            studentServices.removeOne(id);
            responseData.setResult(true);
            responseData.getMessage().add("Successfully Remove");

            return ResponseEntity.ok(responseData);
        } catch (Exception ex) {
            responseData.setResult(false);
            responseData.getMessage().add(ex.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
    }
}
