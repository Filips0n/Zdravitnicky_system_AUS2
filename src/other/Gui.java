package other;

import javax.swing.*;

public class Gui {
    private JFrame frame;
    private JTextArea display;

    private JButton btnBalanceTrees;
    private JButton btnGetPatientsNameSurname;
    private JButton btnDeleteHospital;
    private JButton btnGetPatientsFromTo;
    private JButton btnGetCurrentPatientsInHospitalIc;
    private JButton btnAddHospital;
    private JButton btnGetCurrentPatientsInHospital;
    private JButton btnAddPatient;
    private JButton btnAddHospi;
    private JButton btnEndHospi;
    private JButton btnFillDB;
    private JButton btnGetHospi;
    private JButton btnGetHospitals;

    private JButton btnCreateData;

    private JLabel lblNumRowns;

    private JTextField tfHospitalNewNameM;

    private JTextField tfHospitalYearMonth;

    private JTextField tfQuantityHospitalizations;
    private JTextField tfDiagnosis;
    private JTextField tfHospiFrom;
    private JTextField tfHospiTo;
    private JTextField tfPacientId;
    private JTextField tfHospitalName;
    private JTextField tfIc;
    private JTextField tfBirthDate;
    private JTextField tfPatientIc;
    private JTextField tfPatientSurname;
    private JTextField tfPatientName;
    private JTextField tfHospitalNameM;
    private JTextField tfQuantityPatients;
    private JTextField tfQuantityHospitals;
    private JButton btnLoadData;
    private JButton btnSaveData;

    private JTextField tfQuantityIcs;

    public Gui(){
        frame = new JFrame();
        frame.setBounds(100, 100, 1200, 850);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        hospitalizationGui();
        patientGui();
        hospitalGui();
        databaseGui();

        outputGui();

        display = new JTextArea ( 16, 58 );
        display.setEditable ( false ); // set textArea non-editable
        display.setBounds(65,550,500,250);
        JScrollPane scroll = new JScrollPane ( display );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBounds(65,550,500,250);


        frame.add ( scroll );
        frame.setLocationRelativeTo ( null );

        frame.setVisible(true);
    }

    private void outputGui() {
        int x = 65;
        int xText = 250;
        int xBtns = 350;
        int y = 500;
        int margin = 20;

        JLabel lbldatabase = new JLabel("---Vystup---");
        lbldatabase.setBounds(x, y, 250, margin);
        frame.getContentPane().add(lbldatabase);

        //
        JLabel lblNumRownsTxt = new JLabel("Pocet zaznamov");
        lblNumRownsTxt.setBounds(x, y+margin, 250, margin);
        frame.getContentPane().add(lblNumRownsTxt);

        lblNumRowns = new JLabel();
        lblNumRowns.setBounds(xText, y+margin, 250, margin);
        frame.getContentPane().add(lblNumRowns);

    }

    private void databaseGui() {
        int x = 800;
        int xText = 915;
        int xBtns = 350;
        int y = 10;
        int margin = 20;
        int marginBtns = 40;
        JLabel lbldatabase = new JLabel("---Naplnenie databazy---");
        lbldatabase.setBounds(x, y, 250, margin);
        frame.getContentPane().add(lbldatabase);

        //
        JLabel lblPatients = new JLabel("Pocet pacientov");
        lblPatients.setBounds(x, y+margin, 250, margin);
        frame.getContentPane().add(lblPatients);

        tfQuantityPatients = new JTextField();
        tfQuantityPatients.setBounds(xText, lblPatients.getY(), 86, 20);
        frame.getContentPane().add(tfQuantityPatients);
        tfQuantityPatients.setColumns(10);
        //
        JLabel lblHospitals = new JLabel("Pocet nemocnic");
        lblHospitals.setBounds(x, lblPatients.getY()+margin, 250, margin);
        frame.getContentPane().add(lblHospitals);

        tfQuantityHospitals = new JTextField();
        tfQuantityHospitals.setBounds(xText, lblHospitals.getY(), 86, 20);
        frame.getContentPane().add(tfQuantityHospitals);
        tfQuantityHospitals.setColumns(10);
        //
        JLabel lblHospitalizations = new JLabel("Pocet hospitalizacii");
        lblHospitalizations.setBounds(x, tfQuantityHospitals.getY()+margin, 250, margin);
        frame.getContentPane().add(lblHospitalizations);

        tfQuantityHospitalizations = new JTextField();
        tfQuantityHospitalizations.setBounds(xText, lblHospitalizations.getY(), 86, 20);
        frame.getContentPane().add(tfQuantityHospitalizations);
        tfQuantityHospitalizations.setColumns(10);
        //
        JLabel lblIcs = new JLabel("Pocet zdrav. poist.");
        lblIcs.setBounds(x, tfQuantityHospitalizations.getY()+margin, 250, margin);
        frame.getContentPane().add(lblIcs);

        tfQuantityIcs = new JTextField();
        tfQuantityIcs.setBounds(xText, lblIcs.getY(), 86, 20);
        frame.getContentPane().add(tfQuantityIcs);
        tfQuantityIcs.setColumns(10);
        tfQuantityIcs.setText("5");

        btnFillDB = new JButton("Napln databazu");
        btnFillDB.setBounds(x, lblIcs.getY()+margin*2, 200, 30);
        frame.getContentPane().add(btnFillDB);

        btnBalanceTrees = new JButton("Optimalizuj databazu");
        btnBalanceTrees.setBounds(x, btnFillDB.getY()+margin*2, 200, 30);
        frame.getContentPane().add(btnBalanceTrees);

        btnLoadData = new JButton("Nacitaj data");
        btnLoadData.setBounds(x, btnBalanceTrees.getY()+margin*2, 200, 30);
        frame.getContentPane().add(btnLoadData);

        btnSaveData = new JButton("Uloz data");
        btnSaveData.setBounds(x, btnLoadData.getY()+margin*2, 200, 30);
        frame.getContentPane().add(btnSaveData);
    }
    private void hospitalGui() {
        int x = 65;
        int xText = 250;
        int xBtns = 350;
        int y = 150;
        int margin = 20;
        int marginBtns = 40;
        JLabel lblHospiMain = new JLabel("---Nacitavanie udajov nemocnice---");
        lblHospiMain.setBounds(x, y, 250, margin);
        frame.getContentPane().add(lblHospiMain);

        //
        JLabel lblName = new JLabel("Nazov");
        lblName.setBounds(x, lblHospiMain.getY()+margin, 200, 20);
        frame.getContentPane().add(lblName);

        tfHospitalNameM = new JTextField();
        tfHospitalNameM.setBounds(xText, lblName.getY(), 86, 20);
        frame.getContentPane().add(tfHospitalNameM);
        tfHospitalNameM.setColumns(10);
        //
        JLabel lblNewHospName = new JLabel("Nazov novej nemocnice");
        lblNewHospName.setBounds(x, lblName.getY()+margin, 200, 20);
        frame.getContentPane().add(lblNewHospName);

        tfHospitalNewNameM = new JTextField();
        tfHospitalNewNameM.setBounds(xText, lblNewHospName.getY(), 86, 20);
        frame.getContentPane().add(tfHospitalNewNameM);
        tfHospitalNewNameM.setColumns(10);
        //
        JLabel lblYearMonth = new JLabel("Rok a mesiac podkladov");
        lblYearMonth.setBounds(x, lblNewHospName.getY()+margin, 200, 20);
        frame.getContentPane().add(lblYearMonth);

        tfHospitalYearMonth = new JTextField();
        tfHospitalYearMonth.setBounds(xText, lblYearMonth.getY(), 86, 20);
        frame.getContentPane().add(tfHospitalYearMonth);
        tfHospitalYearMonth.setColumns(10);
        //

        btnAddHospital = new JButton("Pridaj nemocnicu");
        btnAddHospital.setBounds(xBtns, lblName.getY(), 200, 30);
        frame.getContentPane().add(btnAddHospital);

        btnGetHospitals = new JButton("Vypis nemocnice");
        btnGetHospitals.setBounds(btnAddHospital.getX()+220, lblName.getY(), 200, 30);
        frame.getContentPane().add(btnGetHospitals);

        btnGetCurrentPatientsInHospital = new JButton("Vypis aktualne hospitalizovanych pacientov");
        btnGetCurrentPatientsInHospital.setBounds(xBtns, btnAddHospital.getY()+marginBtns, 300, 30);
        frame.getContentPane().add(btnGetCurrentPatientsInHospital);

        btnGetCurrentPatientsInHospitalIc = new JButton("Vypis aktualne hospitalizovanych pacientov zadanej poistovne");
        btnGetCurrentPatientsInHospitalIc.setBounds(xBtns, btnGetCurrentPatientsInHospital.getY()+marginBtns, 400, 30);
        frame.getContentPane().add(btnGetCurrentPatientsInHospitalIc);

        btnGetPatientsNameSurname = new JButton("Vypis hospitalizacie pacientov podla mena a priezviska");
        btnGetPatientsNameSurname.setBounds(xBtns, btnGetCurrentPatientsInHospitalIc.getY()+marginBtns, 400, 30);
        frame.getContentPane().add(btnGetPatientsNameSurname);

        btnGetPatientsFromTo = new JButton("Vypis hospitalizovanych pacientov od - do");
        btnGetPatientsFromTo.setBounds(xBtns, btnGetPatientsNameSurname.getY()+marginBtns, 400, 30);
        frame.getContentPane().add(btnGetPatientsFromTo);

        btnCreateData = new JButton("Vytvor podklady");
        btnCreateData.setBounds(x+30, btnGetPatientsNameSurname.getY(), 200, 30);
        frame.getContentPane().add(btnCreateData);

        btnDeleteHospital = new JButton("Zrus nemocnicu");
        btnDeleteHospital.setBounds(x+30, btnCreateData.getY()+marginBtns, 200, 30);
        frame.getContentPane().add(btnDeleteHospital);
    }

    private void patientGui() {
        int x = 65;
        int xText = 250;
        int xBtns = 350;
        int y = 370;
        int margin = 20;
        JLabel lblPatientMain = new JLabel("---Nacitavanie udajov pacienta---");
        lblPatientMain.setBounds(x, y, 250, margin);
        frame.getContentPane().add(lblPatientMain);

        //
        JLabel lblName = new JLabel("Meno");
        lblName.setBounds(x, lblPatientMain.getY()+margin, 200, 20);
        frame.getContentPane().add(lblName);

        tfPatientName = new JTextField();
        tfPatientName.setBounds(xText, lblName.getY(), 86, 20);
        frame.getContentPane().add(tfPatientName);
        tfPatientName.setColumns(10);
        //
        tfPatientSurname = new JTextField();
        tfPatientSurname.setBounds(xText, lblName.getY()+margin, 86, 20);
        frame.getContentPane().add(tfPatientSurname);
        tfPatientSurname.setColumns(10);

        JLabel lblSurname = new JLabel("Priezvisko");
        lblSurname.setBounds(x, lblName.getY()+margin, 200, 20);
        frame.getContentPane().add(lblSurname);
        //
        tfPatientIc = new JTextField();
        tfPatientIc.setBounds(xText, lblSurname.getY()+margin, 86, 20);
        frame.getContentPane().add(tfPatientIc);
        tfPatientIc.setColumns(10);

        JLabel lblId = new JLabel("Rodne cislo");
        lblId.setBounds(x, lblSurname.getY()+margin, 200, 20);
        frame.getContentPane().add(lblId);
        //
        JLabel lblBirthDate = new JLabel("Datum narodenia");
        lblBirthDate.setBounds(x, lblId.getY()+margin, 200, 20);
        frame.getContentPane().add(lblBirthDate);

        tfBirthDate = new JTextField();
        tfBirthDate.setBounds(xText, lblId.getY()+margin, 86, 20);
        frame.getContentPane().add(tfBirthDate);
        tfBirthDate.setColumns(10);
        //
        JLabel lblIcCode = new JLabel("Kod zdravotnej poistovne");
        lblIcCode.setBounds(x, lblBirthDate.getY()+margin, 200, 20);
        frame.getContentPane().add(lblIcCode);

        tfIc = new JTextField();
        tfIc.setBounds(xText, lblBirthDate.getY()+margin, 86, 20);
        frame.getContentPane().add(tfIc);
        tfIc.setColumns(10);
        //

        btnAddPatient = new JButton("Pridaj pacienta");
        btnAddPatient.setBounds(xBtns, lblSurname.getY(), 200, 30);
        frame.getContentPane().add(btnAddPatient);

    }
    private void hospitalizationGui(){
        int x = 65;
        int xText = 250;
        int xBtns = 350;
        int y = 10;
        int margin = 20;
        JLabel lblHospiMain = new JLabel("---Nacitavanie udajov hospitalizacie---");
        lblHospiMain.setBounds(x, y, 250, margin);
        frame.getContentPane().add(lblHospiMain);

        //
        JLabel lblDiagnosis = new JLabel("Diagnoza");
        lblDiagnosis.setBounds(x, lblHospiMain.getY()+margin, 200, 20);
        frame.getContentPane().add(lblDiagnosis);

        tfDiagnosis = new JTextField();
        tfDiagnosis.setBounds(xText, lblDiagnosis.getY(), 86, 20);
        frame.getContentPane().add(tfDiagnosis);
        tfDiagnosis.setColumns(10);
        //
        tfHospiFrom = new JTextField();
        tfHospiFrom.setBounds(xText, lblDiagnosis.getY()+margin, 86, 20);
        frame.getContentPane().add(tfHospiFrom);
        tfHospiFrom.setColumns(10);

        JLabel lblFrom = new JLabel("Datum zaciatku hospitalizacie");
        lblFrom.setBounds(x, lblDiagnosis.getY()+margin, 200, 20);
        frame.getContentPane().add(lblFrom);
        //
        tfHospiTo = new JTextField();
        tfHospiTo.setBounds(xText, lblFrom.getY()+margin, 86, 20);
        frame.getContentPane().add(tfHospiTo);
        tfHospiTo.setColumns(10);

        JLabel lblHospiTo = new JLabel("Datum konca hospitalizacie");
        lblHospiTo.setBounds(x, lblFrom.getY()+margin, 200, 20);
        frame.getContentPane().add(lblHospiTo);
        //
        JLabel lblId = new JLabel("Rodne cislo pacienta");
        lblId.setBounds(x, lblHospiTo.getY()+margin, 200, 20);
        frame.getContentPane().add(lblId);

        tfPacientId = new JTextField();
        tfPacientId.setBounds(xText, lblHospiTo.getY()+margin, 86, 20);
        frame.getContentPane().add(tfPacientId);
        tfPacientId.setColumns(10);
        //
        JLabel lblHospi = new JLabel("Nazov nemocnice");
        lblHospi.setBounds(x, lblId.getY()+margin, 200, 20);
        frame.getContentPane().add(lblHospi);

        tfHospitalName = new JTextField();
        tfHospitalName.setBounds(xText, lblId.getY()+margin, 86, 20);
        frame.getContentPane().add(tfHospitalName);
        tfHospitalName.setColumns(10);

        //

        btnAddHospi = new JButton("Pridaj hospitalizaciu");
        btnAddHospi.setBounds(xBtns, lblDiagnosis.getY(), 200, 30);
        frame.getContentPane().add(btnAddHospi);

        btnEndHospi = new JButton("Ukonci hospitalizaciu");
        btnEndHospi.setBounds(xBtns, btnAddHospi.getY()+margin*2, 200, 30);
        frame.getContentPane().add(btnEndHospi);

        btnGetHospi = new JButton("Vypis hospitalizacii pacienta v nemocnici");
        btnGetHospi.setBounds(xBtns, btnEndHospi.getY()+margin*2, 270, 30);
        frame.getContentPane().add(btnGetHospi);
    }
    public void setLblNumRowns(String numRowns) {
        this.lblNumRowns.setText(numRowns);
    }

    public JTextField getTfDiagnosis() {
        return tfDiagnosis;
    }

    public JTextField getTfHospiFrom() {
        return tfHospiFrom;
    }

    public JTextField getTfHospiTo() {
        return tfHospiTo;
    }

    public JTextField getTfPacientId() {
        return tfPacientId;
    }

    public JTextField getTfHospitalName() {
        return tfHospitalName;
    }

    public JTextField getTfIc() {
        return tfIc;
    }

    public JTextField getTfBirthDate() {
        return tfBirthDate;
    }

    public JTextField getTfPatientIc() {
        return tfPatientIc;
    }

    public JTextField getTfPatientSurname() {
        return tfPatientSurname;
    }

    public JTextField getTfPatientName() {
        return tfPatientName;
    }

    public JTextField getTfHospitalNameM() {
        return tfHospitalNameM;
    }

    public JTextField getTfQuantityPatients() {
        return tfQuantityPatients;
    }

    public JTextField getTfQuantityHospitals() {
        return tfQuantityHospitals;
    }

    public JTextField getTfQuantityHospitalizations() {
        return tfQuantityHospitalizations;
    }

    public JButton getBtnBalanceTrees() {
        return btnBalanceTrees;
    }

    public JButton getBtnGetPatientsNameSurname() {
        return btnGetPatientsNameSurname;
    }

    public JButton getBtnGetCurrentPatientsInHospitalIc() {
        return btnGetCurrentPatientsInHospitalIc;
    }

    public JTextField getTfHospitalNewNameM() {
        return tfHospitalNewNameM;
    }

    public JTextField getTfHospitalYearMonth() {
        return tfHospitalYearMonth;
    }

    public JButton getBtnGetPatientsFromTo() {
        return btnGetPatientsFromTo;
    }

    public JButton getBtnDeleteHospital() {
        return btnDeleteHospital;
    }

    public JButton getBtnGetHospitals() {
        return btnGetHospitals;
    }

    public JButton getBtnGetHospi() {
        return btnGetHospi;
    }

    public JTextArea getDisplay() {
        return display;
    }

    public JButton getBtnAddHospital() {
        return btnAddHospital;
    }

    public JButton getBtnGetCurrentPatientsInHospital() {
        return btnGetCurrentPatientsInHospital;
    }

    public JButton getBtnAddPatient() {
        return btnAddPatient;
    }

    public JButton getBtnAddHospi() {
        return btnAddHospi;
    }

    public JButton getBtnEndHospi() {
        return btnEndHospi;
    }

    public JButton getBtnFillDB() {
        return btnFillDB;
    }

    public JButton getBtnCreateData() {
        return btnCreateData;
    }

    public JButton getBtnLoadData() {
        return btnLoadData;
    }

    public JButton getBtnSaveData() {
        return btnSaveData;
    }

    public JTextField getTfQuantityIcs() {
        return tfQuantityIcs;
    }

}
