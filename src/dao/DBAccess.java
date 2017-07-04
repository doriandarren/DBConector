package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DBAccess <T> {

	/**
	 * coexion a la base de datos
	 * 
	 * @param user           usuario de la bd
	 * @param password       palabra secreta de la bd
	 * @throws SQLException  si no se puede abrir la base de datos, no autorizado... etc
	 * @throws ClassNotFoundException  si el driver no esta instalado
	 */
	public void connect(String user, String password) 
			throws SQLException, ClassNotFoundException;
	
	
	/**
	 * Inserta un objeto T en la tabla
	 * @param object objeto tipo T
	 * @return
	 * @throws SQLException 
	 */
	public T insert(T object) throws SQLException;
	
	/**
	 * Actualiza un obejto de tipo T, donde T contiene las columnasde la tabla a atualizar
	 * @param object
	 * @throws SQLException 
	 */
	public void update(T object) throws SQLException;
	
	
	/**
	 * 	Recupera un objeto de tipo T segun el id, los miembros de T contienen los valores
	 * de las columnas de la tabla
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public T select(int id) throws SQLException;
	
	
	/**
	 * Ejecuta un select segun strSQL y retorna un ArrayList con todos los objetos
	 * recuperados
	 * @param strSQL
	 * @return
	 */
	public ArrayList<T> select(String strSQL);
	
	
	/**
	 * Elimina el registro segun el id
	 * @param id
	 */
	public void delete(int id);
	
	
	/**
	 * Elimina todos los objetos de la tabla, reinicia e� contador id
	 * @throws SQLException 
	 */
	public void deleteAll() throws SQLException;
	
	
	
	/**
	 * cierra una conexion
	 */
	public void close();
	
}
