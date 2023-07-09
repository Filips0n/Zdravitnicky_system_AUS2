package data;

import data.hospitalization.Hospitalization_EndHospiPatientId;
import data.hospitalization.Hospitalization_HospitalNameHospiId;
import structure.BST;
import structure.BST_Data;
import structure.BST_Node;

import java.time.LocalDate;
import java.util.ArrayList;

public class Patient extends BST_Data<String> {
    private String name;
    private String surname;
    private String id;
    private LocalDate birthDate;
    private int ic_code;
    private BST<Integer> hospitalizations;
    private BST<Hospitalization_HospitalNameHospiId> hospiHospitalNameHospiId;

    public Patient(String key, String name, String surname, String id, LocalDate birthDate, int ic_code) {
        super(key);
        hospitalizations = new BST<>();
        hospiHospitalNameHospiId = new BST<>();
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.birthDate = birthDate;
        this.ic_code = ic_code;
    }

    public boolean addHospitalization(Hospitalization hospitalization) {
        if (checkEndHospi(hospitalization)) {return false;};
        this.hospitalizations.insertData(hospitalization);
        this.hospiHospitalNameHospiId.insertData(new Hospitalization_HospitalNameHospiId(hospitalization));
        return true;
    }

    //Zisti ci uz je nejaka hospitalizacia pacienta v nemocnici NULL
    private boolean checkEndHospi(Hospitalization hospitalization) {
        return hospitalization.getHospital().getHospiDateEndPatientId().findNodeComposite(
                new Hospitalization_EndHospiPatientId(
                        new Hospitalization(Integer.MIN_VALUE, LocalDate.parse("9999-01-01"), null,"xxx",
                                this,
                                hospitalization.getHospital())
                )
        , false) != null;
    }

    public BST<Hospitalization_HospitalNameHospiId> getHospiHospitalNameHospiId() {
        return hospiHospitalNameHospiId;
    }

    public void setHospiHospitalNameHospiId(BST<Hospitalization_HospitalNameHospiId> hospiHospitalNameHospiId) {
        this.hospiHospitalNameHospiId = hospiHospitalNameHospiId;
    }

    public BST<Integer> getHospitalizations() {
        return hospitalizations;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getIc_code() {
        return ic_code;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String print(){
        return String.format("%-30s", name +" "+ surname)  +" "+ id +" "+ birthDate +" "+ String.format("%1$5s", ic_code);
    }

    public String printCSV(){
        return name +";"+ surname  +";"+ id +";"+ birthDate +";"+ ic_code +"\n";
    }

    public String printHospitalizations(){
        StringBuilder output = new StringBuilder();
        ArrayList<BST_Node<Integer>> list = hospitalizations.inOrder(hospitalizations.getRoot());
        for (int i = 0; i < list.size(); i++) {
            output.append(((Hospitalization) list.get(i).getData()).print()).append("\n");
        }
        return output.toString();
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
