package com.example.rest.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 
 * @author tada
 */
public class DepartmentForm {

    @JsonProperty("dept_id")
    @NotNull(message = "{deptId.notnull}")
    @Pattern(regexp = "[1-9][0-9]*", message = "{deptId.pattern.integer}")
    private String deptId;

    /**
     * @return the deptId
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
