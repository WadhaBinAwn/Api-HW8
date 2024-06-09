package org.example.helpers;

import jakarta.ws.rs.ext.ParamConverter;
import org.example.dto.EmployeeIdDto;

public class EmployeeIdParamConverter implements ParamConverter {

    @Override
    public Object fromString(String value) {

        EmployeeIdDto empId = new EmployeeIdDto();
        empId.setDeptCode(value.substring(0, 2));
        empId.setSeq(Integer.parseInt(value.substring(2, 6)));
        empId.setHireYear(Integer.parseInt(value.substring(6)));

        return empId;
    }

    @Override
    public String toString(Object value) {
        return value.toString();
    }
}
