package modelo;

import java.sql.Connection;
import java.sql.DriverManager;


public class DAOConexion {

	private String servidor,usuario,contrasena,puerto,base_datos;
	private Connection conexion;

	public DAOConexion(){
		this.servidor="192.168.0.4";// localhost
		this.usuario="postgres";
		this.contrasena="postgres";//   admin
		this.puerto="5432";
		this.base_datos="practicasuperd";
	}
	//para conectar la bd
	public boolean conectar(){
		try{
			Class.forName("org.postgresql.Driver");
			conexion= DriverManager.getConnection("jdbc:postgresql://"+servidor+":"+puerto+"/"+base_datos,usuario,contrasena);
			System.out.println("Conectado a:"+conexion.getCatalog());
			return true;
		}catch(Exception e){
			return false;
		}
	}
	public boolean desconectar(){
		try{
			conexion.close();
			System.out.println("Conexion cerrada");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//Recuperar la conexionn a la bd
	public Connection getConexion(){
		return conexion;
	}

}
