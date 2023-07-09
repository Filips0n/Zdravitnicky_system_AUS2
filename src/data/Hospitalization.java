package data;

import structure.BST_Data;

import java.time.LocalDate;

public class Hospitalization extends BST_Data<Integer> {
    private LocalDate startOfHospitalization;
    private LocalDate endOfHospitalization;
    private String diagnosis;
    private Patient patient;
    private Hospital hospital;

    public Hospitalization(int hospitalizationId, LocalDate startOfHospitalization, LocalDate endOfHospitalization, String diagnosis, Patient patient, Hospital hospital) {
        super(hospitalizationId);
        this.startOfHospitalization = startOfHospitalization;
        this.endOfHospitalization = endOfHospitalization;
        this.diagnosis = diagnosis;
        this.patient = patient;
        this.hospital = hospital;
    }

    public Hospitalization(int hospitalizationId, LocalDate startOfHospitalization, String diagnosis, Patient patient, Hospital hospital) {
        super(hospitalizationId);
        this.startOfHospitalization = startOfHospitalization;
        this.diagnosis = diagnosis;
        this.patient = patient;
        this.hospital = hospital;
    }

    public LocalDate getStartOfHospitalization() {
        return startOfHospitalization;
    }
    public LocalDate getEndOfHospitalization() {
        return endOfHospitalization;
    }
    public String getDiagnosis() {
        return diagnosis;
    }
    public Patient getPatient() {
        return patient;
    }
    public Hospital getHospital() {
        return hospital;
    }

    public void setEndOfHospitalization(LocalDate endOfHospitalization) {
        this.endOfHospitalization = endOfHospitalization;
    }
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    @Override
    public int compareTo(BST_Data<Integer> nodeData) {
        if(startOfHospitalization.isBefore(((Hospitalization) nodeData).getStartOfHospitalization()))
            return 1;
        else if(startOfHospitalization.isAfter(((Hospitalization) nodeData).getStartOfHospitalization()))
            return -1;
        else {
            if (endOfHospitalization == null && (
                    ((Hospitalization) nodeData).getEndOfHospitalization()) == null) {
                if(patient.getKey().compareToIgnoreCase(((Hospitalization) nodeData).getPatient().getKey()) < 0)
                    return 1;
                else if(patient.getKey().compareToIgnoreCase(((Hospitalization) nodeData).getPatient().getKey()) > 0)
                    return -1;
                else {
                    if (hospital.getKey().compareToIgnoreCase(((Hospitalization) nodeData).getHospital().getKey()) < 0) {
                        return 1;
                    } else if (hospital.getKey().compareToIgnoreCase(((Hospitalization) nodeData).getHospital().getKey()) > 0) {
                        return -1;
                    } else
                        return 0;
                }
            }
            if (endOfHospitalization != null && (
                    ((Hospitalization) nodeData).getEndOfHospitalization()) == null) {return 1;}
            if (endOfHospitalization == null && (
                    ((Hospitalization) nodeData).getEndOfHospitalization()) != null) {return -1;}

            if(endOfHospitalization.isBefore(((Hospitalization) nodeData).getEndOfHospitalization()))
                return 1;
            else if(endOfHospitalization.isBefore(((Hospitalization) nodeData).getEndOfHospitalization()))
                return -1;
            else {
                if(patient.getKey().compareToIgnoreCase(((Hospitalization) nodeData).getPatient().getKey()) < 0)
                    return 1;
                else if(patient.getKey().compareToIgnoreCase(((Hospitalization) nodeData).getPatient().getKey()) > 0)
                    return -1;
                else {
                    if (hospital.getKey().compareToIgnoreCase(((Hospitalization) nodeData).getHospital().getKey()) < 0) {
                        return 1;
                    } else if (hospital.getKey().compareToIgnoreCase(((Hospitalization) nodeData).getHospital().getKey()) > 0) {
                        return -1;
                    } else
                        return 0;
                }
           }
        }

//        if(getKey() < nodeData.getKey())
//            return 1;
//        else if(getKey() > nodeData.getKey())
//            return -1;
//        else
//            return 0;
    }

    @Override
    public int compareToKey(Integer key) {
        if(getKey() < key)
            return 1;
        else if(getKey() > key)
            return -1;
        else
            return 0;
    }

    public String print(){
        return startOfHospitalization +" "+ endOfHospitalization +" "+ diagnosis +" "+ patient.getId() +" "+ hospital.getKey();
    }

    public String printCSV() {
        return getKey()+";"+startOfHospitalization +";"+ endOfHospitalization +";"+ diagnosis +";"+ patient.getId() +";"+ hospital.getKey()+ "\n";
    }
}
