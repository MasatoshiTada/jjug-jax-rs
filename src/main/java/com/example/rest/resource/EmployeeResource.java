package com.example.rest.resource;

import com.example.persistence.entity.Department;
import com.example.persistence.entity.Employee;
import com.example.rest.dto.DepartmentDto;
import com.example.rest.dto.EmployeeDto;
import com.example.rest.form.EmployeeForm;
import com.example.rest.util.DateUtil;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author tada
 */
@Path("employees")
@RequestScoped
public class EmployeeResource {
    
    @Inject
    private EmployeeService employeeService;
    @Inject
    private DepartmentService departmentService;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEmpId(@PathParam("id") Integer id) throws Exception {
        Employee employee = employeeService.findByEmpId(id)
                .orElseThrow(() -> new NotFoundException("該当する社員が見つかりませんでした。"));
        EmployeeDto employeeDto = convertToDto(employee);
        return Response.ok(employeeDto).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@QueryParam("name") @DefaultValue("") 
            @Pattern(regexp = "[a-zA-Z]*", message = "{employee.name.pattern.alphabet}")
            @Size(max = 10, message = "{employee.name.size.string}")
            String name) throws Exception {
        List<Employee> entityList = employeeService.findByName(name);
        List<EmployeeDto> dtoList = convertToDtoList(entityList);
        return Response.ok(dtoList).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@Valid EmployeeForm employeeForm) throws Exception {
        // 部署の存在確認
        Integer deptId = Integer.valueOf(employeeForm.getDepartmentForm().getDeptId());
        if (!departmentService.exists(deptId)) {
            throw new BadRequestException("該当する部署が存在しません。");
        }
        
        Employee employee = convertToEntity(null, employeeForm);
        // 新規追加
        employeeService.insert(employee);
        // 改めてDBから取得
        Employee employeeFromDB = employeeService.findByEmpId(employee.getEmpId()).get();
        EmployeeDto employeeDto = convertToDto(employeeFromDB);
        
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(employee.getEmpId().toString()).build();
        
        return Response.created(location).entity(employeeDto).build();
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") 
            @Min(value = 1, message = "{employee.empId.min}") Integer id, 
            @Valid EmployeeForm employeeForm) throws Exception {
        // 部署の存在確認
        Integer deptId = Integer.valueOf(employeeForm.getDepartmentForm().getDeptId());
        if (!departmentService.exists(deptId)) {
            throw new BadRequestException("該当する部署が存在しません。");
        }
        
        if (!employeeService.exists(id)) {
            throw new NotFoundException("該当する社員が見つかりませんでした。");
        }
        Employee employee = convertToEntity(id, employeeForm);
        // 更新
        employeeService.update(employee);
        // 改めてDBから取得
        Employee employeeFromDB = employeeService.findByEmpId(employee.getEmpId()).get();
        EmployeeDto employeeDto = convertToDto(employeeFromDB);
        URI location = uriInfo.getAbsolutePath();
        return Response.ok(employeeDto).location(location).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") 
            @Min(value = 1, message = "{employee.empId.min}") Integer id) throws Exception {
        if (!employeeService.exists(id)) {
            throw new NotFoundException("該当する社員が見つかりませんでした。");
        }
        employeeService.delete(id);
        
        URI location = uriInfo.getAbsolutePath();
        return Response.noContent().location(location).build();
    }
    
    private EmployeeDto convertToDto(Employee entity) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmpId(entity.getEmpId());
        employeeDto.setName(entity.getName());
        employeeDto.setJoinedDate(entity.getJoinedDate());
        
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDeptId(entity.getDepartment().getDeptId());
        departmentDto.setName(entity.getDepartment().getName());
        employeeDto.setDepartment(departmentDto);
        
        return employeeDto;
    }
    
    private List<EmployeeDto> convertToDtoList(List<Employee> entityList) {
        return entityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private Employee convertToEntity(Integer empId, EmployeeForm form) {
        Employee employee = new Employee();
        employee.setEmpId(empId);
        employee.setName(form.getName());
        employee.setJoinedDate(DateUtil.toDate(form.getJoinedDate(), "yyyy-MM-dd"));
        
        Department department = new Department();
        department.setDeptId(Integer.valueOf(form.getDepartmentForm().getDeptId()));
        employee.setDepartment(department);
        
        return employee;
    }
}
