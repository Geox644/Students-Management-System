package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <test_folder>");
            return;
        }

        String testFolder = args[0];
        String inputFilePath = "src/main/resources/" + testFolder + "/" + testFolder + ".in";
        String outputFilePath = "src/main/resources/" + testFolder + "/" + testFolder + ".out";

        Secretariat secretariat = new Secretariat();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             FileWriter writer = new FileWriter(outputFilePath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                String command = tokens[0];

                switch (command) {
                    case "adauga_student":
                        try {
                            String programStudii = tokens[2];
                            String numeStudent = tokens[4];
                            secretariat.adaugaStudent(programStudii, numeStudent, outputFilePath);
                        } catch (StudentDuplicatException e) {
                            writer.write(e.getMessage() + "\n");
                        }
                        break;
                    case "citeste_mediile":
                        try {
                            secretariat.citesteMediile("src/main/resources/" + testFolder);
                        } catch (Exception e) {
                            writer.write("Eroare: " + line + "\n");
                        }
                        break;
                    case "posteaza_mediile":
                        try {
                            secretariat.posteazaMediile("src/main/resources/" + testFolder + "/" + testFolder + ".out");
                        } catch (Exception e) {
                            writer.write("Eroare: " + line + "\n");
                        }
                        break;
                    case "contestatie":
                        try {
                            String numeStudentContestatie = tokens[2];
                            double nouaMedie = Double.parseDouble(tokens[4]);
                            secretariat.contestatie(numeStudentContestatie, nouaMedie);
                        } catch (StudentNotFoundException e) {
                            writer.write(e.getMessage() + "\n");
                        } catch (Exception e) {
                            writer.write("Eroare: " + line + "\n");
                        }
                        break;
                    case "adauga_curs":
                        try {
                            String programStudiiCurs = tokens[2];
                            String numeCurs = tokens[4];
                            int capacitateMaxima = Integer.parseInt(tokens[6]);
                            secretariat.adaugaCurs(programStudiiCurs, numeCurs, capacitateMaxima);
                        } catch (Exception e) {
                            writer.write("Eroare: " + line + "\n");
                        }
                        break;
                    case "adauga_preferinte":
                        try {
                            String numeStudent = tokens[2];
                            List<String> numeCursuriPreferate = new ArrayList<>();

                            for (int i = 4; i < tokens.length; i += 2) {
                                numeCursuriPreferate.add(tokens[i]);
                            }
                            secretariat.adaugaPreferinte(numeStudent, String.valueOf(numeCursuriPreferate));
                        } catch (Exception e) {
                            writer.write("Eroare: " + line + "\n");
                        }
                        break;
                    case "repartizeaza":
                        try {
                            secretariat.repartizeaza();
                        } catch (Exception e) {
                            writer.write("Eroare: " + line + "\n");
                        }
                        break;
                    case "posteaza_curs":
                        try {
                            String numeCurs = tokens[2];
                            secretariat.posteazaCurs("src/main/resources/" + testFolder + "/" + testFolder + ".out", numeCurs);
                        } catch (Exception e) {
                            writer.write("Eroare: " + line + "\n");
                        }
                        break;
                    case "posteaza_student":
                        try {
                            String numeStudent = tokens[2];
                            secretariat.posteazaStudent("src/main/resources/" + testFolder + "/" + testFolder + ".out", numeStudent);
                        } catch (Exception e) {
                            writer.write("Eroare: " + line + "\n");
                        }
                        break;

                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
