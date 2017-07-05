package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.CommunicationException;

import model.Comments;

public abstract class DBManager<T> implements DBAccess<T>{

	private String dbName;
	private final String dbTable;
	
	private String dbUri;
	private Connection connect;
	
	
	//TODO ojo no sera miembro
	protected ResultSet resultSet;
	
	
	public DBManager(String dbHost, String dbName, String dbTable){
		this.dbTable = dbTable;
		this.dbName = dbName;
		this.dbUri = "jdbc:mysql://host/dbName?user=root&password=1234";
		dbUri = dbUri.replace("host", dbHost)
				.replace("dbName", dbName);
		
	}
	
	
	@Override
	public void connect(String user, String password) 
			throws SQLException, ClassNotFoundException {
		try {
			
			String uri = dbUri.replace("root", user)
					.replace("1234", password);
			
			//Carga el driver Mysql
			Class.forName("com.mysql.jdbc.Driver");
			
			//Conecta a la base de datos
			connect = DriverManager
					.getConnection(uri);
			
		}catch (ClassNotFoundException e1) {
			System.err.println("Verifique que el driver esta incluido en su proyecto.");
			close();
			throw e1;
		}catch (SQLException e) {
			close();
			throw e;
		}
		
	}

	
	
	@Override
	public void delete(int id) throws SQLException {
		PreparedStatement preparedStatement=null;
		try {			
			preparedStatement = connect.prepareStatement("DELETE FROM " + dbTable + " WHERE id=?");
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();			
		} catch (SQLException e) {
			throw e;
		}finally {
			try {
				preparedStatement.close();
			} catch (Exception e2) { }
		}
		
	}

	@Override
	public void deleteAll() throws SQLException {	
		PreparedStatement preparedStatement =null;
		try {			
			preparedStatement = connect.prepareStatement("TRUNCATE " + dbTable);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		}finally {
			try {
				preparedStatement.close();
			} catch (Exception e2) { }
		}
		
	}
	
	
	
	@Override
	public abstract T insert(T object) throws SQLException;

	@Override
	public abstract void update(T object) throws SQLException;

	@Override
	public T select(int id) throws SQLException{		
		String strSql = "SELECT * FROM "
							+ getDbTable() + " WHERE id=?";	
		
		PreparedStatement preparedStatement =null;
		T generic = null;
		
		try {		
			preparedStatement = getConnected().prepareStatement(strSql);						
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();		
			ArrayList<T> list = resultSetToGeneric(resultSet);
			generic = list.get(0);
		} catch (SQLException e) {
			throw e;
		}finally{
			try {
				preparedStatement.close();
			} catch (Exception e1) {}
		}		
		return generic;		
	}

	
	
	/**
	 * Recupera todos los registros con la condicion que:
	 * la columna column operador value, donde operador puede ser:
	 * = 'value'
	 * != 'value'
	 * < 'value'
	 * > 'value'
	 * <= 'value'
	 * >= 'value'
	 * BETWEEN ?
	 * LIKE 'value%' // % para indicar cualquier cosa
	 * IN  ?
	 * @param string 
	 * @throws SQLException 
	 */	
	@Override
	public ArrayList<T> select(String column, String operator, String value) throws SQLException{
		
		ckeckOperator(operator);
		
		//value = operator.toUpperCase().contains("LIKE") ? "'"+value+"'":value;
		
		String strSql = "SELECT * FROM "
						+ getDbTable() + " WHERE " + column +" " + operator+" "+value;	
		
		PreparedStatement preparedStatement =null;
		ArrayList<T> list =null;
		
		try {		
			preparedStatement = getConnected().prepareStatement(strSql);
			ResultSet resultSet = preparedStatement.executeQuery();
			list = resultSetToGeneric(resultSet);
		} catch (SQLException e) {
			throw e;
		}finally{
			try {
				preparedStatement.close();
			} catch (Exception e1) {}
		}
				
		return list;
		
	}

	
	/**
	 * Transforma el resultado de una consulta resultSet en un objeto de tipo 
	 * T
	 * @param resultSet
	 * @return lista de objetos de tipo T 
	 * @throws SQLException
	 */
	protected ArrayList<T> resultSetToGeneric(ResultSet resultSet2) throws SQLException{
		ArrayList<T> list = new ArrayList<>(); 		
		 while (resultSet.next()) {			 
			    T generic= mapDbToObject(resultSet); 
	            list.add(generic); 	        		 
		 }		 
		 return list;		
	}
	
	
	protected abstract T mapDbToObject(ResultSet resultSet2) throws SQLException;

	
	
	
	/**
	 * Verifica el operador sea valido
	 * @param operator
	 */
	private void ckeckOperator(String operator) {
		final ArrayList<String> columns = new ArrayList<String>(
				Arrays.asList("=","!=","<>",">","<",">=","<=", "LIKE", "BETWEEN", "IN"));
		if(!columns.contains(operator))
		throw new RuntimeException("Error: la columna "+ operator 
				+ " no hace parte de la tabla " + getDbTable());		
	}
	
	

	
	
		

	@Override
	public void close() {
		try {
			if(resultSet!=null){
				resultSet.close();
				resultSet=null;
			}
			if(connect!=null){
				connect.close();
				connect=null;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}			
	}


	/** getter y setter accesorios **/
	
	public String getDbName() {
		return dbName;
	}


	public String getDbTable() {
		return dbTable;
	}	
	
	
	protected Connection getConnected(){
		return connect;
	}
	
}
