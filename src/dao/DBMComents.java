package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import model.Comments;

public class DBMComents extends DBManager<Comments>{

	public DBMComents(String dbHost, String dbName, String dbTable) {
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
	 * 
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
	
	
	/**
	 * Condiciones bajo las cuales se puede actualizar un objeto.
	 * @param object
	 */
	private static void ckeckFormatUpdate(Comments object) {
				
		if(object.getId()<=-1){
			throw new RuntimeException("El objeto que trata de actualizar no tiene un id válido");
		}
		
		
		
	}

	@Override
	public Comments select(int id) throws SQLException{
		
		String strSql = "SELECT * FROM "+ getDbTable() + " WHERE id=?";	
		PreparedStatement preparedStatement =null;
		Comments comment = null;
		
		try {		
			preparedStatement = getConnected().prepareStatement(strSql);						
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Comments> list = resultSetToComments(resultSet);
			comment = list.get(0);
		} catch (SQLException e) {
			close();
			throw e;
		}
		
		
		return comment;
	}

	private ArrayList<Comments> resultSetToComments(ResultSet resultSet) throws SQLException {

		ArrayList<Comments> list = new ArrayList<Comments>();
		
			while (resultSet.next()) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				int id = resultSet.getInt("id");
				String user = resultSet.getString("myuser");
				String email = resultSet.getString("email");
				String webpage = resultSet.getString("webpage");
				String summary = resultSet.getString("summary");
				java.sql.Date date = resultSet.getDate("datum");
				String comments = resultSet.getString("comments");
				
				Comments comment = new Comments();
				comment.setId(id);
				comment.setMyUser(user);
				comment.setEmail(email);
				comment.setWebpage(webpage);
				comment.setSummary(summary);
				comment.setDatum(date);
				comment.setComments(comments);
				
				list.add(comment);
			}
		return list;

	}
	
	
	
	
	
	@Override
	public ArrayList<Comments> select(String strSQL) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();		
	}

	
	
	
	
}
