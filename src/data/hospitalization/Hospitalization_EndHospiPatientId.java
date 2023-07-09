package data.hospitalization;

import data.Hospitalization;
import structure.BST_Data;

public class Hospitalization_EndHospiPatientId extends BST_Data {
    private Hospitalization hospitalizationData;

    public Hospitalization getHospitalizationData() {
        return hospitalizationData;
    }

    public Hospitalization_EndHospiPatientId(Hospitalization hospitalization) {
        this.hospitalizationData = hospitalization;
    }

    @Override
    public int compareTo(BST_Data nodeData) {

        if (hospitalizationData.getEndOfHospitalization() == null && (
                ((Hospitalization_EndHospiPatientId) nodeData).hospitalizationData.getEndOfHospitalization()) == null) {
            return comparePatientIds((Hospitalization_EndHospiPatientId) nodeData);
        }
        if (hospitalizationData.getEndOfHospitalization() != null && (
                ((Hospitalization_EndHospiPatientId) nodeData).hospitalizationData.getEndOfHospitalization()) == null) {
            return 1;}//musi takto
        if (hospitalizationData.getEndOfHospitalization() == null && (
                ((Hospitalization_EndHospiPatientId) nodeData).hospitalizationData.getEndOfHospitalization()) != null) {
            return -1;}//musi takto

        if(hospitalizationData.getEndOfHospitalization().isBefore(
                ((Hospitalization_EndHospiPatientId) nodeData).hospitalizationData.getEndOfHospitalization()))
            return 1;//musi takto
        else if(hospitalizationData.getEndOfHospitalization().isAfter(
                ((Hospitalization_EndHospiPatientId) nodeData).hospitalizationData.getEndOfHospitalization()))
            return -1;//musi takto
        else {
                return comparePatientIds((Hospitalization_EndHospiPatientId) nodeData);
        }
    }

    private int comparePatientIds(Hospitalization_EndHospiPatientId nodeData) {
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
