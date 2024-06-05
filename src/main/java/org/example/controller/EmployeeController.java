package org.example.controller;

import jakarta.ws.rs.*;
import org.example.dao.EmployeeDAO;
import org.example.models.Employees;

import java.util.ArrayList;

@Path("/employees")
public class EmployeeController {

    EmployeeDAO dao = new EmployeeDAO();

    @GET
    public ArrayList<Employees> getAllEmployees() {
        try {
            return dao.selectAllEmployees();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{employees_id}")
    public Employees getEmployee(@PathParam("employees_id") int employees_id) {
        try {
            return dao.selectEmployees(employees_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("{employees_id}")
    public void deleteEmployees(@PathParam("employees_id") int employees_id) {
        try {
            dao.deleteEmployees(employees_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    public void insertEmployees(Employees employees) {
        try {
            dao.insertEmployees(employees);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{employees_id}")
    public void updateEmployees(@PathParam("employees_id") int employees_id, Employees employees) {
        try {
            employees.setJob_id(employees_id);
            dao.updateEmployees(employees);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
