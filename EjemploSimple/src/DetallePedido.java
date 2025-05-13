import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class DetallePedido {
	private int id;
	private int pedidoId;
	private String descripcion;
	private int cantidad;
	private int precio;
	private static Connection con = Conexion.getInstance().getConnection();

	public DetallePedido() {
	}

	public DetallePedido(int id, int pedidoId, String descripcion, int cantidad, int precio) {
		super();
		this.id = id;
		this.pedidoId = pedidoId;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	// Getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(int pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "DetallePedido [id=" + id + ", pedidoId=" + pedidoId + ", descripcion=" + descripcion + ", cantidad="
				+ cantidad + "]";
	}

	public static void agregarDetalle(DetallePedido detalle) {
		try {
			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO detalle_pedido (pedido_id, descripcion, cantidad,precio) VALUES (?, ?, ?,?)");
			stmt.setInt(1, detalle.getPedidoId());
			stmt.setString(2, detalle.getDescripcion());
			stmt.setInt(3, detalle.getCantidad());
			stmt.setInt(4, detalle.getPrecio());

			stmt.executeUpdate();
			System.out.println("Detalle agregado correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LinkedList<DetallePedido> obtenerDetallesPorPedido(int pedidoId) {
		LinkedList<DetallePedido> detalles = new LinkedList<>();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM detalle_pedido WHERE pedido_id = ?");
			stmt.setInt(1, pedidoId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String descripcion = rs.getString("descripcion");
				int cantidad = rs.getInt("cantidad");
				int precio = rs.getInt("precio");

				detalles.add(new DetallePedido(id, pedidoId, descripcion, cantidad,precio));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detalles;
	}
}
