import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class Pedido {
    private int id;
    private String descripcion;
    private String fecha;
    private int usuarioId;

    private static Connection con = Conexion.getInstance().getConnection();

    public Pedido() {}

    public Pedido(int id, String descripcion, String fecha, int usuarioId) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", descripcion=" + descripcion + ", fecha=" + fecha + ", usuarioId=" + usuarioId + "]";
    }

    public static void agregarPedido(Pedido pedido) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO pedido (descripcion, fecha, usuario_id) VALUES (?, ?, ?)"
            );
            stmt.setString(1, pedido.getDescripcion());
            stmt.setString(2, pedido.getFecha());
            stmt.setInt(3, pedido.getUsuarioId());

            stmt.executeUpdate();
            System.out.println("Pedido agregado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Pedido> obtenerPedidosPorUsuario(int usuarioId) {
        LinkedList<Pedido> pedidos = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM pedido WHERE usuario_id = ?"
            );
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                String fecha = rs.getString("fecha");

                pedidos.add(new Pedido(id, descripcion, fecha, usuarioId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }
    public static Pedido obtenerPedidosPorID(int id) {
       Pedido pedido =new Pedido();
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM pedido WHERE id = ?"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                int id_usuario = rs.getInt("usuario_id");
                String descripcion = rs.getString("descripcion");
                String fecha = rs.getString("fecha");

                pedido = new Pedido(id, descripcion, fecha, id_usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedido;
    }
}
