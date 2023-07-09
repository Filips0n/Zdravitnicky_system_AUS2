package data.hospitalization;

import data.Hospitalization;
import structure.BST_Data;

public class Hospitalization_HospitalNamePatientIdEndOfHospitalization extends BST_Data {

    private Hospitalization hospitalizationData;

    public Hospitalization getHospitalizationData() {
        return hospitalizationData;
    }

    public Hospitalization_HospitalNamePatientIdEndOfHospitalization(Hospitalization hospitalization) {
        this.hospitalizationData = hospitalization;
    }

    @Override
    public int compareTo(BST_Data nodeData) {
        if(hospitalizationData.getHospital().getName().compareToIgnoreCase(
                ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getHospital().getName()) < 0)
            return 1;
        else if(hospitalizationData.getHospital().getName().compareToIgnoreCase(
                ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getHospital().getName()) > 0)
            return -1;
        else {
            if(hospitalizationData.getPatient().getId().compareToIgnoreCase(
                    ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getPatient().getId()) < 0)
                return 1;
            else if(hospitalizationData.getPatient().getId().compareToIgnoreCase(
                    ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getPatient().getId()) > 0)
                return -1;
            else {
                if (hospitalizationData.getEndOfHospitalization() == null && (
                        ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getEndOfHospitalization()) == null) {return 0;}
                if (hospitalizationData.getEndOfHospitalization() != null && (
                        ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getEndOfHospitalization()) == null) {return 1;}
                if (hospitalizationData.getEndOfHospitalization() == null && (
                        ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getEndOfHospitalization()) != null) {return -1;}

                if(hospitalizationData.getEndOfHospitalization().isBefore(
                        ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getEndOfHospitalization()))
                    return 1;
                else if(hospitalizationData.getEndOfHospitalization().isAfter(
                        ((Hospitalization_HospitalNamePatientIdEndOfHospitalization) nodeData).hospitalizationData.getEndOfHospitalization()))
                    return -1;
                else
                    return 0;
            }
        }
    }

    @Override
    public int compareToKey(Object key) {
        return 0;
    }
}
