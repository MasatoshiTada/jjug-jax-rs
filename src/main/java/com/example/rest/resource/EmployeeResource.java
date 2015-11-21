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
// TODO: 演習1-2. パスとして"employees"を指定する

@RequestScoped
public class EmployeeResource {
    
    @Inject
    private EmployeeService employeeService;
    @Inject
    private DepartmentService departmentService;
    
    @Context
    private UriInfo uriInfo;
    
    // TODO: 演習1-3. HTTPメソッドGET、パス"{id}"、生成するメディアタイプ"application/json"を指定する
    // curl -v -H "Accept: application/json" -X GET http://localhost:8080/jjug-jax-rs/api/employees/1
    public Response findByEmpId(
            /* TODO: 演習1-4. パスパラメータidを取得する */
            Integer id) throws Exception {
        Employee employee = employeeService.findByEmpId(id)
                .orElseThrow(() -> new NotFoundException("該当する社員が見つかりませんでした。"));
        EmployeeDto employeeDto = convertToDto(employee);
        // TODO: 演習1-5. HTTPステータスコード200 OKと共に社員データをレスポンスする
        return null;
    }

    // curl -v -H "Accept: application/json" -X GET http://localhost:8080/jjug-jax-rs/api/employees?name=e
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(
            /* TODO: 演習2-1. クエリパラメータnameを取得する。デフォルト値は"" */
            
            /* TODO: 演習2-2. 正規表現"[a-zA-Z\\s]*" (メッセージ"{employee.name.pattern.alphabet.or.space}")、
                     文字列長の最大値10 (メッセージ"{employee.name.size.string}")を指定する。 */
            
            String name) throws Exception {
        List<Employee> entityList = employeeService.findByName(name);
        List<EmployeeDto> dtoList = convertToDtoList(entityList);
        // TODO: 演習2-3. HTTPステータスコード200 OKと共に社員データのリストをレスポンスする
        return null;
    }
    
    // TODO: 演習3-1. HTTPメソッドPOST、消費するメディアタイプ"application/json"を指定する
    // curl -v -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d "{\"name\":\"Mai Shiraishi\", \"joined_date\":\"2015-11-28\", \"department\":{\"dept_id\":1}}" http://localhost:8080/jjug-jax-rs/api/employees
    // curl -v -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d "{\"name\":\"111\", \"joined_date\":\"aaa\", \"department\":{\"dept_id\":\"aaa\"}}" http://localhost:8080/jjug-jax-rs/api/employees
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(
            /* TODO: 演習3-2. この引数に対するBean Validationを有効化する */
            EmployeeForm employeeForm) throws Exception {
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
        
        // TODO: 演習3-3. HTTPステータスコード201 CREATEDと共にロケーションと社員データをレスポンスする
        return null;
    }
    
    // TODO: 演習4-1. HTTPメソッドPUTを指定する
    // curl -v -H "Accept: application/json" -H "Content-Type: application/json" -X PUT -d "{\"name\":\"Nanami Hashimoto\", \"joined_date\":\"2015-11-30\", \"department\":{\"dept_id\":2}}" http://localhost:8080/jjug-jax-rs/api/employees/10
    // curl -v -H "Accept: application/json" -H "Content-Type: application/json" -X PUT -d "{\"name\":\"111\", \"joined_date\":\"aaa\", \"department\":{\"dept_id\":\"aaa\"}}" http://localhost:8080/jjug-jax-rs/api/employees/10
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
        // TODO: 演習4-2. HTTPステータスコード200 OKと共にロケーションと社員データをレスポンスする
        return null;
    }
    
    // TODO: 演習5-1. HTTPメソッドDELETEを指定する
    // curl -v -H "Accept: application/json" -X DELETE http://localhost:8080/jjug-jax-rs/api/employees/10
    @Path("{id}")
    public Response delete(@PathParam("id") 
            @Min(value = 1, message = "{employee.empId.min}") Integer id) throws Exception {
        if (!employeeService.exists(id)) {
            throw new NotFoundException("該当する社員が見つかりませんでした。");
        }
        employeeService.delete(id);
        
        URI location = uriInfo.getAbsolutePath();
        // TODO: 演習5-2. HTTPステータスコード204 NO CONTENTと共にロケーションをレスポンスする
        return null;
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
