package com.product.service;


import com.product.modal.Address;
import com.product.modal.Student;
import com.product.repository.AddressRepository;
import com.product.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    public Student saveStudent(Student student)
    {
        return studentRepository.save(student);
    }

    public Student getStudent(Address address)
    {
        return addressRepository.findById(address.getStudent().getSid()).get().getStudent();
    }


}
