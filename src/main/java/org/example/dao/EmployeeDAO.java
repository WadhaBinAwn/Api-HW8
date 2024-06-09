
package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.dto.EmployeeFilterDto;
import org.example.models.Employees;

public class EmployeeDAO {


    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\Api_HW08\\src\\main\\resources\\hr.db";

    private static final String SELECT_ALL_EMPLOYEES = "select * from employees";
    private static final String SELECT_ALL_EMPLOYEES_WITH_HDATE = "select * FROM employees WHERE hire_date LIKE ? || '%'";
    private static final String SELECT_ALL_EMPLOYEES_WITH_JOBID = "SELECT * from employees WHERE job_id = ?";

    private static final String SELECT_ONE_EMPLOYEE = "select * from employees where employee_id = ?";
    private static final String INSERT_EMPLOYEE = "INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE employees set first_name = ?, last_name = ? WHERE employee_id = ?";
    private static final String DELETE_EMPLOYEE = "delete from employees where employee_id = ?";

    public EmployeeDAO() {
    }

    public void insertEmployee(Employees d) throws SQLException, ClassNotFoundException { // Changed method name
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement st = conn.prepareStatement(INSERT_EMPLOYEE);
        st.setInt(1, d.getEmployee_id());
        st.setString(2, d.getFirst_name());
        st.setString(3, d.getLast_name());
        st.setString(4, d.getEmail());
        st.setString(5, d.getPhone_number());
        st.setString(6, d.getHire_date());
        st.setInt(7, d.getJob_id());
        st.setDouble(8, d.getSalary());
        st.setInt(9, d.getManager_id());
        st.setInt(10, d.getDepartment_id());

        st.executeUpdate();
        conn.close();
    }

    public void updateEmployee(Employees d) throws SQLException, ClassNotFoundException { // Changed method name
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement st = conn.prepareStatement(UPDATE_EMPLOYEE);
        st.setInt(3, d.getEmployee_id());
        st.setString(1, d.getFirst_name());
        st.setString(2, d.getLast_name());
        st.executeUpdate();
        conn.close();
    }

    public void deleteEmployee(int empId) throws SQLException, ClassNotFoundException { // Changed method name
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement st = conn.prepareStatement(DELETE_EMPLOYEE);
        st.setInt(1, empId);
        st.executeUpdate();
        conn.close();
    }

    public Employees selectEmployeeById(int empId) throws SQLException, ClassNotFoundException { // Changed method name
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_EMPLOYEE);
        st.setInt(1, empId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new Employees(rs);
        } else {
            return null;
        }
    }

    public ArrayList<Employees> selectAllEmployees(EmployeeFilterDto filter) throws SQLException, ClassNotFoundException { // Changed method name
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement st;
        if (filter.getHireDate() != null) {
            st = conn.prepareStatement(SELECT_ALL_EMPLOYEES_WITH_HDATE);
            st.setString(1, filter.getHireDate());
        } else if (filter.getJobId() != null) {
            st = conn.prepareStatement(SELECT_ALL_EMPLOYEES_WITH_JOBID);
            st.setInt(1, filter.getJobId());
        } else {
            st = conn.prepareStatement(SELECT_ALL_EMPLOYEES);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<Employees> emps = new ArrayList<>();
        while (rs.next()) {
            emps.add(new Employees(rs));
        }
        rs.close();
        return emps;
    }}





//    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\Api_HW08\\src\\main\\resources\\hr.db";
//    private static final String SELECT_ALL_EMPLOYS = "select * from employees";
//    private static final String SELECT_ONE_EMPLOYS = "select * from employees where employee_id = ?";
//    private static final String INSERT_EMPLOYS = "insert into employees values (?, ?, ?,?,?,?,?,?,?,?)";
//    private static final String UPDATE_EMPLOYS= "update employees set first_name = ?, last_name = ? ,email = ? ,phone_number = ? ,hire_date = ? ,job_id = ?,Salary = ? ,manager_id = ? ,department_id = ? where employee_id = ?";
//    private static final String DELETE_EMPLOYS = "delete from employees where employee_id = ?";
//
//    private static final String SELECT_BY_HIRE_DATE = "SELECT * FROM employees WHERE hire_date LIKE ? || '%'";
//    private static final String SELECT_BY_JOB_ID = "SELECT * FROM employees WHERE job_id = ?";
//    private static final String SELECT_employees_WITH_ID = "select * from employees where employee_id = ?";
//    private static final String SELECT_employees_WITH_ID_PAGINATION = "select * from employees where employee_id = ? order by employee_id limit ? offset ?";
//    private static final String SELECT_employees_WITH_PAGINATION = "select * from employees order by min_salary limit ? employee_id ?";
//    public EmployeeDAO() {
//    }
//
//    public void insertEmployees(Employees e) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);
//        PreparedStatement st = conn.prepareStatement(INSERT_EMPLOYS);
//        st.setInt(1, e.getEmployee_id());
//        st.setString(2, e.getFirst_name());
//        st.setString(3, e.getLast_name());
//        st.setString(4, e.getEmail());
//        st.setString(5, e.getPhone_number());
//        st.setString(6, e.getHire_date());
//        st.setInt(7, e.getJob_id());
//        st.setDouble(8, e.getSalary());
//        st.setInt(9, e.getManager_id());
//        st.setInt(10, e.getDepartment_id());
//        st.executeUpdate();
//    }
//
//    public void updateEmployees(Employees e) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);
//        PreparedStatement st = conn.prepareStatement(UPDATE_EMPLOYS);
//        st.setString(1, e.getFirst_name());
//        st.setString(2, e.getLast_name());
//        st.setString(3, e.getEmail());
//        st.setString(4, e.getPhone_number());
//        st.setString(5, e.getHire_date());
//        st.setDouble(6, e.getSalary());
//        st.setInt(7, e.getJob_id());
//        st.setInt(8, e.getEmployee_id());
//        st.setInt(9, e.getDepartment_id());
//        st.setInt(   10, e.getEmployee_id());
//        st.executeUpdate();
//    }
//
//    public void deleteEmployees(int employee_id) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);
//        PreparedStatement st = conn.prepareStatement(DELETE_EMPLOYS);
//        st.setInt(1, employee_id);
//        st.executeUpdate();
//    }
//
//    public Employees selectEmployees(int employee_id) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);
//        PreparedStatement st = conn.prepareStatement(SELECT_ONE_EMPLOYS);
//        st.setInt(1, employee_id);
//        ResultSet rs = st.executeQuery();
//        return rs.next() ? new Employees(rs) : null;
//    }
//
//
//    public ArrayList<Employees> selectAll(EmployeeFilterDto filterDto) throws SQLException, ClassNotFoundException {
//        String query;
//        if (filterDto.getHireYear() != null) {
//            query = SELECT_BY_HIRE_DATE;
//        } else if (filterDto.getJobId() != null) {
//            query = SELECT_BY_JOB_ID;
//        } else {
//            query = SELECT_ALL_EMPLOYS;
//        }
//        try (Connection conn = DriverManager.getConnection(URL);
//             PreparedStatement st = conn.prepareStatement(query)) {
//            if (filterDto.getHireYear() != null) {
//                st.setString(1, filterDto.getHireYear());
//            } else if (filterDto.getJobId() != null) {
//                st.setInt(1, filterDto.getJobId());
//            }
//            try (ResultSet rs = st.executeQuery()) {
//                ArrayList<Employees> employees = new ArrayList<>();
//                while (rs.next()) {
//                    employees.add(new Employees(rs));
//                }
//                return employees;
//            }
//        }
//    }
//}

//    public ArrayList<Employees> selectAllEmployees(EmployeeFilterDto filterDto) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);
//        PreparedStatement st ;
//        if (filterDto.getEmployeeId() != null && filterDto.getLimit() != null){
//st = conn.prepareStatement(SELECT_employees_WITH_ID_PAGINATION);
//            st.setInt(1,filterDto.getEmployeeId());
//            st.setInt(2,filterDto.getLimit());
//                   st.setInt(3,filterDto.getOffset());
//        } else if (filterDto.getEmployeeId()!=null) {
//            st = conn.prepareStatement(SELECT_employees_WITH_ID);
//            st.setInt(1,filterDto.getEmployeeId());
//        } else if (filterDto.getLimit()!=null) {
//            st = conn.prepareStatement(SELECT_employees_WITH_PAGINATION);
//            st.setInt(1,filterDto.getLimit());
//            st.setInt(2,filterDto.getOffset());}
//        else{
//            st = conn.prepareStatement(SELECT_ALL_EMPLOYS);
//
//        }
//
//
//    ResultSet rs = st.executeQuery();
//
//    ArrayList<Employees> employees = new ArrayList<>();
//        while (rs.next()) {
//            employees.add(new Employees(rs));
//    }
//
//        return employees;
//}
//    //HW
//    public ArrayList<Employees> selectEmployeesByHireYear(int hireYear) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);
//        String query = "SELECT * FROM employees WHERE hire_date LIKE ?";
//        PreparedStatement st = conn.prepareStatement(query);
//        st.setString(1, "%" + hireYear + "%"); // Assuming hire_date is stored as a string with the format 'YYYY-MM-DD'
//        ResultSet rs = st.executeQuery();
//
//        ArrayList<Employees> employees = new ArrayList<>();
//        while (rs.next()) {
//            employees.add(new Employees(rs));
//        }
//
//        return employees;
//    }
//
//
//    //HW
//    public ArrayList<Employees> selectEmployeesByJobId(int jobId) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);
//        String query = "SELECT * FROM employees WHERE job_id = ?";
//        PreparedStatement st = conn.prepareStatement(query);
//        st.setInt(1, jobId);
//        ResultSet rs = st.executeQuery();
//
//        ArrayList<Employees> employees = new ArrayList<>();
//        while (rs.next()) {
//            employees.add(new Employees(rs));
//        }
//
//        return employees;
//    }

