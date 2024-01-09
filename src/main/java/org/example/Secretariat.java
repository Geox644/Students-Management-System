package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Secretariat {
    private Map<String, Student> studenti;
    private List<Curs<?>> cursuri;

    public Secretariat() {
        this.studenti = new HashMap<>();
        this.cursuri = new ArrayList<>();
    }

    public void adaugaStudent(String programStudii, String numeStudent, String outputFilePath) throws StudentDuplicatException {
        // verific dupa cheie (nume) daca nu cumva exista deja studentul
        if (studenti.containsKey(numeStudent)) {
            throw new StudentDuplicatException("***\nStudent duplicat: " + numeStudent, outputFilePath);
        }

        // adaug studentul in functie de programul de studiu
        Student student;
        if ("licenta".equalsIgnoreCase(programStudii)) {
            student = new Licenta(numeStudent);
        } else if ("master".equalsIgnoreCase(programStudii)) {
            student = new Master(numeStudent);
        } else {
            throw new IllegalArgumentException("Programul " + programStudii + " nu exista.");
        }

        // adaug studentul in HashMap
        studenti.put(numeStudent, student);
    }

    public void citesteMediile(String folderPath) {
        // citesc din fiser mediile si le asigneaza studentilor
        for (int i = 1; ; i++) {
            String filePath = folderPath + "/note_" + i + ".txt";
            if (!Files.exists(Paths.get(filePath))) {
                break;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(" - ");
                    String numeStudent = tokens[0].trim();
                    double medie = Double.parseDouble(tokens[1].trim());

                    Student student = studenti.get(numeStudent);
                    if (student != null) {
                        student.setMedie(medie);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void posteazaMediile(String outputFilePath) {
        List<Student> studentiSortati = new ArrayList<>(studenti.values());
        studentiSortati.sort(Comparator.comparing(Student::getMedie).reversed().thenComparing(Student::getNume));

        try (FileWriter writer = new FileWriter(outputFilePath, true)) {
            writer.write("***\n");
            for (Student student : studentiSortati) {
                writer.write(student.getNume() + " - " + student.getMedie() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contestatie(String numeStudent, double nouaMedie) throws StudentNotFoundException {
        // verific daca studentul exista
        if (!studenti.containsKey(numeStudent)) {
            throw new StudentNotFoundException("Studentul nu exista: " + numeStudent);
        }
        // actualizez media studentului
        Student student = studenti.get(numeStudent);
        student.setMedie(nouaMedie);

    }

    public void adaugaCurs(String programStudii, String numeCurs, int capacitateMaxima) {
        // verific daca cursul exista deja in lista
        for (Curs<?> cursExist : cursuri) {
            if (cursExist.getNume().equalsIgnoreCase(numeCurs)) {
                return; // ies daca cursul exista
            }
        }

        // adaug cursrul in functie de programul de studiu
        Curs<?> curs;
        if ("licenta".equalsIgnoreCase(programStudii)) {
            curs = new Curs<Licenta>(numeCurs, capacitateMaxima);
        } else if ("master".equalsIgnoreCase(programStudii)) {
            curs = new Curs<Master>(numeCurs, capacitateMaxima);
        } else {
            throw new IllegalArgumentException("Programul " + programStudii + " nu exista.");
        }

        // adaug cursul in lista
        cursuri.add(curs);
    }

    public void adaugaPreferinte(String numeStudent, String... cursuriPreferate) {
        Student student = studenti.get(numeStudent);
        if (student != null) {
            student.adaugaPreferinta(cursuriPreferate);
        }
    }

    private Curs<?> getCurs(String numeCurs) {
        for (Curs<?> curs : cursuri) {
            if (curs.getNume().equalsIgnoreCase(numeCurs)) {
                return curs;
            }
        }
        return null;
    }


    public void repartizeaza() {
        // sortare
        List<Student> studentiSortati = new ArrayList<>(studenti.values());
        Collections.sort(studentiSortati, Comparator.comparing(Student::getMedie).reversed());

        // parcurg lista de studenti sortata si ii repartizez in cursuri
        for (Student student : studentiSortati) {
            List<String> preferinte = student.getPreferinte();

            for (String numeCurs : preferinte) {
                // iau fiecare nume de curs din lista de preferinte
                String[] numeCursuri = numeCurs.replaceAll("\\[|\\]", "").trim().split(",");

                // parcurg lista fiecarui curs
                for (String numeCursIndividual : numeCursuri) {
                    Curs<?> cursOptional = getCurs(numeCursIndividual.trim());
                    if (cursOptional != null && cursOptional.inrolareStudent(student, getProgramStudent(student))) {
                        break;
                    }
                }
            }
        }
    }

    private String getProgramStudent(Student student) {
        if (student instanceof Licenta) {
            return "licenta";
        } else if (student instanceof Master) {
            return "master";
        }
        return "";
    }

    public void posteazaCurs(String outputFilePath, String numeCurs) {
        Curs<?> curs = getCurs(numeCurs);

        try (FileWriter writer = new FileWriter(outputFilePath, true)) {
            writer.write("***\n");
            if (curs != null) {
                writer.write(curs.getNume() + " (" + curs.getLocuriDisponibile() + ")\n");
                List<Student> studentiSortati = (List<Student>) curs.getStudentiInrolati();
                Collections.sort(studentiSortati, Comparator.comparing(Student::getNume));
                for (Student student : curs.getStudentiInrolati()) {
                    writer.write(student.getNume() + " - " + student.getMedie() + "\n");
                }
            } else {
                writer.write("Cursul " + numeCurs + " nu exista.");
            }
        } catch (IOException e) {
            System.out.println("Eroare scriere in fisier: " + e.getMessage());
        }
    }

    private Student getStudentOptional(String numeStudent) {
        return studenti.get(numeStudent);
    }

    public void posteazaStudent(String outputFilePath, String numeStudent) {
        Student student = getStudentOptional(numeStudent);

        try (FileWriter writer = new FileWriter(outputFilePath, true)) {
            writer.write("***\n");
            if (student != null) {
                writer.write("Student " + getProgramStudent1(student) + ": ");
                writer.write(student.getNume() + " - " + student.getMedie() + " - ");

                Curs<?> cursAsignat = getCursAsignat(student);
                if (cursAsignat != null) {
                    writer.write(cursAsignat.getNume() + "\n");
                } else {
                    writer.write("Niciun curs asignat.\n");
                }
            } else {
                writer.write("Studentul " + numeStudent + " nu exista.");
            }
        } catch (IOException e) {
            System.out.println("Eroare scriere in fisier: " + e.getMessage());
        }
    }

    private Curs<?> getCursAsignat(Student student) {
        // obtin cursul asignat studentului (daca exista)
        for (Curs<?> curs : cursuri) {
            if (curs.getStudentiInrolati().contains(student)) {
                return curs;
            }
        }
        return null;
    }

    private String getProgramStudent1(Student student) {
        if (student instanceof Licenta) {
            return "Licenta";
        } else if (student instanceof Master) {
            return "Master";
        }
        return "";
    }

}

