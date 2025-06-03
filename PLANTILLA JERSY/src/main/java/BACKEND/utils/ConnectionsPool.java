package BACKEND.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionsPool {
    Connection conn;

    public Connection conectar() throws SQLException {
        // Configuración directa (si no usas Config.java temporalmente)
        String HOST = "DESKTOP-7OS5AP9";
        String PORT = "1521";
        String SERVICE = "orcl"; // Confirmado desde lsnrctl
        String USR = "SYSTEM";
        String PASS = "admin";

        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

        // URL JDBC recomendada
        String jdbcUrl = "jdbc:oracle:thin:@//" + HOST + ":" + PORT + "/" + SERVICE;

        this.conn = DriverManager.getConnection(jdbcUrl, USR, PASS);
        return conn;
    }

    public static void main(String[] args) {
        ConnectionsPool pool = new ConnectionsPool();
        try {
            Connection conn = pool.conectar();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexión exitosa a Oracle DB!");
                conn.close();
            } else {
                System.out.println("❌ Conexión fallida.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }
}
