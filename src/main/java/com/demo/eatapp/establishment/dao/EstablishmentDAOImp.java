package com.demo.eatapp.establishment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.demo.eatapp.establishment.model.Establishment;


@Repository
public class EstablishmentDAOImp implements EstablishmentDAO {
	
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public EstablishmentDAOImp() {
		
	}

	
	
	@Override
	public List<Establishment> getList(String username){
		String sql = "SELECT * FROM establishments WHERE username = '" + username + "'";
		List<Establishment> usersEstablishments = jdbcTemplate.query(sql, new RowMapper<Establishment>() {
			public Establishment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Establishment anEstablishment = new Establishment();
				anEstablishment.setFhrsID(rs.getInt("fhrsID"));
				anEstablishment.setName(rs.getString("name"));
				anEstablishment.setType(rs.getString("type"));
				anEstablishment.setTypeID(rs.getInt("typeID"));
				anEstablishment.setAddressLine1(rs.getString("addressLine1"));
				anEstablishment.setAddressLine1(rs.getString("addressLine2"));
				anEstablishment.setAddressLine1(rs.getString("addressLine3"));
				anEstablishment.setAddressLine1(rs.getString("addressLine4"));
				anEstablishment.setPostcode(rs.getString("postcode"));
				anEstablishment.setPhone(rs.getString("phone"));
				anEstablishment.setRatingValue(rs.getInt("ratingValue"));
				anEstablishment.setRatingDate(LocalDate.parse(rs.getString("ratingDate")));
				return anEstablishment;
			}
		});
		//Collections.sort(usersEstablishments);
		return usersEstablishments;
	}
	
	
	@Override
	public void addToList(Establishment est, String username) {
		String sql = "INSERT INTO establishments (username, fhrsID, name, type, typeID, addressLine1, addressLine2, addressLine3, "
				+ "addressLine4, postcode, phone, ratingValue, ratingDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, username, est.getFhrsID(), est.getName(), est.getType(), est.getTypeID(), est.getAddressLine1(), est.getAddressLine2(), est.getAddressLine3(), 
				 est.getAddressLine4(), est.getPostcode(), est.getPhone(), est.getRatingValue(), est.getRatingDate());
	}

	
	
	
	
	
	
	
	/*

	@Override
	public void deleteCompany(int CompanyId) {
		String sql = "DELETE FROM Company WHERE id=?";
		jdbcTemplate.update(sql, CompanyId);
	}

	*/
	
	
	
}
