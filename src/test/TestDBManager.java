package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;

import dao.DBManager;

public class TestDBManager {

	
	//@Test
	public void testConnection() {
		DBManager dbManager = new MockManager();
		boolean result = false;

		try {
			dbManager.connect("root", "1234");
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			dbManager.close();
		}

		Assert.assertEquals(true, result);

	}
	
	
	
	//@Test
	public void testDelete() {
		DBManager dbManager = new MockManager();

		try {
			dbManager.connect("root", "1234");
			dbManager.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbManager.close();
		}

	}
		
	
	//SOLO DE PRUEBA
	public static class MockManager extends DBManager{

		public MockManager() {
			super("localhost", "dbtest", "comments");
		}

		@Override
		public Object insert(Object object) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(Object object) {
			// TODO Auto-generated method stub
		}

		@Override
		public Object select(int id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayList select(String column, String operator, String value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Object mapDbToObject(ResultSet resultSet2) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}		
	}
	
}
