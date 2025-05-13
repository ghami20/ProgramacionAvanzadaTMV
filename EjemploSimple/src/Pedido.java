import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class Pedido {
	private int id;
	private String descripcion;
	private String fecha;
	private int usuarioId;
	private String estado;
	private static Connection con = Conexion.getInstance().getConnection();

	public Pedido() {
	}

	public Pedido(int id, String descripcion, String fecha, int usuarioId, String estado) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.usuarioId = usuarioId;
		this.estado = estado;
	}

	// Getters y setters

	public int getId() {
		return id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}



	@Override
	public String toString() {
		return "Pedido [id=" + id + ", descripcion=" + descripcion + ", fecha=" + fecha + ", usuarioId=" + usuarioId
				+ ", estado=" + estado + "]";
	}

	public static void agregarPedido(Pedido pedido) {
		try {
			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO pedido (descripcion, fecha, usuario_id, estado) VALUES (?, ?, ?,?)");
			stmt.setString(1, pedido.getDescripcion());
			stmt.setString(2, pedido.getFecha());
			stmt.setInt(3, pedido.getUsuarioId());
			stmt.setString(4, "En proceso");

			stmt.executeUpdate();
			System.out.println("Pedido agregado correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LinkedList<Pedido> obtenerPedidosPorUsuario(int usuarioId) {
		LinkedList<Pedido> pedidos = new LinkedList<>();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM pedido WHERE usuario_id = ?");
			stmt.setInt(1, usuarioId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String descripcion = rs.getString("descripcion");
				String fecha = rs.getString("fecha");
				String estado = rs.getString("estado");

				pedidos.add(new Pedido(id, descripcion, fecha, usuarioId,estado));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pedidos;
	}

	public static Pedido obtenerPedidosPorID(int id) {
		Pedido pedido = new Pedido();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM pedido WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int id_usuario = rs.getInt("usuario_id");
				String descripcion = rs.getString("descripcion");
				String fecha = rs.getString("fecha");
				String estado = rs.getString("estado");

				pedido = new Pedido(id, descripcion, fecha, id_usuario,estado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pedido;
	}
	
	public static void editaEstadoDePedido(int id) {
		try {
			PreparedStatement stmt = con
					.prepareStatement("UPDATE pedido SET estado=? WHERE  id=?");
			stmt.setString(1, "Pagado");
			stmt.setInt(2, id);
			

			stmt.executeUpdate();
			System.out.println("Pedido agregado correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
