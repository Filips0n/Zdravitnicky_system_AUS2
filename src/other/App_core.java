package other;

import data.Hospital;
import data.Hospitalization;
import data.Patient;
import data.hospitalization.*;
import structure.BST;
import structure.BST_Data;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class App_core {
    private int numOfIcs;

    Generator<Integer> gen = new Generator<>();
    Data_handler dataHandler = new Data_handler();

    BST<String> patients = new BST<String>();
    BST<String> hospitals = new BST<String>();
    BST<Integer> hospitalizations = new BST<Integer>();
    BST<Hospitalization_HospitalNamePatientIdEndOfHospitalization> hospiHospNamePacientIdEndOfHospi = new BST<>();

    public App_core() { }

    private void createTrees() {
        ArrayList<BST_Data> listHospi = hospitalizations.inOrderData(hospitalizations.getRoot());
        for (int i = 0; i < hospitalizations.getSize(); i++) {
            hospiHospNamePacientIdEndOfHospi.insertDataComposite(new Hospitalization_HospitalNamePatientIdEndOfHospitalization((Hospitalization) listHospi.get(i)));
        }
    }

    public String addHospitalization(String dateFrom, String dateTo, String diagnosis, String pacientIc, String hospitalName){
        if (dateFrom.equals("") || diagnosis.equals("") || pacientIc.equals("") || hospitalName.equals("")) {return "Chybajuci udaj";}

        BST_Data hospital =  hospitals.findData(hospitalName);
        BST_Data patient = patients.findData(pacientIc);
        if (hospital == null) {return "Zadana nemocnica neexistuje";}
        if (patient == null) {return "Zadany pacient neexistuje";}
        Hospitalization hospi;

        if (dateTo.equals("")) {
            hospi = new Hospitalization(hospitalizations.getSize()+1, LocalDate.parse(dateFrom), diagnosis,(Patient) patient,(Hospital) hospital);
        } else {
            if (LocalDate.parse(dateFrom).isAfter(LocalDate.parse(dateTo))){
                return "Datum konca hospitalizacie je skor ako datum zaciatku hospitalizacie\nHospitalizacia neulozena";
            };
            hospi = new Hospitalization(hospitalizations.getSize()+1, LocalDate.parse(dateFrom), LocalDate.parse(dateTo), diagnosis,(Patient) patient,(Hospital) hospital);
        }

        if (!((Patient) patient).addHospitalization(hospi)) {
            return "Pacient uz je hospitalizovany";
        };
        boolean success = hospitalizations.insertData(hospi);
        if (success) {
            //((Patient) patient).addHospitalization(hospi);
            if (dateTo.equals("")) {
                ((Hospital)hospital).addHospitalization(hospi, true);
            } else {
                ((Hospital)hospital).addHospitalization(hospi, false);
            }
            hospiHospNamePacientIdEndOfHospi.insertData(new Hospitalization_HospitalNamePatientIdEndOfHospitalization(hospi));
        }
        return success ? "Hospitalizacia uspesne pridana" : "Hospitalizacia nepridana";
    }

    public String endHospitalization(String dateTo, String patientId, String hospitalName) {
        if (dateTo.equals("") || patientId.equals("") || hospitalName.equals("")) {return "Chybajuci udaj";}
        Hospitalization_HospitalNamePatientIdEndOfHospitalization hospitalization =
            (Hospitalization_HospitalNamePatientIdEndOfHospitalization)
            hospiHospNamePacientIdEndOfHospi.findDataComposite(
            new Hospitalization_HospitalNamePatientIdEndOfHospitalization(
                new Hospitalization(Integer.MAX_VALUE, LocalDate.parse("9999-01-01"), null,"xxx",
                new Patient(patientId, "xxx", "xxx", patientId, LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                new Hospital(hospitalName))
        ), false);
        if (hospitalization == null) {return "Zadana hospitalizacia neexistuje";}
        if (hospitalization.getHospitalizationData().getStartOfHospitalization().isAfter(LocalDate.parse(dateTo))){
            return "Datum konca hospitalizacie je skor ako datum zaciatku hospitalizacie\nHospitalizacia neukoncena";
        };
//        Patient pat = (Patient) patients.findNode(patientId).getData();
//        Hospital hosp = (Hospital) hospitals.findNode(hospitalName).getData();
        Patient pat = hospitalization.getHospitalizationData().getPatient();
        Hospital hosp = hospitalization.getHospitalizationData().getHospital();
        hosp.getHospiDateToPatientId().removeNodeComposite(
            hosp.getHospiDateToPatientId().findNodeComposite(
                    new Hospitalization_EndOfHospitalizationPatientId(
                            new Hospitalization(Integer.MAX_VALUE, hospitalization.getHospitalizationData().getStartOfHospitalization(), null,"xxx",
                                    new Patient(patientId, "xxx", "xxx", patientId, LocalDate.parse("9999-01-01"), pat.getIc_code()),
                                    new Hospital(hospitalName))
                    )
            , false)
        );

        hospitalization.getHospitalizationData().setEndOfHospitalization(LocalDate.parse(dateTo));
        return "Hospitalizacia uspesne zmenena";
    }

    public String addPatient(String name, String surname, String id, String birthDate, String ic_code) {
        if (name.equals("") || surname.equals("") || id.equals("") || birthDate.equals("") || ic_code.equals("")) {return "Chybajuci udaj";}
        boolean success = patients.insertData(new Patient(id, name, surname, id, LocalDate.parse(birthDate), Integer.parseInt(ic_code)));
        return success ? "Pacient uspesne pridany" : "Pacient nepridany";
    }

    public String addHospital(String hospitalName) {
        if (hospitalName.equals("")) {return "Chybajuci udaj";}
        boolean success = hospitals.insertData(new Hospital(hospitalName));
        return success ? "Nemocica uspesne pridana" : "Nemocnica nepridana";
    }

    public String[] getCurrentPatients(String hospitalName) {
        if (hospitalName.equals("")) {return new String[]{"", "Chybajuci udaj"};}
        Hospital hospital = (Hospital) hospitals.findData(hospitalName);
        if (hospital == null){return new String[]{"", "Zadana nemocnica neexistuje"};}

        BST<Hospitalization_EndOfHospitalizationPatientId> treeDateToPatientId = hospital.getHospiDateToPatientId();

        ArrayList<BST_Data> listOfPatients = treeDateToPatientId.inOrderData(treeDateToPatientId.getRoot());
        if (listOfPatients == null) {return new String[]{"0", "Ziadne evidovane hospitalizacie"};}
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < listOfPatients.size(); i++) {
            output.append(((Hospitalization_EndOfHospitalizationPatientId)
                    listOfPatients.get(i)).getHospitalizationData().getPatient().print());
            output.append("\n");
        }
        return new String[]{ "" + listOfPatients.size(), output.toString() };
    }

    public String generateData(String numOfPatients, String numOfHospitals, String numOfHospitalizations, String numOfIcs) {
        if (numOfPatients.equals("") || numOfHospitals.equals("") || numOfIcs.equals("") || numOfHospitalizations.equals("")) {return "Chybajuci udaj";}
        this.numOfIcs = Integer.parseInt(numOfIcs);
        gen.insertDataIntoTree(patients, gen.generatePatients(Integer.parseInt(numOfPatients),this.numOfIcs));
        gen.insertDataIntoTree(hospitals, gen.generateHospitals(Integer.parseInt(numOfHospitals)));
        gen.insertDataIntoTree(hospitalizations, gen.generateHospitalizations(Integer.parseInt(numOfHospitalizations), patients, hospitals));
        createTrees();
        return "Databaza naplnena";
    }

    public String[] getHospi(String patientId, String hospitalName) {
        if (patientId.equals("") || hospitalName.equals("")) {return new String[]{"", "Chybajuci udaj"};}
        Patient patient = (Patient) patients.findData(patientId);
        if (patient == null){return new String[]{"", "Zadany pacient neexistuje"};}

        //System.out.println(patient.printHospitalizations()); ;

        ArrayList<BST_Data> listOfHospi = patient.getHospiHospitalNameHospiId().intervalFind(
                                new Hospitalization_HospitalNameHospiId(
                new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("9999-01-01"), LocalDate.parse("9999-01-01"),"xxx",
                        new Patient("000000/0000", "xxx", "xxx", "000000/0000", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                        new Hospital(hospitalName))
        ),
                new Hospitalization_HospitalNameHospiId(
                        new Hospitalization(Integer.MAX_VALUE, LocalDate.parse("9999-01-01"), LocalDate.parse("9999-01-01"),"xxx",
                                new Patient("999999/9999", "xxx", "xxx", "999999/9999", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
        ));
        if (listOfHospi == null) {return new String[]{"0", "Ziadne evidovane hospitalizacie"};}
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < listOfHospi.size(); i++) {
            output.append(((Hospitalization_HospitalNameHospiId)
                    listOfHospi.get(i)).getHospitalizationData().print());
            output.append("\n");
        }
        return new String[]{""+ listOfHospi.size(), output.toString()};
    }

    public String[] getHospitals() {
        ArrayList<BST_Data> listOfHospitals = hospitals.inOrderData(hospitals.getRoot());
        if (listOfHospitals == null){return new String[]{"", "Neexistuje ziadna nemocnica"};}
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < listOfHospitals.size(); i++) {
            output.append(((Hospital)
                    listOfHospitals.get(i)).getName());
            output.append("\n");
        }
        return new String[]{""+ listOfHospitals.size(), output.toString()};
    }

    public String balanceTrees() {
        patients.balance();
        ArrayList<BST_Data> listOfPatients = patients.inOrderData(patients.getRoot());
        if (listOfPatients != null) {
            for (int i = 0; i < listOfPatients.size(); i++) {
                Patient patient = (Patient) listOfPatients.get(i);
                patient.getHospiHospitalNameHospiId().balance();
                patient.getHospitalizations().balance();
            }
        }

        hospitals.balance();
        ArrayList<BST_Data> listOfHospitals = hospitals.inOrderData(hospitals.getRoot());
        if (listOfHospitals != null) {
            for (int i = 0; i < listOfHospitals.size(); i++) {
                Hospital hospital = (Hospital) listOfHospitals.get(i);
                hospital.getHospiDateToPatientId().balance();
                hospital.getAllHospitalizations().balance();
                hospital.getHospiDateEndPatientId().balance();
                hospital.getHospiPacientNameSurnameIdHospiId().balance();
            }
        }
        hospitalizations.balance();
        hospiHospNamePacientIdEndOfHospi.balance();

        return "Optimalizacia databazy dokoncena";
    }

    public String[] getCurrentPatientsIc(String hospitalName, String icName) {
        if (hospitalName.equals("") || icName.equals("")) {return new String[]{"", "Chybajuci udaj"};}
        Hospital hospital = (Hospital) hospitals.findData(hospitalName);
        if (hospital == null){return new String[]{"", "Zadana nemocnica neexistuje"};}

        ArrayList<BST_Data> listOfPatients = hospital.getHospiDateToPatientId().intervalFind(
                new Hospitalization_EndOfHospitalizationPatientId(
                new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("9999-01-01"), null,"xxx",
                        new Patient("000000/0000", "xxx", "xxx", "000000/0000", LocalDate.parse("9999-01-01"), Integer.parseInt(icName)),
                        new Hospital(hospitalName))
        ),
                new Hospitalization_EndOfHospitalizationPatientId(
                        new Hospitalization(Integer.MAX_VALUE, LocalDate.parse("9999-01-01"), null,"xxx",
                                new Patient("999999/9999", "xxx", "xxx", "999999/9999", LocalDate.parse("9999-01-01"), Integer.parseInt(icName)),
                                new Hospital(hospitalName))
                ));
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < listOfPatients.size(); i++) {
            output.append(((Hospitalization_EndOfHospitalizationPatientId)
                    listOfPatients.get(i)).getHospitalizationData().getPatient().print());
            output.append("\n");
        }
        return new String[]{""+ listOfPatients.size(), output.toString()};
    }

    public String[] getPatientsNameSurname(String hospitalName, String name, String surname) {
        if (hospitalName.equals("") || name.equals("") || surname.equals("")) {return new String[]{"", "Chybajuci udaj"};}
        Hospital hospital = (Hospital) hospitals.findData(hospitalName);
        if (hospital == null){return new String[]{"", "Zadana nemocnica neexistuje"};}

        ArrayList<BST_Data> listOfHospitalizations = hospital.getHospiPacientNameSurnameIdHospiId().intervalFind(
                new Hospitalization_PacientNameSurnameIdHospiId(
                        new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("9999-01-01"), null,"xxx",
                                new Patient("000000/0000", name, surname, "000000/0000", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ),
                new Hospitalization_PacientNameSurnameIdHospiId(
                        new Hospitalization(Integer.MAX_VALUE, LocalDate.parse("9999-01-01"), LocalDate.parse("9999-01-01"),"xxx",
                                new Patient("999999/9999", name, surname, "999999/9999", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ));
        if (listOfHospitalizations == null) {return new String[]{"0", "Ziadne evidovane hospitalizacie"};}
        StringBuilder output = new StringBuilder();

        String patientId = ((Hospitalization_PacientNameSurnameIdHospiId)listOfHospitalizations.get(0)).getHospitalizationData().getPatient().getId();
        output.append("----------"+((Hospitalization_PacientNameSurnameIdHospiId)listOfHospitalizations.get(0)).getHospitalizationData().getPatient().print()+"----------\n");
        for (int i = 0; i < listOfHospitalizations.size(); i++) {

            if (!patientId.equals(
                    ((Hospitalization_PacientNameSurnameIdHospiId)listOfHospitalizations.get(i)).getHospitalizationData().getPatient().getId())){
                output.append("----------"+((Hospitalization_PacientNameSurnameIdHospiId)listOfHospitalizations.get(i)).getHospitalizationData().getPatient().print()+"----------\n");
                patientId = ((Hospitalization_PacientNameSurnameIdHospiId)listOfHospitalizations.get(i)).getHospitalizationData().getPatient().getId();
            }

            output.append(((Hospitalization_PacientNameSurnameIdHospiId)
                    listOfHospitalizations.get(i)).getHospitalizationData().print());
            output.append("\n");
        }
        return new String[]{""+ listOfHospitalizations.size(), output.toString()};
    }

    public String[] getPatientsFromTo(String hospitalName, String dateFrom, String dateTo) {
        if (hospitalName.equals("") || dateFrom.equals("") || dateTo.equals("")) {return new String[]{"", "Chybajuci udaj"};}
        Hospital hospital = (Hospital) hospitals.findData(hospitalName);
        //AL
        ArrayList<BST_Data> listOfHospi = hospital.getHospiDateEndPatientId().intervalFind(
                new Hospitalization_EndHospiPatientId(
                        new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("1111-01-01"), LocalDate.parse(dateFrom),"xxx",
                                new Patient("000000/0000", "xxx", "xxx", "000000/0000", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ),
                new Hospitalization_EndHospiPatientId(
                        new Hospitalization(Integer.MAX_VALUE, LocalDate.parse("9999-01-01"), null,"xxx",
                                new Patient("999999/9999", "xxx", "xxx", "999999/9999", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ));
        //
        if (listOfHospi == null) {return new String[]{"0", "Ziadne evidovane hospitalizacie"};}
        BST<Hospitalization_StartHospiPatientId> hospiDateFromPatientId = new BST<>();

        //gen.insertDataIntoTree(hospiDateFromPatientId, listOfHospi);
        ArrayList<Hospitalization> changedDateTo = new ArrayList<>();
        for (int i = 0; i < listOfHospi.size(); i++){
            Hospitalization hospiEnd = ((Hospitalization_EndHospiPatientId)listOfHospi.get(i)).getHospitalizationData();
            if (hospiEnd.getEndOfHospitalization() == null) {
                hospiEnd.setEndOfHospitalization(LocalDate.now());
                changedDateTo.add(hospiEnd);
            }
            hospiDateFromPatientId.insertData(
                    new Hospitalization_StartHospiPatientId(hospiEnd)
            );
        }
        //AL Final
        ArrayList<BST_Data> listOfHospiFinal = hospiDateFromPatientId.intervalFind(
                new Hospitalization_StartHospiPatientId(
                        new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("1111-01-01"), LocalDate.parse("1111-01-01"),"xxx",
                                new Patient("000000/0000", "xxx", "xxx", "000000/0000", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ),
                new Hospitalization_StartHospiPatientId(
                        new Hospitalization(Integer.MAX_VALUE, LocalDate.parse(dateTo), LocalDate.parse("9999-01-01"),"xxx",
                                new Patient("999999/9999", "xxx", "xxx", "999999/9999", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ));
        //
        if (listOfHospiFinal == null) {return new String[]{"0", "Ziadne evidovane hospitalizacie"};}

        for (int i = 0; i < changedDateTo.size(); i++) {
            changedDateTo.get(i).setEndOfHospitalization(null);
        }

        BST<String> selectedPatients = new BST<String>();
        for (int i = 0; i < listOfHospiFinal.size(); i++) {
            selectedPatients.insertData(((Hospitalization_StartHospiPatientId)listOfHospiFinal.get(i)).getHospitalizationData().getPatient());
        }

        ArrayList<BST_Data> finalList = selectedPatients.inOrderData(selectedPatients.getRoot());
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < finalList.size(); i++) {
            output.append(((Patient)
                    finalList.get(i)).print());
            output.append("\n");
        }

        return new String[]{""+ finalList.size(), output.toString()};
    }

    public String removeHospital(String hospitalName, String newHospitalName) {
        if (newHospitalName.equals("") || hospitalName.equals("")) {return "Chybajuci udaj";}
        if (newHospitalName.equals(hospitalName)) {return "Zhodne nazvy";}
        Hospital hospital = (Hospital) hospitals.findData(hospitalName);
        Hospital newHospital = (Hospital) hospitals.findData(newHospitalName);
        if (hospital == null || newHospital == null) {return "Neexistujuca nemocnica";}
        ///////////////---------------------------
        ArrayList<BST_Data> allHospiOldHospital = hospital.getAllHospitalizations().inOrderData(hospital.getAllHospitalizations().getRoot());
        for (int i = 0; i < hospital.getAllHospitalizations().getSize(); i++) {
            newHospital.addHospitalization(
                    (Hospitalization) allHospiOldHospital.get(i), ((Hospitalization) allHospiOldHospital.get(i)).getEndOfHospitalization() == null ? true : false
            );
        }

        //////////////////---------------------------
        ArrayList<BST_Data> listOfHospi = hospiHospNamePacientIdEndOfHospi.intervalFind(
                new Hospitalization_HospitalNamePatientIdEndOfHospitalization(
                        new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("9999-01-01"), LocalDate.parse("9999-01-01"),"xxx",
                                new Patient("000000/0000", "xxx", "xxx", "000000/0000", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ),
                new Hospitalization_HospitalNamePatientIdEndOfHospitalization(
                        new Hospitalization(Integer.MAX_VALUE, LocalDate.parse("9999-01-01"), LocalDate.parse("9999-01-01"),"xxx",
                                new Patient("999999/9999", "xxx", "xxx", "999999/9999", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ));
        if (listOfHospi == null) {return "Zadana nemocnica neexistuje";}

        for (int i = 0; i < listOfHospi.size(); i++) {
            ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) listOfHospi.get(i)).getHospitalizationData().setHospital(newHospital);
        }
        listOfHospi = hospiHospNamePacientIdEndOfHospi.inOrderData(hospiHospNamePacientIdEndOfHospi.getRoot());
        sortNameEndHospi(listOfHospi);
        BST<Hospitalization_HospitalNamePatientIdEndOfHospitalization> tempTree = new BST<Hospitalization_HospitalNamePatientIdEndOfHospitalization>();
        gen.insertDataIntoTree(tempTree, listOfHospi);
        hospiHospNamePacientIdEndOfHospi = tempTree;
        /////////////////---------------------------
        //Patient class
        ArrayList<BST_Data> lisfOPatients = patients.inOrderData(patients.getRoot());
        for (int i = 0; i < lisfOPatients.size(); i++) {
            Patient patient = ((Patient)lisfOPatients.get(i));
            ArrayList<BST_Data> allHospiPatient = patient.getHospiHospitalNameHospiId().inOrderData(patient.getHospiHospitalNameHospiId().getRoot());

            sortHospNameHospiId(allHospiPatient);
            BST<Hospitalization_HospitalNameHospiId> tempTreeP = new BST<>();
            gen.insertDataIntoTree(tempTreeP, allHospiPatient);
            patient.setHospiHospitalNameHospiId(tempTreeP);
        }
        /////////////////---------------------------

        hospitals.removeNodeByKey(hospitalName);
        return "Nemocnica zrusena";
    }

    private void sortHospNameHospiId(ArrayList<BST_Data> allHospiPatient) {
        Collections.sort(allHospiPatient, (Comparator) (hospi1, hospi2) -> {
            return ((Hospitalization_HospitalNameHospiId) hospi1).compareTo((Hospitalization_HospitalNameHospiId) hospi2);
        });
    }

    private void sortNameEndHospi(ArrayList<BST_Data> listOfHospi) {
        Collections.sort(listOfHospi, (Comparator) (hospi1, hospi2) -> {
            return ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) hospi1).compareTo((Hospitalization_HospitalNamePatientIdEndOfHospitalization) hospi2);
        });
    }

    public String[] createData(String hospitalName, String yearMonth) {
        if (hospitalName.equals("") || yearMonth.equals("")) {return new String[]{"", "Chybajuci udaj"};}
        LocalDate firstDayOfMonth = LocalDate.parse(yearMonth+"-01");
        LocalDate lastDayOfMonth = YearMonth.from(LocalDate.parse(yearMonth+"-01")).atEndOfMonth();

        Hospital hospital = (Hospital) hospitals.findData(hospitalName);
        if (hospital == null) {return new String[]{"", "Neexistujuca nemocnica"};}
        ArrayList<BST_Data> listOfHospi = hospital.getHospiDateEndPatientId().intervalFind(
                new Hospitalization_EndHospiPatientId(
                        new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("1111-01-01"), firstDayOfMonth,"xxx",
                                new Patient("000000/0000", "xxx", "xxx", "000000/0000", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ),
                new Hospitalization_EndHospiPatientId(
                        new Hospitalization(Integer.MAX_VALUE, LocalDate.parse("9999-01-01"), null,"xxx",
                                new Patient("999999/9999", "xxx", "xxx", "999999/9999", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ));
        if (listOfHospi == null) {return new String[]{"0", "Ziadne evidovane hospitalizacie"};}
        BST<Hospitalization_StartHospiPatientId> hospiDateFromPatientId = new BST<>();

        //gen.insertDataIntoTree(hospiDateFromPatientId, listOfHospi);
        ArrayList<Hospitalization> changedDateTo = new ArrayList<>();
        for (int i = 0; i < listOfHospi.size(); i++){
            Hospitalization hospiEnd = ((Hospitalization_EndHospiPatientId)listOfHospi.get(i)).getHospitalizationData();
            if (hospiEnd.getEndOfHospitalization() == null) {
                hospiEnd.setEndOfHospitalization(LocalDate.now());
                changedDateTo.add(hospiEnd);
            }
            hospiDateFromPatientId.insertData(
                    new Hospitalization_StartHospiPatientId(hospiEnd)
            );
        }
        //AL Final
        ArrayList<BST_Data> listOfHospiFinal = hospiDateFromPatientId.intervalFind(
                new Hospitalization_StartHospiPatientId(
                        new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("1111-01-01"), LocalDate.parse("1111-01-01"),"xxx",
                                new Patient("000000/0000", "xxx", "xxx", "000000/0000", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ),
                new Hospitalization_StartHospiPatientId(
                        new Hospitalization(Integer.MAX_VALUE, lastDayOfMonth, LocalDate.parse("9999-01-01"),"xxx",
                                new Patient("999999/9999", "xxx", "xxx", "999999/9999", LocalDate.parse("9999-01-01"), Integer.MAX_VALUE),
                                new Hospital(hospitalName))
                ));
        //
        if (listOfHospiFinal == null) {return new String[]{"0", "Ziadne evidovane hospitalizacie"};}

        ArrayList<Integer> daysByIc = new ArrayList<>(numOfIcs);
        ArrayList<ArrayList<ArrayList<Hospitalization>>> listOfDayHospi = new ArrayList<>(numOfIcs);
        for (int i = 0; i < numOfIcs; i++) {
            daysByIc.add(0);
            listOfDayHospi.add(new ArrayList<ArrayList<Hospitalization>>(lastDayOfMonth.getDayOfMonth()));
            for (int j = 0; j < lastDayOfMonth.getDayOfMonth(); j++) {
                listOfDayHospi.get(i).add(new ArrayList<Hospitalization>());
            }
        }

        calculateDays(firstDayOfMonth, lastDayOfMonth, listOfHospiFinal, daysByIc, listOfDayHospi);

        for (int i = 0; i < changedDateTo.size(); i++) {
            changedDateTo.get(i).setEndOfHospitalization(null);
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < daysByIc.size(); i++) {
            int code = i+1;
            output.append("Kod poistovne: " + code + "      Celkovy pocet dni: " + daysByIc.get(i));
            output.append("\n");
            for (int j = 0; j < lastDayOfMonth.getDayOfMonth(); j++) {
                int day = j+1;
                output.append(day);
                output.append(". den        Pocet pacientov: " + listOfDayHospi.get(i).get(j).size() + "\n");
                    for (int k = 0; k < listOfDayHospi.get(i).get(j).size(); k++) {
                        output.append(listOfDayHospi.get(i).get(j).get(k).getPatient().print());
                        output.append("    Diagnoza: " + listOfDayHospi.get(i).get(j).get(k).getDiagnosis());
                        output.append("\n");
                    }
            }
        }
//        StringBuilder output = new StringBuilder();
//        for (int i = 0; i < listOfHospiFinal.size(); i++) {
//            output.append(((Hospitalization_StartHospiPatientId)
//                    listOfHospiFinal.get(i)).getHospitalizationData().print());
//            output.append("\n");
//        }
//---------------------------VYPISE VSETKYCH PACIENTOV-----------------------
//        BST<String> selectedPatients = new BST<String>();
//        for (int i = 0; i < listOfHospiFinal.size(); i++) {
//            selectedPatients.insertData(((Hospitalization_StartHospiPatientId)listOfHospiFinal.get(i)).getHospitalizationData().getPatient());
//        }
//
//        ArrayList<BST_Data> finalList = selectedPatients.inOrderData(selectedPatients.getRoot());
//        StringBuilder output = new StringBuilder();
//        for (int i = 0; i < finalList.size(); i++) {
//            output.append(((Patient)
//                    finalList.get(i)).print());
//            output.append("\n");
//        }
//---------------------------------------------------------------------------
        return new String[]{""+ listOfHospiFinal.size(), output.toString()};
    }

    private void calculateDays(LocalDate firstDayOfNonth, LocalDate lastDayOfMonth, ArrayList<BST_Data> listOfHospiFinal, ArrayList<Integer> daysByIc, ArrayList<ArrayList<ArrayList<Hospitalization>>> listOfDayHospi) {
        for (int i = 0; i < listOfHospiFinal.size(); i++) {
            Hospitalization hospi = ((Hospitalization_StartHospiPatientId)listOfHospiFinal.get(i)).getHospitalizationData();
            for (int j = 1; j < numOfIcs+1; j++) {
                if (j == hospi.getPatient().getIc_code()) {
                    int days = 0;
                    int minDay = 0;
                    if ((hospi.getStartOfHospitalization().isBefore(firstDayOfNonth)
                            && hospi.getEndOfHospitalization().isAfter(lastDayOfMonth)) ||
                            (hospi.getStartOfHospitalization().isEqual(firstDayOfNonth)
                            && hospi.getEndOfHospitalization().isEqual(lastDayOfMonth))){

                        days = lastDayOfMonth.getDayOfMonth();
                        minDay = 1;

                    } else if (hospi.getStartOfHospitalization().isAfter(firstDayOfNonth)
                    && hospi.getEndOfHospitalization().isBefore(lastDayOfMonth)){
                        minDay = hospi.getStartOfHospitalization().getDayOfMonth();
                        days = hospi.getEndOfHospitalization().getDayOfMonth() - hospi.getStartOfHospitalization().getDayOfMonth();

                    } else if ((hospi.getStartOfHospitalization().isBefore(firstDayOfNonth) || (hospi.getStartOfHospitalization().isEqual(firstDayOfNonth))) &&
                                hospi.getEndOfHospitalization().isAfter(firstDayOfNonth) &&
                                hospi.getEndOfHospitalization().isBefore(lastDayOfMonth)) {
                        minDay = 1;
                        days = hospi.getEndOfHospitalization().getDayOfMonth() - firstDayOfNonth.getDayOfMonth();

                    } else if ((hospi.getEndOfHospitalization().isAfter(lastDayOfMonth) || (hospi.getEndOfHospitalization().isEqual(lastDayOfMonth))) &&
                            hospi.getStartOfHospitalization().isAfter(firstDayOfNonth) &&
                            hospi.getStartOfHospitalization().isBefore(lastDayOfMonth)) {
                        minDay = hospi.getStartOfHospitalization().getDayOfMonth();
                        days = lastDayOfMonth.getDayOfMonth() - hospi.getStartOfHospitalization().getDayOfMonth();

                    }
                    daysByIc.set(j-1, daysByIc.get(j-1) + days);
                    for (int k = minDay; k < minDay+days+1; k++) {
                        listOfDayHospi.get(j-1).get(k-1).add(hospi);
                    }
                }
            }
        }
    }

    public String saveData() throws FileNotFoundException, UnsupportedEncodingException {
        dataHandler.savePatientsToCSV(patients);
        dataHandler.saveHospitalsToCSV(hospitals);
        dataHandler.saveHospitalizationsToCSV(hospitalizations);
        dataHandler.saveIcsToCSV(this.numOfIcs);
        return "Ulozenie databazy dokoncene";
    }

    public String loadData() throws FileNotFoundException {
        gen.insertDataIntoTree(patients, dataHandler.loadPatientsFromCSV());
        gen.insertDataIntoTree(hospitals, dataHandler.loadHospitalsFromCSV());
        gen.insertDataIntoTree(hospitalizations, dataHandler.loadHospitalizaitonsFromCSV(patients, hospitals));
        createTrees();
        this.numOfIcs = dataHandler.getNumberOfIcs();
        return "Nacitavanie databazy dokoncene";
    }
}
