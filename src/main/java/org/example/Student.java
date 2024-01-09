package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student {
    String nume;
    double medie;

    private List<String> preferinte;

    public Student(String nume) {
        this.nume = nume;
        this.medie = 0.0;
        this.preferinte = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public List<String> getPreferinte() {
        return preferinte;
    }

    public void adaugaPreferinta(String... numeCursuriPreferate) {
        preferinte.addAll(Arrays.asList(numeCursuriPreferate));
    }
}

