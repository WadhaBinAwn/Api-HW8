package org.example.controller;
import jakarta.ws.rs.core.GenericEntity;
import java.util.List;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.EmployeeDAO;
import org.example.dto.EmployeeFilterDto;
import org.example.dto.EmployeesDto;
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response getAllEmployees(@BeanParam EmployeeFilterDto filter) {
        try {
            ArrayList<Employees> employees = dao.selectAllEmployees(filter);

            for (Employees employee : employees) {
                addLinks(employee, uriInfo);
            }

            GenericEntity<ArrayList<Employees>> employeeEntity = new GenericEntity<ArrayList<Employees>>(employees) {};

//            Response.ResponseBuilder responseBuilder = Response.ok(employeeEntity);
            if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(employeeEntity)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            }
            else if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(employeeEntity)
                        .type("text/csv")
                        .build();
            }

            return Response
                    .ok(employeeEntity, MediaType.APPLICATION_JSON)
                    .build();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        try {
            dao.deleteEmployee(id);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    public Response insertEmployee(Employees employee) {
        try {
            dao.insertEmployee(employee);
            URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(employee.getEmployee_id())).build();
            return Response.created(uri).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{id}")
    public Response updateEmployee(@PathParam("id") int id, Employees employee) {
        try {
            employee.setEmployee_id(id);
            dao.updateEmployee(employee);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getEmployee(@PathParam("id") int id) {
        try {
            Employees employee = dao.selectEmployeeById(id);
            if (employee == null) {
                throw new DataNotFoundException("Employee " + id + " not found");
            }

            EmployeesDto dto = new EmployeesDto();
            dto.setEmployeeId(employee.getEmployee_id());
            dto.setHireDate(employee.getHire_date());
            dto.setJobId(employee.getJob_id());

            addLinks(dto);

            return Response.ok(dto).build();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void addLinks(EmployeesDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        dto.addLink(selfUri.toString(), "self");


        URI empsUri = uriInfo.getBaseUriBuilder().path(EmployeeController.class).build();
        dto.addLink(empsUri.toString(), "employee job");
    }


    private void addLinks(Employees employee, UriInfo uriInfo) {

        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();


        String employeeUri = uriBuilder.path(EmployeeController.class)
                .path(Integer.toString(employee.getEmployee_id()))
                .build().toString();


        employee.addLink(employeeUri, "self");
    }
}


//    EmployeeDAO dao = new EmployeeDAO();
//
//    @Context
//    private UriInfo uriInfo;
//
//    @Context
//    private HttpHeaders headers;
//
//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response getAll(@BeanParam EmployeeFilterDto filterDto) {
//        try {
//            ArrayList<Employees> employees;
//            if (filterDto.getHireYear() != null) {
//                employees = dao.getByHireYear(filterDto.getHireYear());
//            } else if (filterDto.getJobId() != null) {
//                employees = dao.getByJobId(filterDto.getJobId());
//            } else {
//                employees = dao.getAll(filterDto);
//            }
//
//            for (Employees employee : employees) {
//                addLinks(employee, uriInfo);
//            }
//
//            GenericEntity<ArrayList<Employees>> employeeEntity = new GenericEntity<ArrayList<Employees>>(employees) {};
//
//            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
//                return Response.ok(employeeEntity).type(MediaType.APPLICATION_XML).build();
//            } else {
//                return Response.ok(employeeEntity, MediaType.APPLICATION_JSON).build();
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @DELETE
//    @Path("{id}")
//    public Response delete(@PathParam("id") int id) {
//        try {
//            dao.deleteEmployees(id);
//            return Response.noContent().build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @POST
//    public Response insert(Employees employee) {
//        try {
//            dao.insertEmployees(employee);
//            URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(employee.getEmployee_id())).build();
//            return Response.created(uri)
//                    .cookie(new NewCookie("username", "OOOOO"))
//                    .header("Created by", "Wadha")
//                    .build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @PUT
//    @Path("{id}")
//    public Response update(@PathParam("id") int id, Employees employee) {
//        try {
//            employee.setEmployee_id(id);
//            dao.updateEmployees(employee);
//            return Response.noContent().build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response get(@QueryParam("hireYear") Integer hireYear,
//                        @QueryParam("jobId") Integer jobId,
//                        @Context UriInfo uriInfo) {
//        try {
//            ArrayList<Employees> employees;
//            if (hireYear != null) {
//                employees = dao.getByHireYear(hireYear);
//            } else if (jobId != null) {
//                employees = dao.getByJobId(jobId);
//            } else {
//                // If neither hireYear nor jobId is provided, return all employees
//                employees = dao.getAll(null);
//            }
//
//            // Add links to each employee
//            for (Employees employee : employees) {
//                addLinks(employee, uriInfo);
//            }
//
//            return Response.ok(employees).build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // Method to add links to each employee
//    private void addLinks(Employees employee, UriInfo uriInfo) {
//        // Get the base URI
//        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
//
//        // Create link for the employee
//        String employeeUri = uriBuilder.path(EmployeeController.class)
//                .path(Integer.toString(employee.getId()))
//                .build().toString();
//
//        // Add the link to the employee object
//        employee.addLink(employeeUri, "self");
//    }
//}
