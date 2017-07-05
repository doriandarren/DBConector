package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import model.Comments;

public class DBMComments extends DBManager<Comments>{
	
	
	private static Map<Integer, String> map;
	static{
		map = new HashMap<Integer, String>();
		map.put(1, "id");
		map.put(2, "MYUSER");
		map.put(3, "EMAIL");
		map.put(4, "WEBPAGE");
		map.put(5, "DATUM");
		map.put(6, "SUMMARY");
		map.put(7, "COMMENTS");
	}
	
	
	
	public DBMComments(String dbHost, String dbName, String dbTable) {
		super(dbHost, dbName, dbTable);		
	}

	@Override
	public Comments insert(Comments object) throws SQLException {		
		
		int lastInsertId = -1;
		String strSql = "INSERT INTO "+ getDbTable() + " values (default,?,?,?,?,?,?)";
		
		PreparedStatement preparedStatement =null;
		
		try {
			preparedStatement = getConnected()
					.prepareStatement(strSql, Statement.RETURN_GENERATED_KEYS);
						
			preparedStatement.setString(1, object.getMyUser());
			preparedStatement.setString(2, object.getEmail());
			preparedStatement.setString(3, object.getWebpage());
			preparedStatement.setDate(4, object.getDatum());
			preparedStatement.setString(5, object.getSummary());
			preparedStatement.setString(6, object.getComments());
			
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			
			if(rs.next()){
				lastInsertId = rs.getInt(1);
			}
			
			
		} catch (SQLException e) {
			throw e;
		}finally{
			try {
				preparedStatement.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}		
		object.setId(lastInsertId);		
		return object;
	}

	
	/**
	 * "UPDATE tabla set myuser = ?  where id = ?";
	 * @throws SQLException 
	 */
	@Override
	public void update(Comments object) throws SQLException {
		
		ckeckFormatUpdate(object);
		
		String strSql = "UPDATE "+ getDbTable() + " SET " 
				+ " myuser=?,email=?,webpage=?, datum=?, summary=?, comments=?"
				+ " WHERE id=?";		
		PreparedStatement preparedStatement =null;
		
		try {
			preparedStatement = getConnected()
					.prepareStatement(strSql);			
			
			preparedStatement.setString(1, object.getMyUser());
			preparedStatement.setString(2, object.getEmail());
			preparedStatement.setString(3, object.getWebpage());
			preparedStatement.setDate(4, object.getDatum());
			preparedStatement.setString(5, object.getSummary());
			preparedStatement.setString(6, object.getComments());
			preparedStatement.setInt(7, object.getId());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		}finally{
			try {
				preparedStatement.close();
			} catch (Exception e1) {}
		}
		
	}
	
		

	@Override
	protected Comments mapDbToObject(ResultSet resultSet) throws SQLException {
		// lee el resultado i
		int id = resultSet.getInt("id");
		String user = resultSet.getString("myuser");
		String email = resultSet.getString("email");
		String webpage = resultSet.getString("webpage");
		String summary = resultSet.getString("summary");
		Date date = resultSet.getDate("datum");
		String comments = resultSet.getString("comments");

		Comments comment = new Comments();

		comment.setId(id);
		comment.setEmail(email);
		comment.setDatum(date);
		comment.setMyUser(user);
		comment.setSummary(summary);
		comment.setComments(comments);
		comment.setWebpage(webpage);
		return comment;
	}
	
	
	
	/**
	 * Condiciones bajo las cuales se puede actualizar un objeto.
	 * @param object
	 */
	private static void ckeckFormatUpdate(Comments object) {		
		if(object.getId()<=-1){
			throw new RuntimeException("El objeto que trata de actualizar no tiene un id válido");
		}		
	}
	
	
}
