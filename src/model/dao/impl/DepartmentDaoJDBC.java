package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+"(Name) "
					+"VALUES "
					+"(?) ",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			int rowsAfecteds = st.executeUpdate();
			if(rowsAfecteds > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					obj.setId(rs.getInt(1));
				}else {
					throw new DbException("Unexpected error! No rows affected!");
				}
				DB.closeResultSet(rs);
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+"SET Name = ? "
					+"WHERE id = ? "		
					);
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE Id = ? "
					);
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeConnection();
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department WHERE id = ? "
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department department = new Department(rs.getInt("Id"), rs.getString("Name"));
				return department;
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}
		
		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			List<Department> list = new ArrayList<>();
			st = conn.prepareStatement(
					"SELECT * FROM department");
			rs = st.executeQuery();
			while(rs.next()) {
				Department department = new Department(rs.getInt("Id"), rs.getString("Name"));
				list.add(department);
			}
			return list;
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
