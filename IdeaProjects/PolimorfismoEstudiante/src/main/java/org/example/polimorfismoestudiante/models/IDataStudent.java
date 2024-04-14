package org.example.polimorfismoestudiante.models;

import java.util.ArrayList;

public interface IDataStudent {
     void saveStudent(Student student);
     void updateStudent(Student student);
     ArrayList<Student> getStudents();
}
