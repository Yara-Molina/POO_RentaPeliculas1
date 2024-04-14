package org.example.polimorfismoestudiante.models;

import java.util.ArrayList;

public class ServiciosEscolares {
    private ArrayList<IDataStudent> databases = new ArrayList<>();
    private Database01 database01;
    private Database02 database02;
    private Database03 database03;

    public ServiciosEscolares() {
        database01 = new Database01();
        database02 = new Database02();
        database03 = new Database03();

        databases.add(database01);
        databases.add(database02);
        databases.add(database03);
    }

    public void saveToDatabases(Student student) {
        for (IDataStudent database : databases) {
            database.saveStudent(student);
        }
    }

    public void updateInDatabases(Student modifiedStudent, Student originalStudent) {
        for (IDataStudent database : databases) {
            database.updateStudent(modifiedStudent);
        }
    }

    public void printStudents() {
        System.out.println("Estudiantes en la Escuela:");

        for (IDataStudent database : databases) {
            System.out.println("Base de Datos: " + database.getClass().getSimpleName());
            for (Student student : database.getStudents()) {
                System.out.println(student);
            }
            System.out.println();
        }
    }
}
