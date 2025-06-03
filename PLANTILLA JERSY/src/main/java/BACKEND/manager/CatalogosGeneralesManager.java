package BACKEND.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import BACKEND.utils.ConnectionsPool;
import oracle.jdbc.OracleTypes;

public class CatalogosGeneralesManager {

    public List<Map<String, Object>> GetCategoriasProductos() throws Exception {
        List<Map<String, Object>> salida = new ArrayList<>();

        ConnectionsPool c = new ConnectionsPool();
        Connection conn = c.conectar();

        CallableStatement call = conn.prepareCall("{call PKG_CATALOGOS.PROC_GET_CATEGORIAS_PRODUCTOS(?)}");
        call.registerOutParameter(1, OracleTypes.CURSOR);
        call.execute();

        ResultSet rset = (ResultSet) call.getObject(1);
        ResultSetMetaData meta = rset.getMetaData();

        while (rset.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String key = meta.getColumnName(i);
                String value = Objects.toString(rset.getString(key), "");
                map.put(key, value);
            }
            salida.add(map);
        }

        rset.close();
        call.close();
        conn.close();

        return salida;
    }

    public List<Map<String, Object>> GetProductosCategoria(String id_categoria) throws Exception {
        List<Map<String, Object>> salida = new ArrayList<>();

        ConnectionsPool c = new ConnectionsPool();
        Connection conn = c.conectar();

        CallableStatement call = conn.prepareCall("{call PKG_CATALOGOS.PROC_GET_ARTICULOS(?, ?)}");
        call.setString(1, id_categoria);
        call.registerOutParameter(2, OracleTypes.CURSOR);
        call.execute();

        ResultSet rset = (ResultSet) call.getObject(2);
        ResultSetMetaData meta = rset.getMetaData();

        while (rset.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String key = meta.getColumnName(i);
                String value = Objects.toString(rset.getString(key), "");
                map.put(key, value);
            }
            salida.add(map);
        }

        rset.close();
        call.close();
        conn.close();

        return salida;
    }


    // Método para agregar una nueva categoría
    public boolean AgregarCategoria(String nombre) throws Exception {
        ConnectionsPool c = new ConnectionsPool();
        Connection conn = c.conectar();

        String sql = "INSERT INTO CATEGORIAS_PRODUCTOS (NOMBRE) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);

        int filas = ps.executeUpdate();

        ps.close();
        conn.close();

        return filas > 0;
    }

    // Método para editar una categoría
    public boolean EditarCategoria(int id_categoria, String nombre) throws Exception {
        ConnectionsPool c = new ConnectionsPool();
        Connection conn = c.conectar();

        String sql = "UPDATE CATEGORIAS_PRODUCTOS SET NOMBRE = ? WHERE ID_CATEGORIA = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setInt(2, id_categoria);

        int filas = ps.executeUpdate();

        ps.close();
        conn.close();

        return filas > 0;
    }

    // Método para eliminar una categoría
    public boolean EliminarCategoria(int id_categoria) throws Exception {
        ConnectionsPool c = new ConnectionsPool();
        Connection conn = c.conectar();

        String sql = "DELETE FROM CATEGORIAS_PRODUCTOS WHERE ID_CATEGORIA = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id_categoria);

        int filas = ps.executeUpdate();

        ps.close();
        conn.close();

        return filas > 0;
    }
}
