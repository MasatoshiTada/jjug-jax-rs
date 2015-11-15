package com.example.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;

@JsonPropertyOrder({"dept_id", "name"})
public class DepartmentDto implements Serializable {
    
    @JsonProperty("dept_id")
    private Integer deptId;
    
    private String name;

    /**
     * @return the deptId
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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

    @Override
    public String toString() {
        return "DepartmentDto{" + "deptId=" + deptId + ", name=" + name + '}';
    }
    
}
