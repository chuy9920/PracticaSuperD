package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class DAOReportes {
	DAOConexion con;
	JasperPrint impreso;

	public DAOReportes(){
		con = new DAOConexion();
 
	}
	public  JasperPrint cargarReporteUsuarios()throws JRException, IOException{
	try{
		if(con.conectar()){
			InputStream input = new FileInputStream(new File(getClass().getResource("../vista/Reportes/Productos.jrxml").getPath()));
			JasperDesign disenio = JRXmlLoader.load(input);
			JasperReport reporte= JasperCompileManager.compileReport(disenio);
			impreso =JasperFillManager.fillReport(reporte,null, con.getConexion());
			JasperViewer vista = new JasperViewer(impreso, false);
			vista.setVisible(true);
			input.close();

		}
	}catch(FileNotFoundException e){
		e.printStackTrace();
	}
	finally{
		con.desconectar();
	}
	return impreso;
	}
}
