package data.hospitalization;

import data.Hospitalization;
import structure.BST_Data;

public class Hospitalization_PacientNameSurnameIdHospiId extends BST_Data {
    private Hospitalization hospitalizationData;

    public Hospitalization getHospitalizationData() {
        return hospitalizationData;
    }

    public Hospitalization_PacientNameSurnameIdHospiId(Hospitalization hospitalization) {
        this.hospitalizationData = hospitalization;
    }

    @Override
    public int compareTo(BST_Data nodeData) {
        if(hospitalizationData.getPatient().getName().compareToIgnoreCase(
                ((Hospitalization_PacientNameSurnameIdHospiId) nodeData).hospitalizationData.getPatient().getName()) < 0) {
            return 1;
        }
        else if(hospitalizationData.getPatient().getName().compareToIgnoreCase(
                ((Hospitalization_PacientNameSurnameIdHospiId) nodeData).hospitalizationData.getPatient().getName()) > 0) {
            return -1;
        } else {
            if(hospitalizationData.getPatient().getSurname().compareToIgnoreCase(
                    ((Hospitalization_PacientNameSurnameIdHospiId) nodeData).hospitalizationData.getPatient().getSurname()) < 0) {
                return 1;
            }
            else if(hospitalizationData.getPatient().getSurname().compareToIgnoreCase(
                    ((Hospitalization_PacientNameSurnameIdHospiId) nodeData).hospitalizationData.getPatient().getSurname()) > 0) {
                return -1;
            } else {
                if(hospitalizationData.getPatient().getId().compareToIgnoreCase(
                        ((Hospitalization_PacientNameSurnameIdHospiId) nodeData).hospitalizationData.getPatient().getId()) < 0)
                    return 1;
                else if(hospitalizationData.getPatient().getId().compareToIgnoreCase(
                        ((Hospitalization_PacientNameSurnameIdHospiId) nodeData).hospitalizationData.getPatient().getId()) > 0)
                    return -1;
                else {
                    if (hospitalizationData.getKey() < ((Hospitalization_PacientNameSurnameIdHospiId)nodeData).hospitalizationData.getKey()){
                        return 1;
                    } else if(hospitalizationData.getKey() > ((Hospitalization_PacientNameSurnameIdHospiId)nodeData).hospitalizationData.getKey()){
                        return -1;
                    } else return 0;
                }
            }
        }
    }

    @Override
    public int compareToKey(Object key) {
        return 0;
    }
}
