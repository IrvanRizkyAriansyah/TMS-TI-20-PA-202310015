package com.ibik.academic.academicservices.student;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentServices {
    
    @Autowired
    private StudentRepo studentRepo;

    public Student save(Student student){
        return studentRepo.save(student);
    }

    public Student update(Student student) {
        Student result = findOne(student.getId());

        result.setFirstname(student.getFirstname());
        result.setMiddlename(student.getMiddlename());
        result.setLastname(student.getLastname());
        result.setNpm(student.getNpm());
        result.setPrograms(student.getPrograms());
        result.setProgramStudy(student.getProgramStudy());
        result.setCourses(student.getCourses());

        return result;
    }

    public Student findOne(int id){
        return studentRepo.findById(id).get();
    }    

    public Iterable<Student> findAll(){
        return studentRepo.findAll();
    }

    public void removeOne(int id){
        studentRepo.deleteById(id);
    }
}
