package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DBManager<T> implements DBAccess<T>{

	private String dbName;
	private final String dbTable;
	
	private String dbUri;
	private Connection connect;
		
	private PreparedStatement preparedStatement;
	
	
	//TODO ojo no sera miembro
	private ResultSet resultSet;
	
	
	
	
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
						
			//statement = connect.createStatement();
			
		} catch (Exception e) {
			close();
			throw e;
		}
		
	}

	@Override
	public abstract T insert(T object) throws SQLException;

	@Override
	public abstract void update(T object) throws SQLException;

	@Override
	public abstract T select(int id) throws SQLException;

	@Override
	public abstract ArrayList<T> select(String strSQL);

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() throws SQLException {		
		try {			
			preparedStatement = connect.prepareStatement("TRUNCATE " + dbTable);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			close();
			throw e;
		}
		
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
