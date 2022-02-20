import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AccountFrame extends JFrame {

    JLabel accNumberLbl, ownerLbl, balanceLbl, cityLbl, genderLbl, amountLbl;
    JTextField accNumberTxt, ownerTxt, balanceTxt, amountTxt;
    JComboBox<City> citiesCB;

    JButton newBtn, saveBtn, showBtn, quitBtn, depositBtn, withdrawBtn;

    JRadioButton maleRbtn, femaleRbtn;
    ButtonGroup genderBtngrp;

    JList<Account> accountsList;
    JPanel p1, p2, p3, p4, p5;

    Set<Account> accountsSet = new TreeSet<>();
    Account acc, x;
    boolean newRecord = true;

    // ComboBox Data
    DefaultComboBoxModel<City> citiesCMBMDL;
    DefaultListModel<Account> accountsLSTMDL;

    // Table Data
    DefaultTableModel tableModel;
    ArrayList<Transaction> transList = new ArrayList<>();

    public AccountFrame() {
        super("Account Operations");
        setLayout(null);
        setSize(600, 400);

        //Adding components
        accNumberLbl = new JLabel("Account No.");
        ownerLbl = new JLabel("Owner");
        balanceLbl = new JLabel("Balance");
        cityLbl = new JLabel("City");
        genderLbl = new JLabel("Gender");
        amountLbl = new JLabel("Amount");

        accNumberTxt = new JTextField(); accNumberTxt.setEnabled(false);
        ownerTxt = new JTextField();
        balanceTxt = new JTextField(); balanceTxt.setEnabled(false);
        amountTxt = new JTextField();
        amountTxt.setPreferredSize(new Dimension(150, 25));

        citiesCMBMDL = new DefaultComboBoxModel<>();
        citiesCMBMDL.addElement(null);
        citiesCMBMDL.addElement(new City("Belgrade", "Serbia"));
        citiesCMBMDL.addElement(new City("Cacak", "Serbia"));
        citiesCMBMDL.addElement(new City("Kragujevac", "Serbia"));
        citiesCMBMDL.addElement(new City("Zagreb", "Croatia"));
        citiesCMBMDL.addElement(new City("Ljubljana", "Slovenia"));

        // Adding data to JComboBox
        citiesCB = new JComboBox<City>(citiesCMBMDL);

        maleRbtn = new JRadioButton("Male", true);
        femaleRbtn = new JRadioButton("Female");
        genderBtngrp = new ButtonGroup();
        genderBtngrp.add(maleRbtn);
        genderBtngrp.add(femaleRbtn);

        newBtn = new JButton("New");
        saveBtn = new JButton("Save");
        showBtn = new JButton("Show");
        quitBtn = new JButton("Quit");
        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");

        // Table
        accountsLSTMDL = new DefaultListModel<>();
        accountsList = new JList<>(accountsLSTMDL);

        // Panels
        p1 = new JPanel(); p1.setBounds(5, 5, 300, 150);
        p1.setLayout(new GridLayout(5, 2));

        p2 = new JPanel(); p2.setBounds(5, 155, 300, 40);
        p2.setLayout(new FlowLayout());

        p3 = new JPanel(); p3.setBounds(5, 195, 600, 40);
        p3.setLayout(new FlowLayout());

        p4 = new JPanel(); p4.setBounds(305, 5, 300, 190);
        p4.setLayout(new BorderLayout());

        p5 = new JPanel(); p5.setBounds(5, 240, 580, 120);
        p5.setLayout(new BorderLayout());

        // Adding Components to Panel
        p1.add(accNumberLbl);
        p1.add(accNumberTxt);
        p1.add(ownerLbl);
        p1.add(ownerTxt);
        p1.add(balanceLbl);
        p1.add(balanceTxt);
        p1.add(cityLbl);
        p1.add(citiesCB);
        p1.add(maleRbtn);
        p1.add(femaleRbtn);

        p2.add(newBtn);
        p2.add(saveBtn);
        p2.add(showBtn);
        p2.add(quitBtn);

        p3.add(amountLbl);
        p3.add(amountTxt);
        p3.add(depositBtn);
        p3.add(withdrawBtn);

        p4.add(accountsList);

        // Adding Panels to Frame
        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p5);

        // Table creation
        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("TrsNo.");
        tableModel.addColumn("TrsDate");
        tableModel.addColumn("TrsType");
        tableModel.addColumn("TrsAmount");

        JScrollPane scrollPane = new JScrollPane(table);
        p5.add(scrollPane);

        /** Functionality **/
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accNumberTxt.setText("");
                ownerTxt.setText("");
                citiesCB.setSelectedIndex(0);
                maleRbtn.setSelected(true);
                balanceTxt.setText("");
                amountTxt.setText("");
                newRecord = true;
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newRecord){
                    // Insertion
                    if (ownerTxt.getText().length() != 0){
                        acc = new Account(ownerTxt.getText(), (City)citiesCB.getSelectedItem(), maleRbtn.isSelected()? 'M':'F');

                        accNumberTxt.setText(String.valueOf(acc.accNumber));
                        accountsSet.add(acc);
                        accountsLSTMDL.addElement(acc);
                        newRecord = false;
                    }else{
                        JOptionPane.showMessageDialog(null, "Please Fill All Fields");
                    }
                }else{
                    // Updating
                    accountsSet.remove(acc);

                    int a = Integer.parseInt(accNumberTxt.getText());
                    String o = ownerTxt.getText();
                    City c = (City) citiesCB.getSelectedItem();

                    char g = maleRbtn.isSelected()? 'M' : 'F';
                    double b = Double.parseDouble(balanceTxt.getText());
                    acc = new Account(a, o, c, g, b);
                    accountsSet.add(acc);
                    accountsLSTMDL.setElementAt(acc, accountsList.getSelectedIndex());
                    newRecord = false;
                }
            }
        });

        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "";
                Iterator<Account> it = accountsSet.iterator();
                while (it.hasNext()){
                    s += it.next().toString()+"\n";
                    JOptionPane.showMessageDialog(null, s);
                }
            }
        });

        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newRecord && amountTxt.getText().length() != 0){
                    // Adding Transaction to table
                    Transaction t = new Transaction(acc, LocalDate.now(), 'D', Double.parseDouble(amountTxt.getText()));
                    DisplayTransactionsInTable(t);

                    // Perform deposit from account
                    acc.deposit(Double.parseDouble(amountTxt.getText()));
                    balanceTxt.setText(String.valueOf(acc.balance));
                }
            }
        });

        withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newRecord && amountTxt.getText().length() != 0){
                    // Adding Transaction to table
                    Transaction t = new Transaction(acc, LocalDate.now(), 'W', Double.parseDouble(amountTxt.getText()));

                DisplayTransactionsInTable(t);

                // Perform withdrawal on account
                acc.withdrawl(Double.parseDouble(amountTxt.getText()));
                balanceTxt.setText(String.valueOf(acc.balance));
                }
            }
        });

        accountsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                acc = x = accountsList.getSelectedValue();
                accNumberTxt.setText(String.valueOf(acc.accNumber));
                ownerTxt.setText(acc.owner);
                citiesCB.setSelectedItem(acc.city);

                if (acc.gender == 'M') maleRbtn.setSelected(true);
                else femaleRbtn.setSelected(true);

                balanceTxt.setText(String.valueOf(acc.balance));
                amountTxt.setEnabled(true);
                depositBtn.setEnabled(true);
                newRecord = false;

                // Clear Table
                for (int i=tableModel.getRowCount()-1; i>=0; i--){
                    tableModel.removeRow(i);
                }

                // Get transactions to selected account
                SearchTransactionList(acc.accNumber);

            }
        });

    }

    private void SearchTransactionList(int accNumber) {
        List<Transaction> filteredList = new ArrayList<>();

        // Iterate through the list
        for (Transaction t: transList){
            // Filter values that contains trsNo.
            if (t.getAcc().accNumber == accNumber){
                filteredList.add(t);
            }
        }

        // Display the filtered list
        for (int i=0; i<filteredList.size(); i++){

            // Displaying Data into table
            tableModel.addRow(new Object[]{
                    filteredList.get(i).getTransNumber(),
                    filteredList.get(i).getDate(),
                    filteredList.get(i).getOperation(),
                    filteredList.get(i).getAmount()
            });
        }

    }

    private void DisplayTransactionsInTable(Transaction t) {
        // Displaying Data into Table
        tableModel.addRow(new Object[]{t.getTransNumber(), t.getDate(), t.getOperation(), t.getAmount()});

        // Adding object to arraylist
        transList.add(t);
    }



    public static void main(String[] args){
        AccountFrame af = new AccountFrame();
        af.setVisible(true);
        af.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
