import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        String[] acciones = { "Login", "Registrar", "Salir" };
        int menu = 0;

        do {
            menu = JOptionPane.showOptionDialog(null, "Bienvenido", null, 0, 0, null, acciones, acciones[0]);

            switch (menu) {
                case 0: // Login
                    String nombre = "";
                    while (nombre.isEmpty()) {
                        nombre = JOptionPane.showInputDialog("Ingrese nombre");
                        if (nombre.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Incorrecto");
                        }
                    }

                    String contrasenia = "";
                    while (contrasenia.isEmpty()) {
                        contrasenia = JOptionPane.showInputDialog("Ingrese contraseña");
                        if (contrasenia.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Incorrecto");
                        }
                    }

                    Usuario usuario = Usuario.login(nombre, contrasenia);
                    if (usuario != null) {
                        JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());

                        String[] accionesUsuario = { "Agregar pedido", "Ver mis pedidos", "Salir" };
                        int opcion;
                        do {
                            opcion = JOptionPane.showOptionDialog(null, "¿Qué desea hacer?", null, 0, 0, null, accionesUsuario, accionesUsuario[0]);
                            switch (opcion) {
                                case 0: 
                                    String descripcionPedido = JOptionPane.showInputDialog("Descripción del pedido:");
                                    String fecha = JOptionPane.showInputDialog("Fecha (YYYY-MM-DD):");
                                    Pedido nuevoPedido = new Pedido(0, descripcionPedido, fecha, usuario.getId(),"En proceso");
                                    Pedido.agregarPedido(nuevoPedido);

                                    LinkedList<Pedido> pedidosActuales = Pedido.obtenerPedidosPorUsuario(usuario.getId());
                                    Pedido pedidoReciente = pedidosActuales.getLast();

                             
                                    int seguirAgregando = JOptionPane.showConfirmDialog(null, "¿Agregar detalle al pedido?");
                                    while (seguirAgregando == JOptionPane.YES_OPTION) {
                                        String descDetalle = JOptionPane.showInputDialog("Detalle:");
                                        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));
                                        int precio = Integer.parseInt(JOptionPane.showInputDialog("Precio:"));

                                        DetallePedido detalle = new DetallePedido(0, pedidoReciente.getId(), descDetalle, cantidad, precio);
                                        DetallePedido.agregarDetalle(detalle);

                                        seguirAgregando = JOptionPane.showConfirmDialog(null, "¿Agregar otro detalle?");
                                    }
                                    break;

                                case 1: 
                                	
                                    String[] opciones = { "Ver todos los pedidos", "Ver pedido por id","Filtrar pedidos", "Salir" };
                                    
                                    int elegido = JOptionPane.showOptionDialog(null,
                                    		"Elija una opción", "", 0, 0, null, opciones, opciones[0]);
                                    LinkedList<Pedido> pedidos = Pedido.obtenerPedidosPorUsuario(usuario.getId());
                                    switch (elegido) {
                                    //todos los pedidos
									case 0:
	                                	String resultado = "";
	                                	for (Pedido p : pedidos) {
	                                	    resultado += "Pedido: " + p.getDescripcion() + " (" + p.getFecha() + ")" + " estado " + p.getEstado() +"\n" ;
	                                	    
	                                	    LinkedList<DetallePedido> detalles = DetallePedido.obtenerDetallesPorPedido(p.getId());
	                                	    for (DetallePedido d : detalles) {
	                                	        resultado += "   - " + d.getDescripcion() + " x" + d.getCantidad() + " Precio unidad" + d.getPrecio()  + " valor " + d.getCantidad() * d.getPrecio()+ "\n";
	                                	    }
	                                	}
	                                	JOptionPane.showMessageDialog(null, resultado.isEmpty() ? "No hay pedidos" : resultado);
										break;
										//pedido por id
									case 1:
										String []pedidostring = new String[pedidos.size()];
										
										for (int i = 0; i < pedidostring.length; i++) {
											pedidostring[i] = pedidos.get(i).getDescripcion() + "/" +  pedidos.get(i).getId();		
										}
										String seleccionado = (String)JOptionPane.showInputDialog(null,
												"Seleccione uno", "", 0,null, pedidostring, pedidostring[0]);
										String id  = seleccionado.split("/")[1];
										//String descr  = seleccionado.split("/")[0];
										//JOptionPane.showMessageDialog(null, "id seleccionado" + id);
										//JOptionPane.showMessageDialog(null, "id seleccionado" + descr);
										Pedido encontrado=	Pedido.obtenerPedidosPorID(Integer.parseInt(id));
										String resulta2="";
										int total = 0;
										 LinkedList<DetallePedido> detalles = DetallePedido.obtenerDetallesPorPedido(encontrado.getId());
	                                	    for (DetallePedido d : detalles) {
	                                	    	resulta2 += "   - " + d.getDescripcion() + " x " + d.getCantidad() + " Precio unidad" + d.getPrecio()  + " valor " + d.getCantidad() * d.getPrecio()+ "\n";
	                                	    	total = total + d.getCantidad() *d.getPrecio();
	                                	    }
	                                	    JOptionPane.showMessageDialog(null, encontrado + "\n" + resulta2 + " precio total " + total );
	                                	    Pedido.editaEstadoDePedido(Integer.parseInt(id));
										break;
									case 2:
										String[] filtros = { 
										"Pagado","En proceso","Enviado"
										};
										int filtro = JOptionPane.showOptionDialog(null,
												"Elija que pedidos quiere ver", "", 0,
												0, null, filtros, filtros[0]);
										String filtrados = "";
	                                	for (Pedido p : pedidos) {
	                                		if (filtros[filtro].equals(p.getEstado())) {
												
				
		                                		filtrados += "Pedido: " + p.getDescripcion() + " (" + p.getFecha() + ")" + " estado " + p.getEstado() +"\n" ;
		                                	    
		                                	    LinkedList<DetallePedido> detalles2 = DetallePedido.obtenerDetallesPorPedido(p.getId());
		                                	    for (DetallePedido d : detalles2) {
		                                	    	filtrados += "   - " + d.getDescripcion() + " x" + d.getCantidad() + " Precio unidad" + d.getPrecio()  + " valor " + d.getCantidad() * d.getPrecio()+ "\n";
		                                	    }
	                                		}
	                                	}
	                                	JOptionPane.showMessageDialog(null, filtrados.isEmpty() ? "No hay pedidos" : filtrados);
										break;
									default:
										break;
									}
                                	
                              

                                    break;
                            }
                        } while (opcion != 2);

                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                    break;

                case 1: // Registrar
                    String nuevoNombre = JOptionPane.showInputDialog("Ingrese nombre");
                    String nuevoEmail = JOptionPane.showInputDialog("Ingrese email");
                    String nuevoTipo = JOptionPane.showInputDialog("Tipo (admin, cliente, etc)");
                    String nuevaPass = JOptionPane.showInputDialog("Ingrese contraseña");

                    Usuario nuevoUsuario = new Usuario(0, nuevoNombre, nuevoEmail, nuevoTipo, nuevaPass);
                    Usuario.RegistrarUsuario(nuevoUsuario);
                    break;
            }
        } while (menu != 2);
    }
}
