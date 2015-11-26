package com.example.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.Date;

@JsonPropertyOrder({"emp_id", "name", "joined_date", "department"})
public class EmployeeDto implements Serializable {

    @JsonProperty("emp_id")
    private Integer empId;
    
    private String name;
    
    @JsonProperty("joined_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
    private Date joinedDate;
    
    private DepartmentDto department;

    /**
     * @return the empId
     */
    public Integer getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the joinedDate
     */
    public Date getJoinedDate() {
        return joinedDate;
    }

    /**
     * @param joinedDate the joinedDate to set
     */
    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    /**
     * @return the department
     */
    public DepartmentDto getDepartment() {
        return department;
    }

    /**
     * @param departmentDto the department to set
     */
    public void setDepartment(DepartmentDto departmentDto) {
        this.department = departmentDto;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" + "empId=" + empId + ", name=" + name + ", joinedDate=" + joinedDate + ", departmentDto=" + department + '}';
    }
    
}
