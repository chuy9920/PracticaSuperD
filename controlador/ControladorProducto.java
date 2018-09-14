package controlador;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import modelo.DAOProductos;
import modelo.DAOReportes;
import net.sf.jasperreports.engine.JRException;

public class ControladorProducto implements Initializable{



	@FXML Button btnNuevo, btnEliminar, btnEditar, btnCancelar, btnGuardar,btnReportes;
	@FXML TableView<DAOProductos> tvProductos;
	@FXML TextField txtNombre, txtDescripcion, txtCostoUninario, txtPrecioMay, txtPrecioMen;
	@FXML CheckBox cbkInactivos;

	private ObservableList<DAOProductos>lista;
	private DAOProductos producto;
	private DAOReportes repor;
	public ControladorProducto() {
		this.producto= new DAOProductos();
		this.repor=new DAOReportes();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		actualizarTabla();
		bloquear();
		clear();
	}

	public void actualizarTabla(){
		tvProductos.getItems().clear();
		lista=producto.consultar("SELECT * FROM Productos WHERE estatus = 1");
		tvProductos.setItems(lista);
		tvProductos.refresh();
	//	listaBusqueda=new FilteredList<DAOProductos>(lista);
	}

	@FXML public void clickNuevo()
	{
		txtNombre.setDisable(false);
		txtCostoUninario.setDisable(false);
		txtDescripcion.setDisable(false);
		txtPrecioMay.setDisable(false);
		txtPrecioMen.setDisable(false);
		btnNuevo.setDisable(false);
		btnCancelar.setDisable(false);
		btnGuardar.setDisable(false);
	}


	public void clickTabla(){
		if(tvProductos.getSelectionModel().getSelectedItem() != null){
			producto=tvProductos.getSelectionModel().getSelectedItem();
			txtNombre.setText(producto.getNombre());
			//txtClaveProducto.setText(producto.getClave_producto());
			txtDescripcion.setText(producto.getDescripcion());
			txtPrecioMay.setText(String.valueOf(producto.getPrecio_mayoreo()));
			txtPrecioMen.setText(String.valueOf(producto.getPrecio_menudeo()));
			txtCostoUninario.setText(String.valueOf(producto.getCosto_unitario()));

			btnEditar.setDisable(false);
			btnEliminar.setDisable(false);
			btnCancelar.setDisable(false);
			activar();
			btnNuevo.setDisable(true);
		}
		else{
			JOptionPane.showMessageDialog(null, "Seleccione un usuario de la tabla");
		}


	}

	@FXML public void clickActualizar(){
		actualizarTabla();
	}
	@FXML public void clickGuardar()
	{
		try
		{
			if(txtNombre.getText().trim().isEmpty() || txtDescripcion.getText().isEmpty())
			{

				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

			}else
			{

				producto.setNombre(txtNombre.getText());
				producto.setCosto_unitario(Double.parseDouble(txtCostoUninario.getText()));
				producto.setPrecio_menudeo(Double.parseDouble(txtPrecioMen.getText()));
				producto.setPrecio_mayoreo(Double.parseDouble(txtPrecioMay.getText()));
				producto.setDescripcion(txtDescripcion.getText());

				if(producto.agregar())
				{
					JOptionPane.showMessageDialog(null, "El usurio se agrego exitosamente");
					actualizarTabla();
					clear();
					bloquear();
				}else
				{
					JOptionPane.showMessageDialog(null, "Ha! ocurrido un error, no se pudo agregar el usuario :(");
				}
			}

		}catch(Exception e)
		{e.getStackTrace();
		}
	}


	@FXML public void clickEditar(){
		if(txtNombre.getText().trim().isEmpty() || txtDescripcion.getText().trim().isEmpty() || txtPrecioMay.getText().trim().isEmpty()||txtPrecioMen.getText().trim().isEmpty() /*|| !validar.ValidaNumerosDecimal(txtPrecioMays.getText())||!validar.ValidaNumerosDecimal(txtPrecioMen.getText())*/){
		/*	Alert alerta = new Alert(AlertType.ERROR);
			alerta.setHeaderText("¡Error!");
			alerta.setHeaderText("Campos vacíos");
			alerta.setContentText("Favor  de revisar la información");
			alerta.showAndWait();*/
			JOptionPane.showMessageDialog(null, "Campos vacios");
		}
		else{
			int a=JOptionPane.showConfirmDialog(null, "¿Seguro que desea editar?");
			System.out.println(a);
			if(a==0){
				producto.setNombre(txtNombre.getText());
				//producto.setClave_producto(txtClaveProducto.getText());
				producto.setDescripcion(txtDescripcion.getText());
				producto.setPrecio_mayoreo(Double.parseDouble(txtPrecioMay.getText()));
				producto.setPrecio_menudeo(Double.parseDouble(txtPrecioMen.getText()));
				producto.setCosto_unitario(Double.parseDouble(txtCostoUninario.getText()));
				if(producto.editar()){
					Alert alerta=new Alert(AlertType.INFORMATION);
					alerta.setTitle("Información");
					alerta.setHeaderText(null);
					alerta.setContentText("Información editada con éxito");
					alerta.showAndWait();
					actualizarTabla();
					clear();
					bloquear();
				}

			}
			else if(a==2){
				bloquear();
				clear();
			}
			else{
				JOptionPane.showMessageDialog(null, "¡Error! \nOcurrió un error");
			}
		}
		clear();
		bloquear();
		btnNuevo.setDisable(false);
	}


	@FXML public void clickCancelar(){
		bloquear();
		clear();
		btnNuevo.setDisable(false);
	}

	@FXML public void clickEliminar(){
		if(producto.getId()>0){
			producto.eliminar();
			actualizarTabla();
		}
		else{
			JOptionPane.showMessageDialog(null, "Error");
		}
		clear();
		bloquear();
	}

	public void activar(){
		txtNombre.setDisable(false);
		txtDescripcion.setDisable(false);
		//txtClaveProducto.setDisable(false);
		txtPrecioMay.setDisable(false);
		txtPrecioMen.setDisable(false);
		txtCostoUninario.setDisable(false);

	}

	public void clear(){
		txtNombre.clear();
		txtDescripcion.clear();
		//txtClaveProducto.clear();
		txtPrecioMay.clear();
		txtPrecioMen.clear();
		txtCostoUninario.clear();
	}

	public void bloquear(){
		txtNombre.setDisable(true);
		txtDescripcion.setDisable(true);

		txtPrecioMay.setDisable(true);
		txtPrecioMen.setDisable(true);
		txtCostoUninario.setDisable(true);



		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
	}

	@FXML public void clickReportes() throws JRException, IOException{
		repor.cargarReporteUsuarios();
	}
}