package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.EmployeeDAO;
import org.example.dto.EmployeeFilterDto;
import org.example.exceptions.DataNotFoundException;
import org.example.models.Employees;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/employees")
public class EmployeeController {

    EmployeeDAO dao = new EmployeeDAO();

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllEmployees(@BeanParam EmployeeFilterDto filterDto) throws SQLException, ClassNotFoundException {

        GenericEntity<ArrayList<Employees>>  employee = new GenericEntity<ArrayList<Employees>> (dao.selectAllEmployees(filterDto)){};
       try {
           if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
               return Response
                       .ok(employee)
                       .type(MediaType.APPLICATION_XML)
                       .build();
           }

        return Response
                .ok(employee, MediaType.APPLICATION_JSON)
                .build();
    }
    catch (Exception e)
    {
        throw new RuntimeException(e);
    }
    }

    @GET
    @Path("{employees_id}")
    public Response getEmployee(@PathParam("employees_id") int employees_id) {
        try {
            Employees employees = dao.selectEmployees(employees_id);

            if (employees == null) {
                throw new DataNotFoundException("employee with ID " + employees_id + " not found");
            }
//            EmployeeFilterDto dto = new EmployeeFilterDto();
//            dto.setJobId(employees.getJob_id());
//            dto.setMaxSalary(employees.getMax_salary());
//            dto.setMinSalary(employees.getMin_salary());
//            dto.setJob_title(employees.getJob_title());
//            addLink(dto);

            return Response.ok(employees).build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
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
    public Response insertEmployees(Employees employees) {
        try {
            dao.insertEmployees(employees);
            NewCookie cookie = (new NewCookie.Builder("username")).value("OOOOO").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(employees.getJob_id()+"").build();
            return Response
                    .created(uri)
                    .cookie(cookie)
                    .header("Created by", "Wadha")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
    }}

    @PUT
    @Path("{employees_id}")
    public void updateEmployees(@PathParam("employees_id") int employees_id, Employees employees) {



        try {
            employees.setEmployee_id(employees_id);
            dao.updateEmployees(employees);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
