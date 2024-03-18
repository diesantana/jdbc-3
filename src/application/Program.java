package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
					+ "VALUES (?, ?, ?, ?, ?)");

			preparedSt.setString(1, "Walter White");
			preparedSt.setString(2, "walter-white@gmail.com");

			// LocalDate date = LocalDate.parse("18/03/1967", format);
			// converte para a data SQL
			// Date dateSQL = Date.valueOf(date);
			preparedSt.setDate(3, Date.valueOf(LocalDate.parse("18/03/1967", format)));
			preparedSt.setDouble(4, 3000.0);
			preparedSt.setInt(5, 4);

			int changedLines = preparedSt.executeUpdate();

			System.out.println("Done! Rows affected: " + changedLines);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(preparedSt);
			DB.closeConnection();
		}

	}

}
