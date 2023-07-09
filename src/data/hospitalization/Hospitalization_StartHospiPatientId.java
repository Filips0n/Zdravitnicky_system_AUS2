package data.hospitalization;

import data.Hospitalization;
import structure.BST_Data;

public class Hospitalization_StartHospiPatientId extends BST_Data {
    private Hospitalization hospitalizationData;

    public Hospitalization getHospitalizationData() {
        return hospitalizationData;
    }

    public Hospitalization_StartHospiPatientId(Hospitalization hospitalization) {
        this.hospitalizationData = hospitalization;
    }

    @Override
    public int compareTo(BST_Data nodeData) {
        if(hospitalizationData.getStartOfHospitalization().isBefore(
                ((Hospitalization_StartHospiPatientId) nodeData).hospitalizationData.getStartOfHospitalization()))
            return 1;
        else if(hospitalizationData.getStartOfHospitalization().isAfter(
                ((Hospitalization_StartHospiPatientId) nodeData).hospitalizationData.getStartOfHospitalization()))
            return -1;
        else {
                return comparePatientIds((Hospitalization_StartHospiPatientId) nodeData);
        }
    }

    private int comparePatientIds(Hospitalization_StartHospiPatientId nodeData) {
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
