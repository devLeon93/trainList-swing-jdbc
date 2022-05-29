package model;

import controller.DBOController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TrainList extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem connect;

    private JButton find;
    private JComboBox<String> listFrom, listTo;

    private DBOController dboController;
    private JTable table;

    String[] columnName =
            {
                    "Name of Train",
                    "Number",
                    "Type of Places",
                    "Departure"

            };


    public TrainList(){

        super("Train Schedule");

        this.setLayout(new BorderLayout());

        Dimension windowsDimension = Toolkit.getDefaultToolkit().getScreenSize();

        JPanel comandPanel = new JPanel();

        this.menu = new JMenu("Options");

        this.connect =  new JMenuItem("Connect");
        this.connect.addActionListener(this);
        this.menu.add(this.connect);

        this.menuBar = new JMenuBar();
        this.menuBar.add(this.menu);
        this.setJMenuBar(this.menuBar);


        JLabel from = new JLabel("Departure");
        JLabel to = new JLabel("End");


        listFrom = new JComboBox<String>();
        listTo = new JComboBox<String>();


        find = new JButton("Search");
        find.addActionListener(this);
        find.setEnabled(false);


        comandPanel.setMaximumSize(new Dimension(200,300));
        comandPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        comandPanel.setLayout(new GridLayout(6, 1));
        comandPanel.add(from);
        comandPanel.add(this.listFrom);
        comandPanel.add(to);
        comandPanel.add(this.listTo);
        comandPanel.add(this.find);


        JPanel p = new JPanel();
        p.setSize(300, 300);
        p.setBackground(Color.red);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(150 + split.getInsets().left);

        split.add(comandPanel);

        this.table = new JTable();

        split.add(this.table);


        this.dboController = new DBOController(this);
        this.dboController.connect();
        this.find.setEnabled(true);
        this.add(split);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 300);
        this.setLocation(
                windowsDimension.width/2 - this.getSize().width/2,
                windowsDimension.height/2 - this.getSize().height/2);
        this.setResizable(false);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(connect.getText())){
            this.dboController.connect();
            this.find.setEnabled(true);
        }else if(e.getActionCommand().equals(find.getText())){
            this.dboController.findTrains();
        }

    }

    public JComboBox<String> getListFrom() {
        return listFrom;
    }


    public JComboBox<String> getListTo() {
        return listTo;
    }

    public JTable getTable() {
        return table;
    }

    public String[] getColumnName() {
        return columnName;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TrainList();
            }
        });
    }
}







