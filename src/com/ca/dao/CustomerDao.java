package com.ca.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import com.ca.bo.CustomerBo;

public class CustomerDao {
	private final String SQL_GET_ALL_CUSTOMERS = "select * from customer";
	private JdbcTemplate jdbcTemplate;

	public CustomerDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<CustomerBo> getAllCustomers() {
		return jdbcTemplate.execute(new GetAllCustomersStatementCallback());
	}

	private final class GetAllCustomersStatementCallback implements StatementCallback<List<CustomerBo>> {

		public List<CustomerBo> doInStatement(Statement stmt) throws SQLException, DataAccessException {
			ResultSet rs = null;
			List<CustomerBo> customers = null;
			CustomerBo bo = null;
			customers = new ArrayList<CustomerBo>();
			rs = stmt.executeQuery(SQL_GET_ALL_CUSTOMERS);

			while (rs.next()) {
				bo = new CustomerBo();
				bo.setCustomerNo(rs.getInt("customer_no"));
				bo.setFirstName(rs.getString("first_nm"));
				bo.setLastName(rs.getString("last_nm"));
				bo.setMobile(rs.getString("mobile"));
				bo.setEmailAddress(rs.getString("email_address"));

				customers.add(bo);
			}
			return customers;
		}

	}

}
