package org.example.polimorfismoestudiante.models;

import java.util.ArrayList;

public class Database02 implements IDataStudent{
    private ArrayList<Student> students2 = new ArrayList<>();

    @Override
    public void saveStudent(Student student) {
        students2.add(student);
    }

    @Override
    public void updateStudent(Student student) {
        for (Student e : students2) {
            if (e.getId() == student.getId()) {
                e.setNombre(student.getNombre());
                e.setApellido(student.getApellido());

                return;
            }
        }
    }

    @Override
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students2);
    }
}
