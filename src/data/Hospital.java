package data;

import data.hospitalization.Hospitalization_EndOfHospitalizationPatientId;
import data.hospitalization.Hospitalization_EndHospiPatientId;
import data.hospitalization.Hospitalization_PacientNameSurnameIdHospiId;
import structure.BST;
import structure.BST_Data;
import structure.BST_Node;

import java.util.ArrayList;

public class Hospital extends BST_Data<String> {
    private String name;
    private BST<Integer> allHospitalizations;

    private BST<Hospitalization_EndOfHospitalizationPatientId> hospiDateToPatientId;
    private BST<Hospitalization_EndHospiPatientId> hospiDateEndPatientId;
    private BST<Hospitalization_PacientNameSurnameIdHospiId> hospiPacientNameSurnameIdHospiId;

    public Hospital(String name) {
        super(name);

        allHospitalizations = new BST<>();
        hospiDateToPatientId = new BST<Hospitalization_EndOfHospitalizationPatientId>();
        hospiPacientNameSurnameIdHospiId = new BST<Hospitalization_PacientNameSurnameIdHospiId>();
        hospiDateEndPatientId = new BST<Hospitalization_EndHospiPatientId>();
        this.name = name;
    }

    public BST<Hospitalization_EndHospiPatientId> getHospiDateEndPatientId() {
        return hospiDateEndPatientId;
    }

    public BST<Hospitalization_PacientNameSurnameIdHospiId> getHospiPacientNameSurnameIdHospiId() {
        return hospiPacientNameSurnameIdHospiId;
    }

    public void addHospitalization(Hospitalization hospitalization, boolean isDateToNull) {
        this.allHospitalizations.insertData(hospitalization);
        this.hospiPacientNameSurnameIdHospiId.insertData(new Hospitalization_PacientNameSurnameIdHospiId(hospitalization));
        this.hospiDateEndPatientId.insertData(new Hospitalization_EndHospiPatientId(hospitalization));
        if (isDateToNull) {
            this.hospiDateToPatientId.insertData(new Hospitalization_EndOfHospitalizationPatientId(hospitalization));
        }
    }

    public BST<Integer> getAllHospitalizations() {
        return allHospitalizations;
    }

    public BST getHospiDateToPatientId() {
        return hospiDateToPatientId;
    }

    public String getName() {
        return name;
    }

    public String printHospitalizations(){
        StringBuilder output = new StringBuilder();
        ArrayList<BST_Node<Integer>> list = allHospitalizations.inOrder(allHospitalizations.getRoot());
        for (int i = 0; i < list.size(); i++) {
            output.append(((Hospitalization) list.get(i).getData()).print()).append("\n");
        }
        return output.toString();
    }

    public String printCSV() {
        return name + "\n";
    }

    @Override
    public int compareTo(BST_Data<String> nodeData) {
        if(getKey().compareToIgnoreCase(nodeData.getKey()) < 0)
            return 1;
        else if(getKey().compareToIgnoreCase(nodeData.getKey()) > 0)
            return -1;
        else
            return 0;
    }

    @Override
    public int compareToKey(String key) {
        if(getKey().compareToIgnoreCase(key) < 0)
            return 1;
        else if(getKey().compareToIgnoreCase(key) > 0)
            return -1;
        else
            return 0;
    }
}
