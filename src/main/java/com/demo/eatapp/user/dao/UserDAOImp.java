package com.demo.eatapp.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.user.model.User;

@Repository
public class UserDAOImp implements UserDAO {
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public UserDAOImp() {
		
	}

	
	@Override
	public List<User> getUsers(){
		String sql = "SELECT * FROM users";
		List<User> users = jdbcTemplate.query(sql, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setAge(rs.getInt("age"));
				user.setPostcode(rs.getString("postcode"));
				user.setPhone(rs.getString("phone"));
				return user;
			}
		});
		//Maybe sort alpha?
		return users;
	}
	
	
	@Override
	public User getUser(String username) {
		String sql = "SELECT * FROM users WHERE username='" + username +"';";
		return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {
			@Override
			public User extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setFirstName(rs.getString("firstname"));
					user.setLastName(rs.getString("lastname"));
					user.setAge(rs.getInt("age"));
					user.setPostcode(rs.getString("postcode"));
					user.setPhone(rs.getString("phone"));
					return user;
				}
				return null;
			}
		});
	}

}
