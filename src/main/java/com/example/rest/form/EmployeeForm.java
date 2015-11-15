package com.example.rest.form;

import com.example.rest.validation.DatePattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author tada
 */
public class EmployeeForm {

    @NotNull(message = "{employee.name.notnull}")
    @Size(min = 1, max = 40, message = "{employee.name.size.string}")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "{employee.name.pattern.alphabet.or.space}")
    private String name;

    @DatePattern(pattern = "yyyy-MM-dd", message = "{employee.joinedDate.pattern}")
    @JsonProperty("joined_date")
    private String joinedDate;

    @Valid
    @JsonProperty("department")
    private DepartmentForm departmentForm = new DepartmentForm();

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
    public String getJoinedDate() {
        return joinedDate;
    }

    /**
     * @param joinedDate the joinedDate to set
     */
    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    /**
     * @return the departmentForm
     */
    public DepartmentForm getDepartmentForm() {
        return departmentForm;
    }

    /**
     * @param departmentForm the departmentForm to set
     */
    public void setDepartmentForm(DepartmentForm departmentForm) {
        this.departmentForm = departmentForm;
    }
    
    
}
