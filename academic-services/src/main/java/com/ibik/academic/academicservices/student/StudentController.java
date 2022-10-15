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


@RestController
@RequestMapping("/api/student")
public class StudentController {
    
    @Autowired
    private StudentServices studentServices;

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
    public Iterable<Student> fetchPrograms(){
        return studentServices.findAll();
    }//mengambil seluruh data

    @GetMapping("/{id}")
    public Student fetchProgramsById(@PathVariable("id") int id){
        return studentServices.findOne(id);
    }//mengambil data by id

    @PutMapping
    public Student updatePrograms(@RequestBody Student student){
        return studentServices.save(student);
    }//update programs


    @DeleteMapping("/{id}")
    public void deleteProgramsById(@PathVariable("id") int id){
        studentServices.removoOne(id);
    }
}
