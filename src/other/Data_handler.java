package other;

import data.Hospital;
import data.Hospitalization;
import data.Patient;
import structure.BST;
import structure.BST_Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Data_handler {

    public void savePatientsToCSV(BST<String> patients) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("patients.csv", "UTF-8");
        ArrayList<BST_Data> patientsList = patients.inOrderData(patients.getRoot());
        for (int i = 0; i < patientsList.size(); i++) {
            writer.print(((Patient) patientsList.get(i)).printCSV());
        }
        writer.close();
    }

    public void saveHospitalsToCSV(BST<String> hospitals) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("hospitals.csv", "UTF-8");
        ArrayList<BST_Data> hospitalsList = hospitals.inOrderData(hospitals.getRoot());
        for (int i = 0; i < hospitalsList.size(); i++) {
            writer.print(((Hospital) hospitalsList.get(i)).printCSV());
        }
        writer.close();
    }

    public void saveHospitalizationsToCSV(BST<Integer> hospitalizations) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("hospitalizations.csv", "UTF-8");
        ArrayList<BST_Data> hospitalizationsList = hospitalizations.inOrderData(hospitalizations.getRoot());
        for (int i = 0; i < hospitalizationsList.size(); i++) {
            writer.print(((Hospitalization) hospitalizationsList.get(i)).printCSV());
        }
        writer.close();
    }

    public ArrayList<BST_Data> loadHospitalizaitonsFromCSV(BST patients, BST hospitals) {
        File f = new File("hospitalizations.csv");
        ArrayList<BST_Data> hospitalizationsList = new ArrayList<>();
        try {
            Scanner sc = new Scanner(f);

            while(sc.hasNext()){
                String readLine = sc.nextLine();
                Scanner rowSc = new Scanner(readLine);
                rowSc.useDelimiter(";");
                ArrayList<String> dataList = new ArrayList<>();
                while (rowSc.hasNext()) {
                    dataList.add(rowSc.next());
                }
                Patient patient =  (Patient) patients.findData(dataList.get(4));
                Hospital hospital = (Hospital) hospitals.findData(dataList.get(5));
                Hospitalization hospi;
                if (!dataList.get(2).equals("null")) {
                    hospi = new Hospitalization(Integer.parseInt(dataList.get(0)), LocalDate.parse(dataList.get(1)), LocalDate.parse(dataList.get(2)), dataList.get(3), patient, hospital);
                    hospitalizationsList.add(hospi);
                    patient.addHospitalization(hospi);
                    hospital.addHospitalization(hospi, false);
                } else {
                    hospi = new Hospitalization(Integer.parseInt(dataList.get(0)), LocalDate.parse(dataList.get(1)), dataList.get(3), patient, hospital);
                    hospitalizationsList.add(hospi);
                    patient.addHospitalization(hospi);
                    hospital.addHospitalization(hospi, true);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return hospitalizationsList;
    }

    public ArrayList<BST_Data> loadHospitalsFromCSV() {
        File f = new File("hospitals.csv");
        ArrayList<BST_Data> hospitalsList = new ArrayList<>();
        try {
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                String readLine = sc.nextLine();
                hospitalsList.add(new Hospital(readLine));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return hospitalsList;
    }

    public ArrayList<BST_Data> loadPatientsFromCSV() {
        File f = new File("patients.csv");
        ArrayList<BST_Data> patientsList = new ArrayList<>();
        try {
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                String readLine = sc.nextLine();
                Scanner rowSc = new Scanner(readLine);
                rowSc.useDelimiter(";");
                ArrayList<String> dataList = new ArrayList<>();
                while (rowSc.hasNext()) {
                    dataList.add(rowSc.next());
                }
                patientsList.add(new Patient(dataList.get(2), dataList.get(0), dataList.get(1), dataList.get(2), LocalDate.parse(dataList.get(3)), Integer.parseInt(dataList.get(4))));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return patientsList;
    }

    public int getNumberOfIcs() throws FileNotFoundException {
        int numOfIcs = 0;
        File f = new File("ics.csv");
        Scanner sc = new Scanner(f);
        while(sc.hasNext()){
            String readLine = sc.nextLine();
            numOfIcs = Integer.parseInt(readLine);
        }
        return numOfIcs;
    }

    public void saveIcsToCSV(int numOfIcs) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("ics.csv", "UTF-8");
        writer.println(numOfIcs);
        writer.close();
    }
}
