package data.hospitalization;

import data.Hospitalization;
import structure.BST_Data;

public class Hospitalization_EndOfHospitalizationPatientId extends BST_Data {
    private Hospitalization hospitalizationData;

    public Hospitalization getHospitalizationData() {
        return hospitalizationData;
    }

    public Hospitalization_EndOfHospitalizationPatientId(Hospitalization hospitalization) {
        this.hospitalizationData = hospitalization;
    }

    @Override
    public int compareTo(BST_Data nodeData) {
        if(hospitalizationData.getPatient().getIc_code() <
                ((Hospitalization_EndOfHospitalizationPatientId)nodeData).getHospitalizationData().getPatient().getIc_code()) {
            return 1;
        } else if(hospitalizationData.getPatient().getIc_code() >
                ((Hospitalization_EndOfHospitalizationPatientId)nodeData).getHospitalizationData().getPatient().getIc_code()) {
            return -1;
        } else { //same insurance company code
            if (hospitalizationData.getEndOfHospitalization() == null && (
                    ((Hospitalization_EndOfHospitalizationPatientId) nodeData).hospitalizationData.getEndOfHospitalization()) == null) {
                return comparePatientIds((Hospitalization_EndOfHospitalizationPatientId) nodeData);
            }
            if (hospitalizationData.getEndOfHospitalization() != null && (
                    ((Hospitalization_EndOfHospitalizationPatientId) nodeData).hospitalizationData.getEndOfHospitalization()) == null) {return 1;}
            if (hospitalizationData.getEndOfHospitalization() == null && (
                    ((Hospitalization_EndOfHospitalizationPatientId) nodeData).hospitalizationData.getEndOfHospitalization()) != null) {return -1;}

            if(hospitalizationData.getEndOfHospitalization().isBefore(
                    ((Hospitalization_EndOfHospitalizationPatientId) nodeData).hospitalizationData.getEndOfHospitalization()))
                return 1;
            else if(hospitalizationData.getEndOfHospitalization().isAfter(
                    ((Hospitalization_EndOfHospitalizationPatientId) nodeData).hospitalizationData.getEndOfHospitalization()))
                return -1;
            else {
                return comparePatientIds((Hospitalization_EndOfHospitalizationPatientId) nodeData);
            }
        }


    }

    private int comparePatientIds(Hospitalization_EndOfHospitalizationPatientId nodeData) {
        if(hospitalizationData.getPatient().getId().compareToIgnoreCase(
                nodeData.hospitalizationData.getPatient().getId()) < 0) {
            return 1;
        } else if(hospitalizationData.getPatient().getId().compareToIgnoreCase(
                nodeData.hospitalizationData.getPatient().getId()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareToKey(Object key) {
        return 0;
    }

}
