package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement preparedSt = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		try {
			conn = DB.connectDb();
			preparedSt = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			preparedSt.setString(1, "Walter White");
			preparedSt.setString(2, "walter-white@gmail.com");


			preparedSt.setDate(3, Date.valueOf(LocalDate.parse("18/03/1967", format)));
			preparedSt.setDouble(4, 3000.0);
			preparedSt.setInt(5, 4);

			int changedLines = preparedSt.executeUpdate();
			
			
			if (changedLines > 0) {
				try {
				ResultSet result = preparedSt.getGeneratedKeys();
				while(result.next()) {
					int generatedId = result.getInt(1);	
					System.out.println("Done! Id: " + generatedId);
				}
				} catch (Exception e) {
					System.out.println("Error returning ID: " + e.getMessage());
				}
				
				
				System.out.println("Rows affected: " + changedLines);
				
				
			} else {
				System.out.println("No Rows affected!");
			}


		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(preparedSt);
			DB.closeConnection();
		}

	}

}
