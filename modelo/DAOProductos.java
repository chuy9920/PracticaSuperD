package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOProductos {
	private int id;
	private String nombre, descripcion;
	private int estatus;
	private double precio_menudeo, precio_mayoreo,costo_unitario;

	private DAOConexion con;
	private PreparedStatement comando;
	private ObservableList<DAOProductos> lista;

	public DAOProductos(){
		this.id=0;
		this.nombre="";
		this.nombre="";
		this.costo_unitario=0;
		this.precio_mayoreo=0;
		this.precio_menudeo=0;
		this.estatus=0;

		this.con=new DAOConexion();
		this.lista = FXCollections.observableArrayList();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public double getPrecio_menudeo() {
		return precio_menudeo;
	}

	public void setPrecio_menudeo(double precio_menudeo) {
		this.precio_menudeo = precio_menudeo;
	}

	public double getPrecio_mayoreo() {
		return precio_mayoreo;
	}

	public void setPrecio_mayoreo(double precio_mayoreo) {
		this.precio_mayoreo = precio_mayoreo;
	}

	public double getCosto_unitario() {
		return costo_unitario;
	}

	public void setCosto_unitario(double costo_unitario) {
		this.costo_unitario = costo_unitario;
	}


	public ObservableList<DAOProductos> consultar(String consulta){
		ResultSet rs = null;
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement(consulta);
				rs = comando.executeQuery();
				while(rs.next()){
					DAOProductos oProductos = new DAOProductos();
					oProductos.setId(rs.getInt("id"));
					oProductos.setNombre(rs.getString("nombre"));
					oProductos.setCosto_unitario(rs.getDouble("costo_unitario"));
					oProductos.setDescripcion(rs.getString("descripcion"));
					oProductos.setPrecio_mayoreo(rs.getDouble("precio_mayoreo"));
					oProductos.setPrecio_menudeo(rs.getDouble("precio_menudeo"));
					//oProductos.setNombre_producto(rs.getString("nombre_producto"));
					//oProductos.setRuta_imagen(rs.getString("ruta_imagen"));
					//System.out.println(ruta);
					lista.add(oProductos);
					//System.out.println(oClientes.getaPaterno());
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

			con.desconectar();
			return lista;



	}
	public boolean agregar(){
		try{
			if(con.conectar()){
				String sql ="INSERT INTO Productos VALUES(default,?,?,?,?,?,1);";
				comando = con.getConexion().prepareStatement(sql);
				//comando.setInt(1, this.clave_producto);
				comando.setString(1, this.nombre);
				comando.setString(2, this.descripcion);
				comando.setDouble(3, this.costo_unitario);
				comando.setDouble(4, this.precio_menudeo);
				comando.setDouble(5, this.precio_mayoreo);

				comando.execute();
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}

	public boolean eliminar(){
		try{
			if(con.conectar()){
				String sql = "UPDATE Productos SET estatus = 0 WHERE id = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id);
				comando.execute();
			}
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}

	public boolean editar(){
		try{
			con.conectar();
			String sql ="UPDATE Productos SET nombre = ?,  descripcion = ?, costo_unitario = ? ,precio_mayoreo = ?, precio_menudeo=? where id= ?";
			comando = con.getConexion().prepareStatement(sql);
			comando.setInt(6, this.id);
			comando.setString(1, this.nombre);
			comando.setString(2, this.descripcion);
			comando.setDouble(3, this.costo_unitario);
			comando.setDouble(4, this.precio_menudeo);
			comando.setDouble(5, this.precio_mayoreo);

			comando.execute();
			return true;

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}

	public boolean reactivar(){
		try{
			if(con.conectar()){
				String sql = "UPDATE Productos SET estatus = 1 WHERE id = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id);
				comando.execute();
			}
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}

}
