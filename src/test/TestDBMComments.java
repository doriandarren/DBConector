package test;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Assert;

import org.junit.Test;


import dao.DBMComments;
import model.Comments;

/**   OPCINAL 
 *Ejecutar en orden los TEST esto es opcional 
 * y las firmar de los metodos se renombrar por orden alfabeticamente 
 * ej.  atestInsert() ... btestUpdate() ... ctestGet()
 * 
 * Esto se debe colocar en la CLASE "public class TestDBMComments {"
 *  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
 *  
 *  y se importan:
 *  
 *  import org.junit.FixMethodOrder;
 *  import org.junit.runners.MethodSorters;
 */

public class TestDBMComments {

	
	@Test 
	public void testInsert() {
		boolean result = true;
		DBMComments dbManager = new DBMComments("localhost", "dbtest","comments");
		
		Comments coments1 = getMockDBMComments();
				
		try {
			dbManager.connect("root", "1234");
			dbManager.insert(coments1);
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		} finally{
			dbManager.close();
		}
		
		Assert.assertEquals(true, result);
		Assert.assertEquals(true,coments1.getId()!=-1);

	}
	
	
	
	
	@Test
	public void testUpdate() {
		boolean result = true;
		DBMComments dbManager =new DBMComments("localhost", "dbtest","comments");				
		Comments coments1 = getMockDBMComments();
		Comments comentsUpdate = getMockDBMComments();
		
		try {
			dbManager.connect("root", "1234");
			
			dbManager.deleteAll();
			dbManager.insert(coments1);
			
			coments1.setMyUser("Don Updata");
			coments1.setComments("Me han actualizado...");
			coments1.setDatum(new Date(124563));
			
			dbManager.update(coments1);
			
			comentsUpdate = dbManager.select(coments1.getId());
			
			
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			
		}finally {
			dbManager.close();
		} 
		
				
		
		Assert.assertEquals("Don Updata", comentsUpdate.getMyUser());
		Assert.assertEquals("Me han actualizado...", comentsUpdate.getComments());		
		Assert.assertEquals(new Date(1234567).toString(), comentsUpdate.getDatum().toString());
		Assert.assertEquals(true,comentsUpdate.getId()!=-1);
		
	}


	@Test
	public void testGet(){
		boolean result = true;
		DBMComments dbManager =   new DBMComments("localhost", "dbTest", "comments"); 		
		Comments comments1 =getMockDBMComments(); 
		Comments results = null; 
		try {
			dbManager.connect("root","1234"); 
			
			dbManager.insert(comments1);
			
			results = dbManager.select(comments1.getId()); 
			
		} catch (Exception e) {
			result = false; 
			e.printStackTrace();
		}finally{
			dbManager.close(); 
		}
		
		Assert.assertEquals(true,result);
		Assert.assertEquals(comments1.getMyUser(),results.getMyUser()); 
		Assert.assertEquals(comments1.getComments(),results.getComments()); 
	}
	
	
	
	@Test
	public void testSelect() {
		boolean result = true;
		DBMComments dbManager =new DBMComments("localhost", "dbtest","comments");
				
		Comments coments1 = getMockDBMComments("user1","user1@poo.com");
		Comments coments2 = getMockDBMComments("user2","user2@poo.com");
		Comments coments3 = getMockDBMComments("user3","user3@poo.com");
		Comments coments4 = getMockDBMComments("user1","user4@poo.com");
		Comments coments5 = getMockDBMComments("xuser4","user5@poo.com");
		
		ArrayList<Comments> results1 = null;
		ArrayList<Comments> results2 = null;
		ArrayList<Comments> results3 = null;
		try {
			
			dbManager.connect("root", "1234");
	
			dbManager.deleteAll();
			dbManager.insert(coments1);
			dbManager.insert(coments2);
			dbManager.insert(coments3);
			dbManager.insert(coments4);
			dbManager.insert(coments5);
			
			
			results1 = dbManager.select("myuser", "LIKE","'user%'");
			results2 = dbManager.select("myuser", "=", "'user1'");
			results3 = dbManager.select("id", "BETWEEN", "2 AND 5");
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}finally{
			dbManager.close(); 
		}
		
		Assert.assertEquals(true, result);
		Assert.assertEquals(4, results1.size());
		Assert.assertEquals(2, results2.size());
		Assert.assertEquals(4, results3.size());
		
		//Assert.assertEquals("user1", results1.get(0).getMyUser());		
	}
	
	
	
	
	
	//@Test
	public void testDelete() {
		DBMComments dbManager =new DBMComments("localhost", "dbtest","comments");
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
		return getMockDBMComments("root", "root@root.com");		
	}
	
	private Comments getMockDBMComments(String myUser, String email) {	
		Comments coments1 = new Comments();
		coments1.setMyUser(myUser);
		coments1.setEmail(email);
		coments1.setSummary("Esto es un test");
		coments1.setWebpage("poo.cifo");
		coments1.setComments("Esto es un comentario");
		coments1.setDatum(new java.sql.Date(System.currentTimeMillis()));
		return coments1;
	}
	
	
}
