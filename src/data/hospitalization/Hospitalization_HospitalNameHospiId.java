package data.hospitalization;

import data.Hospitalization;
import structure.BST_Data;

public class Hospitalization_HospitalNameHospiId extends BST_Data {
    private Hospitalization hospitalizationData;

    public Hospitalization getHospitalizationData() {
        return hospitalizationData;
    }

    public Hospitalization_HospitalNameHospiId(Hospitalization hospitalization) {
        this.hospitalizationData = hospitalization;
    }

    @Override
    public int compareTo(BST_Data nodeData) {
        if(hospitalizationData.getHospital().getName().compareToIgnoreCase(
                ((Hospitalization_HospitalNameHospiId) nodeData).hospitalizationData.getHospital().getName()) < 0)
            return 1;
        else if(hospitalizationData.getHospital().getName().compareToIgnoreCase(
                ((Hospitalization_HospitalNameHospiId) nodeData).hospitalizationData.getHospital().getName()) > 0)
            return -1;
        else {
            if (hospitalizationData.getKey() < ((Hospitalization_HospitalNameHospiId)nodeData).hospitalizationData.getKey()){
                return 1;
            } else if(hospitalizationData.getKey() > ((Hospitalization_HospitalNameHospiId)nodeData).hospitalizationData.getKey()){
                return -1;
            } else return 0;
        }
    }

    @Override
    public int compareToKey(Object key) {
        return 0;
    }
}
