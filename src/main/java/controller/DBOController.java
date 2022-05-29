package controller;

import model.TrainList;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBOController {

    private TrainList mainTrainList;

    private static final String DB_USER = "trainUser";
    private static final String DB_PASS = "trainpass";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/trainlist";


    private Connection connection;


    String getAllCities = "SELECT name FROM Cities";
    String start =        "Select t.name," +
            "t.number," +
            "t.type," +
            "s.time_going  " +
            "From Station s " +
            "INNER JOIN Cities c  on s.city_id = c.id " +
            "INNER JOIN Train t on s.train_id = t.id";


    String where[] = {" where s.city_id = ", " and s.time_going != '00:00:00' and s.time_going >= CONVERT(s.time_going, DATE)",
            " and t.id in (select train_id from Station where time_going > '00:00:00' )"};


    public DBOController(TrainList trainList) {

        this.mainTrainList = trainList;

    }



    public void connect() {
        try {

            //System.out.println("Start connection");

            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            System.out.println(this.connection.isClosed());


            ResultSet results = connection.createStatement().executeQuery(this.getAllCities);


            List<String> queryList = new ArrayList<String>();

            int i = 0;
            while (results.next())
                queryList.add(results.getString("name"));


            for (String s : queryList) {
                this.mainTrainList.getListFrom().addItem(s);
                this.mainTrainList.getListTo().addItem(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findTrains() {

        try {
            String query = start + where[0] + (this.mainTrainList.getListFrom().getSelectedIndex() + 1) + where[1];

            if (mainTrainList.getListTo().getSelectedIndex() < mainTrainList.getListFrom().getSelectedIndex())
                query += where[2];

            ResultSet set = connection.createStatement().executeQuery(query);
            List<List> queryList = new ArrayList<>();

            int i = 0;
            while (set.next()) {
                List<Object> l = new ArrayList<>();
                l.add(set.getString("name"));
                l.add(set.getString("number"));
                l.add(set.getString("type"));
                l.add(set.getString("time_going"));
                queryList.add(l);
            }

            DefaultTableModel model = (DefaultTableModel) this.mainTrainList.getTable().getModel();
            int index = model.getRowCount();

            for (int in = index - 1; in >= 0; in--)
                model.removeRow(in);

            this.mainTrainList.getTable().repaint();

            model.setColumnIdentifiers(this.mainTrainList.getColumnName());
            for (List l : queryList)
                model.addRow(l.toArray());

            this.mainTrainList.getTable().setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
