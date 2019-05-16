package com.example.demo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;
import com.example.demo.mapper.EmployeeRowMapper;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
	
	private NamedParameterJdbcTemplate template;
	
	public EmployeeDaoImpl(NamedParameterJdbcTemplate template) {
		this.template = template;
	}
	
	@Override
	public List<Employee> findAll() {
	return template.query("select * from employee", new EmployeeRowMapper());
	}

	@Override
	public void insertEmployee(Employee emp) {
		final String sql = "insert into employee(employeeId, employeeName , employeeAddress,employeeEmail) values(:employeeId,:employeeName,:employeeEmail,:employeeAddress)";
	    KeyHolder holder = new GeneratedKeyHolder();
	    SqlParameterSource param = new MapSqlParameterSource()
	    			.addValue("employeeId", emp.getId())
	    			.addValue("employeeName", emp.getName())
	    			.addValue("employeeEmail", emp.getEmail())
	    			.addValue("employeeAddress", emp.getAddress());
	    template.update(sql,param, holder);
	}

	@Override
	public void updateEmployee(Employee emp) {
		final String sql = "update employee set employeeName=:employeeName, employeeAddress=:employeeAddress, employeeEmail=:employeeEmail where employeeId=:employeeId";
	    KeyHolder holder = new GeneratedKeyHolder();
	    SqlParameterSource param = new MapSqlParameterSource()
	    		.addValue("employeeId", emp.getId())
	    		.addValue("employeeName", emp.getName())
	    		.addValue("employeeEmail", emp.getEmail())
	    		.addValue("employeeAddress", emp.getAddress());
	    template.update(sql,param, holder);
	}

	@Override
	public void executeUpdateEmployee(Employee emp) {
	 final String sql = "update employee set employeeName=:employeeName, employeeAddress=:employeeAddress, employeeEmail=:employeeEmail where employeeId=:employeeId";
	 Map<String,Object> map=new HashMap<String,Object>();  
	 map.put("employeeId", emp.getId());
	 map.put("employeeName", emp.getName());
	 map.put("employeeEmail", emp.getEmail());
	 map.put("employeeAddress", emp.getAddress());
	 template.execute(sql,map,new PreparedStatementCallback<Object>() {  
	    @Override  
	    public Object doInPreparedStatement(PreparedStatement ps)  
	            throws SQLException, DataAccessException {  
	        return ps.executeUpdate();  
	    }  
	});  
	}

	@Override
	public void deleteEmployee(Employee emp) {
	 final String sql = "delete from employee where employeeId=:employeeId";
	 Map<String,Object> map=new HashMap<String,Object>();  
	 map.put("employeeId", emp.getId());
	 template.execute(sql,map,new PreparedStatementCallback<Object>() {  
	    @Override  
	    public Object doInPreparedStatement(PreparedStatement ps)  
	            throws SQLException, DataAccessException {  
	        return ps.executeUpdate();  
	    }  
	});  
	}

}
