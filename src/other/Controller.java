package other;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Controller {
    private Gui gui;
    private App_core core;

    public Controller(Gui gui, App_core core) {
        this.gui = gui;
        this.core = core;
    }

    public void setOutput(String number, String text){
        gui.setLblNumRowns(number);
        gui.getDisplay().setText(text);
    }

    public void initController() {
        gui.getBtnAddHospi().addActionListener(e -> {
            setOutput("",core.addHospitalization(gui.getTfHospiFrom().getText(), gui.getTfHospiTo().getText(), gui.getTfDiagnosis().getText(), gui.getTfPacientId().getText(), gui.getTfHospitalName().getText())) ;
        });
        gui.getBtnEndHospi().addActionListener(e -> {
            setOutput("",core.endHospitalization(gui.getTfHospiTo().getText(), gui.getTfPacientId().getText(), gui.getTfHospitalName().getText())) ;
        });
        gui.getBtnAddHospital().addActionListener(e -> {
            setOutput("",core.addHospital(gui.getTfHospitalNameM().getText()));
        });
        gui.getBtnAddPatient().addActionListener(e -> {
            setOutput("",core.addPatient(gui.getTfPatientName().getText(), gui.getTfPatientSurname().getText(), gui.getTfPatientIc().getText(), gui.getTfBirthDate().getText(), gui.getTfIc().getText())) ;
        });
        gui.getBtnFillDB().addActionListener(e -> {
            setOutput("", core.generateData(gui.getTfQuantityPatients().getText(), gui.getTfQuantityHospitals().getText(), gui.getTfQuantityHospitalizations().getText(), gui.getTfQuantityIcs().getText()));
        });
        gui.getBtnGetCurrentPatientsInHospital().addActionListener(e -> {
            String[] data = core.getCurrentPatients(gui.getTfHospitalNameM().getText());
            setOutput(data[0], data[1]);
        });
        gui.getBtnGetPatientsNameSurname().addActionListener(e -> {
            String[] data = core.getPatientsNameSurname(gui.getTfHospitalNameM().getText(),gui.getTfPatientName().getText(), gui.getTfPatientSurname().getText());
            setOutput(data[0], data[1]);
        });
        gui.getBtnGetCurrentPatientsInHospitalIc().addActionListener(e -> {
            String[] data = core.getCurrentPatientsIc(gui.getTfHospitalNameM().getText(), gui.getTfIc().getText());
            setOutput(data[0], data[1]);
        });
        gui.getBtnGetHospi().addActionListener(e -> {
            String[] data = core.getHospi(gui.getTfPacientId().getText(), gui.getTfHospitalName().getText());
            setOutput(data[0], data[1]);
        });
        gui.getBtnGetHospitals().addActionListener(e -> {
            String[] data = core.getHospitals();
            setOutput(data[0], data[1]);
        });
        gui.getBtnBalanceTrees().addActionListener(e -> {
            setOutput("",core.balanceTrees());
        });
        gui.getBtnGetPatientsFromTo().addActionListener(e -> {
            String[] data = core.getPatientsFromTo(gui.getTfHospitalNameM().getText(),gui.getTfHospiFrom().getText(), gui.getTfHospiTo().getText());
            setOutput(data[0], data[1]);
        });
        gui.getBtnDeleteHospital().addActionListener(e -> {
            setOutput("",core.removeHospital(gui.getTfHospitalNameM().getText(),gui.getTfHospitalNewNameM().getText()));
        });
        gui.getBtnCreateData().addActionListener(e -> {
            String[] data = core.createData(gui.getTfHospitalNameM().getText(), gui.getTfHospitalYearMonth().getText());
            setOutput(data[0], data[1]);
        });
        gui.getBtnSaveData().addActionListener(e -> {
            try {
                setOutput("",core.saveData());
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                unsupportedEncodingException.printStackTrace();
            }
        });
        gui.getBtnLoadData().addActionListener(e -> {
            try {
                setOutput("",core.loadData());
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
    }
}
