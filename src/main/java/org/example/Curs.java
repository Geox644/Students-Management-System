package org.example;

import java.util.ArrayList;
import java.util.List;

public class Curs<T extends Student> {
    String nume;
    int locuriDisponibile;
    List<T> studentiInrolati;

    public Curs(String nume, int locuriDisponibile) {
        this.nume = nume;
        this.locuriDisponibile = locuriDisponibile;
        this.studentiInrolati = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public int getLocuriDisponibile() {
        return locuriDisponibile;
    }

    public boolean inrolareStudent(Student student, String programStudent) {
        if ((student instanceof Licenta && programStudent.equals("licenta")) ||
                (student instanceof Master && programStudent.equals("master"))) {
            if (studentiInrolati.size() < locuriDisponibile || ultimaMedie(student)) {
                studentiInrolati.add((T) student);
                return true;
            }
        }
        return false;
    }

    private boolean ultimaMedie(Student student) {
        if (!studentiInrolati.isEmpty()) {
            double ultimaMedie = studentiInrolati.get(studentiInrolati.size() - 1).getMedie();
            return Double.compare(ultimaMedie, student.getMedie()) == 0;
        }
        return false;
    }

    public List<T> getStudentiInrolati() {
        return studentiInrolati;
    }
}

