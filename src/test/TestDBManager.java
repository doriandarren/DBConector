package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;

import dao.DBManager;
import model.Table;

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
		protected HashMap mapObjectToDb(Table object) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Table mapDbToObject(ResultSet resultSet) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

			
	}
	
}
