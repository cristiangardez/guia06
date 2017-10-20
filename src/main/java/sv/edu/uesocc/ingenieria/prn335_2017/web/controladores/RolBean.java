package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.RolFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Rol;


/**
 *
 * @author Nestor
 */
@Named(value = "rolBean")
@ViewScoped
public class RolBean implements Serializable {
   
     public RolBean() {
    }

    @EJB
    private RolFacadeLocal rfl;
    private LazyDataModel<Rol> modelo;
    private Rol registro;
    private boolean btnadd = true;
    private boolean botones = false;
    private boolean seleccions =false;

    
    @PostConstruct
    private void inicio() {

        registro = new Rol();

        try {
            this.modelo = new LazyDataModel<Rol>() {
                @Override
                public Object getRowKey(Rol object) {
                    if (object != null) {
                        return object.getIdRol();
                    }
                    return null;
                }

                @Override
                public Rol getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Rol reg : (List<Rol>) getWrappedData()) {
                                if (reg.getIdRol().compareTo(buscado) == 0) {
                                    return reg;
                                }
                            }
                        } catch (Exception e) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                        }
                    }
                    return null;
                }

                @Override
                public List<Rol> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Rol> salida = new ArrayList();
                    try {
                        if (rfl != null) {
                            this.setRowCount(rfl.count());
                            salida = rfl.findRange(first, pageSize);
                            
                        }
                    } catch (Exception e) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }

            };
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
                 
    }
    
    
    /**
     * metodo para cancelar y limpiar
     */
    
    public void cancelar() {
        this.registro = new Rol();
        this.botones=false;
        this.btnadd=true;
        
    }

    /**
     * metodo para persistir el registro 
     */
    public void guardarRegistro() {
        try {
            if (this.registro != null && this.rfl != null) {
               if (this.rfl.create(registro)) {
                    inicio();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * metodo para eliminar un regitro 
     */
    public void Eliminar() {
        try {

            if (this.registro != null && this.rfl != null) {
                if (this.rfl.remove(registro)) {
                    this.registro = new Rol();
                    inicio();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    
    /**
     * metodo que guarda el registro editado
     */
    public void Modificar() {
        try {
            if (this.registro != null && this.rfl != null) {
                if (this.rfl.edit(registro)) {
                    inicio();
                    
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * tiene la funcion de ocultar y aparecer botones
     */
    public void cambiarSeleccion() {
                this.botones = true;
                this.btnadd = false;
                  
    }
    
    /**
     * todos los GETTER y SETTER
     * @return 
     */
    public LazyDataModel<Rol> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Rol> modelo) {
        this.modelo = modelo;
    }

    public Rol getRegistro() {
        return registro;
    }

    public void setRegistro(Rol registro) {
        this.registro = registro;
    }

    public boolean isBtnadd() {
        return btnadd;
    }

    public void setBtnadd(boolean btnadd) {
        this.btnadd = btnadd;
    }

    public boolean isSeleccions() {
        return seleccions;
    }

    public void setSeleccions(boolean seleccions) {
        this.seleccions = seleccions;
    }

    
    public boolean isBotones() {
        return botones;
    }

    public void setBotones(boolean botones) {
        this.botones = botones;
    }

   
   
}
