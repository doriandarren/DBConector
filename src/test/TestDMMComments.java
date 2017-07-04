package test;

import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import dao.DBMComents;
import model.Comments;

public class TestDMMComments {

	
	//@Test
	public void testInsert() {
		boolean result = true;
		DBMComents dbManager = new DBMComents("localhost", "dbtest","comments");
		
		Comments coments1 = getMockDBMComments();
				
		try {
			dbManager.connect("root", "1234");
			dbManager.insert(coments1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			dbManager.close();
		}
		
		Assert.assertEquals(true, result);
		Assert.assertEquals(true,coments1.getId()!=-1);

	}
	
	
	
	
	//@Test
	public void testUpdate() {
		boolean result = true;
		DBMComents dbManager =new DBMComents("localhost", "dbtest","comments");				
		Comments coments1 = getMockDBMComments();
		Comments comentsUpdate = getMockDBMComments();
		
		try {
			dbManager.connect("root", "1234");
			dbManager.insert(coments1);
			
			coments1.setMyUser("Don Updata");
			coments1.setComments("Me han actualizado...");
			coments1.setDatum(new Date(1234567));
			dbManager.update(coments1);
			
			comentsUpdate = dbManager.select(coments1.getId());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			dbManager.close();
		} 
				
		/*
		Assert.assertEquals("Don Updata", coments1.getMyUser());
		Assert.assertEquals("Me han actualizado...", coments1.getComments());		
		Assert.assertEquals(new Date(1234567).toString(), coments1.getDatum().toString());
		Assert.assertEquals(true,coments1.getId()!=-1);*/
		
	}


	
	
	@Test
	public void testGet() {
		boolean result = true;
		DBMComents dbManager =new DBMComents("localhost", "dbtest","comments");
				
		Comments coments1 = getMockDBMComments();
		
		
		try {
			
	
	
		} catch (Exception e) {
			e.printStackTrace();
			dbManager.close();
		} 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	// @Test
	public void testDelete() {
		DBMComents dbManager =new DBMComents("localhost", "dbtest","comments");

		try {
			dbManager.connect("root", "1234");
			dbManager.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbManager.close();
		}

	}
	


	private Comments getMockDBMComments() {	
		Comments coments1 = new Comments();
		coments1.setMyUser("root");
		coments1.setEmail("root@root.com");
		coments1.setSummary("Esto es un test");
		coments1.setWebpage("poo.cifo");
		coments1.setComments("Esto es un comentario");
		coments1.setDatum(new java.sql.Date(System.currentTimeMillis()));
		return coments1;
	}
	
	
	
}
