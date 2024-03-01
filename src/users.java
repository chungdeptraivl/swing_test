import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.event.ListSelectionListener;

public class users {

    private JFrame frame;
    private JTextField txtID;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtCity;
    private JTable table;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                users window = new users();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public users() {
        initialize();
        connect();
        loadData();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/dbjoes?characterEncoding=utf8";
            String username = "root";
            String password = "";
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {
        try {
            pst = con.prepareStatement("SELECT * FROM users");
            rs = pst.executeQuery();
            table.setModel(buildTableModel(rs));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("CURD Operation Swing MySQL");
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);

        JLabel lblNewLabel = new JLabel("User Management System");
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(10, 11, 259, 60);
        frame.getContentPane().add(lblNewLabel);

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(20, 71, 387, 284);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Id");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(21, 46, 46, 24);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Name");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1_1.setBounds(21, 81, 46, 24);
        panel.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Age");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1_2.setBounds(21, 116, 46, 24);
        panel.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("City");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1_3.setBounds(21, 154, 46, 24);
        panel.add(lblNewLabel_1_3);

        txtID = new JTextField();
        txtID.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtID.setBounds(78, 46, 287, 24);
        txtID.setColumns(10);
        panel.add(txtID);

        txtName = new JTextField();
        txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtName.setColumns(10);
        txtName.setBounds(78, 81, 287, 24);
        panel.add(txtName);

        txtAge = new JTextField();
        txtAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtAge.setColumns(10);
        txtAge.setBounds(78, 120, 287, 24);
        panel.add(txtAge);

        txtCity = new JTextField();
        txtCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtCity.setColumns(10);
        txtCity.setBounds(78, 155, 287, 24);
        panel.add(txtCity);

        // ADD
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBounds(78, 195, 89, 23);
        panel.add(btnSave);


        // SUA
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnUpdate.setBounds(177, 195, 89, 23);
        panel.add(btnUpdate);


        // XOA
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDelete.setBounds(276, 195, 89, 23);
        panel.add(btnDelete);


        // SEARCH
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchById();
            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSearch.setBounds(78, 235, 89, 23);
        panel.add(btnSearch);

        // table

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(417, 71, 467, 284);

        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int index = table.getSelectedRow();
                    TableModel model = table.getModel();
                    txtID.setText(model.getValueAt(index, 0).toString());
                    txtName.setText(model.getValueAt(index, 1).toString());
                    txtAge.setText(model.getValueAt(index, 2).toString());
                    txtCity.setText(model.getValueAt(index, 3).toString());
                }
            }
        });
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setForeground(new Color(8, 48, 148));
        table.setGridColor(new Color(47, 47, 47));
        table.setDefaultEditor(Object.class, null); // Disable cell editing
        scrollPane.setViewportView(table);
        frame.setBounds(100, 100, 910, 522);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void saveData() {
        String name = txtName.getText();
        String age = txtAge.getText();
        String city = txtCity.getText();
        if (name.isEmpty() || age.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
            return;
        }
        try {
            // Create a new User object
            User newUser = new User(0, name, Integer.parseInt(age), city);
            String sql = "INSERT INTO users (NAME, AGE, CITY) VALUES (?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, newUser.getName());
            pst.setInt(2, newUser.getAge());
            pst.setString(3, newUser.getCity());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data inserted successfully");
            clearFields();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateData() {
        String id = txtID.getText();
        String name = txtName.getText();
        String age = txtAge.getText();
        String city = txtCity.getText();
        if (id.isEmpty() || name.isEmpty() || age.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a record to update");
            return;
        }
        try {
            User updatedUser = new User(Integer.parseInt(id), name, Integer.parseInt(age), city);
            String sql = "UPDATE users SET NAME=?, AGE=?, CITY=? WHERE ID=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, updatedUser.getName());
            pst.setInt(2, updatedUser.getAge());
            pst.setString(3, updatedUser.getCity());
            pst.setInt(4, updatedUser.getId());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data updated successfully");
            clearFields();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteData() {
        String id = txtID.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a record to delete");
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete Record", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM users WHERE ID=?";
                pst = con.prepareStatement(sql);
                pst.setString(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data deleted successfully");
                clearFields();
                loadData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void searchById() {
        String id = txtID.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an ID to search");
            return;
        }
        try {
            pst = con.prepareStatement("SELECT * FROM users WHERE ID=?");
            pst.setString(1, id);
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No user found with this ID");
            } else {
                table.setModel(buildTableModel(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtCity.setText("");
    }

    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        int columnCount = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            model.addColumn(rs.getMetaData().getColumnName(i));
        }
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            model.addRow(row);
        }
        return model;
    }
}
