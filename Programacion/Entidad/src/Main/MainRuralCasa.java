package Main;



import java.sql.Connection;
import java.sql.SQLException;
import DAO.*;
import Entidad.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * Main RuralCasa
 * Programa principal
 */

public class MainRuralCasa {
	
	/**
	 * Constructor
	 */
	public MainRuralCasa() {}
private static Scanner in = new Scanner(System.in);
	/**
	 * Main
	 * @param args Main
	 * @throws Exception SQLException
	 */
    public static void main(String[] args) throws Exception {
    	int menu=0,num=0, menu2=0;
      ConexionBD conexion_BD = new ConexionBD();
      Connection _con = null;
      CasaDAO casaDAO_ = new CasaDAO();
      ClienteDAO cDAO = new ClienteDAO();
      ReservaDAO rDAO = new ReservaDAO();
      LiquidacionDAO lDAO = new LiquidacionDAO();
      PropietarioDAO pDAO = new PropietarioDAO();
      ActividadDAO actDAO = new ActividadDAO();
      ServicioDAO servDAO = new ServicioDAO();
      TipoCasaDAO tipoDAO = new TipoCasaDAO();
      ARDAO arDAO = new ARDAO();
      SRDAO srDAO = new SRDAO();
      Date fecha = new Date(Calendar.getInstance().getTime().getTime());
      String dni;
        try {
	        _con = conexion_BD.AbrirConexion();
	    
		} catch(Exception e) {
			e.printStackTrace();
		}
        
        
        do {
        	getMenu();
        	menu2=in.nextInt();
   
        	switch (menu2) {
        	//Gestion Cliente

        	case 1: 
        		do {
            		getMenuClientes();
            		menu=in.nextInt();                   
            		switch (menu) {
            		
            		case 1:	
            			System.out.println("Introduzca los datos del cliente: ");
            			if (cDAO.darAlta(_con, darAltaCliente())) {
            				System.out.println("Cliente anyadido");
            			}
            			break;
            		case 2:
            			System.out.println("Introduzca el dni del cliente: ");
            			System.out.println("Introduzca 0 si quiere abortar ");
            			System.out.println("Introduzca 1 si quiere mostrar todos los clientes ");
            			dni= in.next();
            			if (!cDAO.buscarCliente(_con, dni)){
                			System.out.println("Cliente no encontrado");
            			}

            			break;
            		case 3:
            			if (cDAO.actualiza(_con, darAltaCliente())) {
            				System.out.println("Cliente actualizado");
            			}
            			break;
            		case 4:
            			System.out.print("Introduzca el dni del cliente: ");
            			dni= in.next();
            			if (cDAO.elimina(_con, dni)) {
            				System.out.println("Cliente eliminado");
            			}
            			break;
            		default: break;
            		}
        		}while (menu!=0);
            	break;
                    
            //Gestion Casa
            case 2:
            	do {
                        Casa casa = new Casa();
                        ArrayList<Casa> listaCasas;
                        ArrayList<Servicio> listaServicio;
                        ArrayList<Actividad>listaActividad;
            		getMenuCasas();
            		menu=in.nextInt();
            		switch(menu) {
                            
                            case 1:
                                System.out.println("Ingrese dni de Propietario");
                                dni=in.next();
                                in.nextLine();
                                while (pDAO.existePropietario(_con, dni) == false) {            
                                    System.out.println("El propietario ingresado no existe.\nIngrese de nuevo el DNI");
                                    dni=in.next();
                                    in.nextLine();
                                }
                                casa.setPropietario(dni);                              
                                
                                System.out.println("Escoja el tipo de la casa");
                                tipoDAO.mostrarTiposDeCasa(_con);
                                int tipo=in.nextInt();
                                in.nextLine();
                                
                                while(tipoDAO.existeTipo(_con, tipo) == false){
                                    System.out.println("Tipo no encontrado, seleccione otro");
                                    tipo=in.nextInt();
                                    in.nextLine();
                                }
                                
                                casa.setTipo(tipo);
                                casa = darAltaCasa(casa);
                                
                                listaActividad = actDAO.buscarActividadByTipoDeCasa(_con, casa.getTipo());
                                int eleccionesActividad[] = new int[listaActividad.size()];
                                boolean finalizado = false;
                                int entrada = 0;
                                int[] contador = {0}; 
                                String resultado[] = {""};
                                if(listaActividad.isEmpty() != true){
                                        do{
                                            mostrarListasArrayList(listaActividad);
                                            System.out.println("Que actividades desea asignarle a la casa? (Presiona 0 en caso contrario)");
                                               entrada = in.nextInt();
                                               if(actDAO.existeActividad(_con, entrada) == true || entrada == 0){
                                                finalizado = ingresarVariosTipos(entrada, eleccionesActividad, contador, resultado);
                                                } 
                                           }while(finalizado != true);
                                        casa.setActividades(resultado[0]);
                                        } else {
                                            System.out.println("No hay actividades disponibles");
                                            casa.setActividades(String.valueOf(0));
                                }
                                listaServicio = servDAO.buscarTodosLosServicios(_con);
                                int[] eleccionesServicios = new int[listaServicio.size()];
                                finalizado = false;
                                entrada = 0;
                                contador[0] = 0; 
                                resultado[0] = "";
                                
                                if(listaServicio.isEmpty() != true){
                                    do{
                                    servDAO.mostrarServicios(_con);
                                    System.out.println("Que servicios desea asignarle a la casa? (Presiona 0 en caso contrario)");
                                       entrada = in.nextInt();
                                       if(servDAO.existeServicio(_con, entrada) == true || entrada == 0){
                                        finalizado = ingresarVariosTipos(entrada, eleccionesServicios, contador, resultado);
                                       } 
                                   }while(finalizado != true);
                                casa.setServicio(resultado[0]);
                                } else {
                                    System.out.println("No hay servicios disponibles");
                                    casa.setServicio(String.valueOf(0));
                                }
                                
                                if (casaDAO_.darAlta(_con, casa)) {
                                    System.out.println("La casa a sido correctamente registrada");
                                } else{
                                    System.out.println("No se ha podido registrar la casa");
                                }
                            break;
                            
                            case 2:
                                casaDAO_.mostrarCasas(_con);
                                System.out.println("Escoja la vivienda a modificar");
                                int seleccion = in.nextInt();                               
                                in.nextLine();
                                while(casaDAO_.existeCasa(_con, seleccion) == false){
                                    System.out.println("Casa no encontrada, intentalo de nuevo.");
                                    seleccion = in.nextInt(); 
                                    in.nextLine();
                                }
                                
                                System.out.println("Escoja el tipo de la casa");
                                tipoDAO.mostrarTiposDeCasa(_con);
                                int tipo2=in.nextInt();
                                in.nextLine();
                               
                                while(tipoDAO.existeTipo(_con, tipo2) == false){
                                    System.out.println("Tipo no encontrado, seleccione otro");
                                    tipo2=in.nextInt();
                                    in.nextLine();
                                }
                                
                                casa.setTipo(tipo2);
                                casa = darAltaCasa(casa);
                                
                                
                                
                                System.out.println("Que actividades desea asignarle a la casa? (Presiona 0 en caso contrario)");
                               listaActividad = actDAO.buscarActividadByTipoDeCasa(_con, tipo2);
                                  
                                int[] eleccionesActividadesMod = new int[listaActividad.size()];
                                finalizado = false;
                                entrada = 0;
                                int contadorActividades[] = {0}; 
                                String resultadoActividades[] = {""};
                                if(listaActividad.isEmpty() != true){
                                        do{
                                            mostrarListasArrayList(listaActividad);
                                            System.out.println("Que actividades desea asignarle a la casa? (Presiona 0 en caso contrario)");
                                               entrada = in.nextInt();
                                               if(actDAO.existeActividad(_con, entrada) == true || entrada == 0){
                                                finalizado = ingresarVariosTipos(entrada, eleccionesActividadesMod, contadorActividades, resultadoActividades);
                                                } 
                                           }while(finalizado != true);
                                        casa.setActividades(resultadoActividades[0]);
                                        } else {
                                            System.out.println("No hay actividades disponibles");
                                            casa.setActividades(String.valueOf(0));
                                }
                                listaServicio = servDAO.buscarTodosLosServicios(_con);
                                
                                System.out.println("Que servicios desea asignarle a la casa? (Presiona 0 en caso contrario)");
                                int[] eleccionesServiciosMod = new int[listaServicio.size()];
                                finalizado = false;
                                entrada = 0;
                                int contadorServicios[] = {0}; 
                                resultadoActividades[0] = "";
                                if(listaActividad.isEmpty() != true){
                                         do{
                                             servDAO.mostrarServicios(_con);
                                             System.out.println("Que servicio desea asignarle a la casa? (Presiona 0 en caso contrario)");
                                                entrada = in.nextInt();
                                                if(servDAO.existeServicio(_con, entrada) == true || entrada == 0){
                                                 finalizado = ingresarVariosTipos(entrada, eleccionesServiciosMod, contadorServicios, resultadoActividades);
                                                 } 
                                            }while(finalizado != true);
                                         casa.setServicio(resultadoActividades[0]);
                                         } else {
                                             System.out.println("No hay servicios disponibles");
                                             casa.setServicio(String.valueOf(0));
                                 }
                                
                                if (casaDAO_.modificarCasa(_con, casa, seleccion) == true) {
                                    System.out.println("La casa a sido correctamente registrada");
                                } else{
                                    System.out.println("No se ha podido registrar la casa");
                                }
                            break;
                            
                            case 3:
                                casaDAO_.mostrarCasas(_con);
                                System.out.println("Escoja la vivienda a eliminar");
                                seleccion = in.nextInt();
                                in.nextLine();
                                while(casaDAO_.existeCasa(_con, seleccion) == false){
                                    System.out.println("Casa no encontrada, intentalo de nuevo.");
                                    seleccion = in.nextInt(); 
                                    in.nextLine();
                                }
                                
                                if (casaDAO_.eliminarCasa(_con, seleccion) == true) {
                                    System.out.println("La casa a sido correctamente eliminada");
                                } else{
                                    System.out.println("No se ha podido eliminar la casa");
                                }
                            
                            case 4:
                            casaDAO_.mostrarCasas(_con);
                            break;
                            
                    
                            case 5:
                                int eleccion;
                                ArrayList<String> provincias; 
                                provincias = casaDAO_.buscarProvinciasDeCasas(_con);
                                if (provincias.isEmpty() == false) {
                                    System.out.println("Estas son las provincias disponibles");
                                    for (int i = 0; i < provincias.size(); i++) {
                                        System.out.println((i+1) + ". " + provincias.get(i));
                                    }
                                    System.out.println("Seleccione las provincias disponibles");
                                    eleccion = in.nextInt()-1;
                                    in.nextLine();
                                    System.out.println(eleccion);
                                    while (eleccion < 0 || eleccion > provincias.size()){
                                        System.out.println("Eleccion equivocada, seleccione la provincia correcta");
                                        eleccion = in.nextInt()-1;
                                        in.nextLine();
                                    }
                                 listaCasas = casaDAO_.findByProvinciaDisponible(_con, provincias.get(eleccion));
                                    if (listaCasas.isEmpty()) {
                                        System.out.println("No hay casas disponibles en esta provincia");
                                    } else {
                                        for (int i = 0; i < listaCasas.size(); i++) {
                                        System.out.println(listaCasas.get(i));
                                        }
                                    }
                                } else {
                                    System.out.println("No hay casas disponibles");
                                }
                            break;
                            
                            case 6:
                               ArrayList<Casa> listaDeCasasByTipo; 
                               tipoDAO.mostrarTiposDeCasa(_con);
                               System.out.println("Escoge un tipo de casa");
                               int escogeTipo = in.nextInt();
                               
                               while(tipoDAO.existeTipo(_con, escogeTipo) == false){
                                   System.out.println("Ese tipo de casa no existe. Ingrese la opcion de nuevo");
                                   escogeTipo = in.nextInt();
                               }
                               
                               listaDeCasasByTipo = casaDAO_.findByTipoDeCasa(_con, escogeTipo);
                               
                               if (listaDeCasasByTipo.isEmpty()) {
                                    System.out.println("No hay casas registradas de ese tipo");
                                } else {
                                   for (int i = 0; i < listaDeCasasByTipo.size(); i++) {
                                    System.out.println(listaDeCasasByTipo.get(i));
                                }
                               }
                            break;
                            
                            case 7:
                                System.out.println("Ingrese el codigo de la casa a consultar precio");
                                int codigo = in.nextInt();
                                in.nextLine();
                                
                                while (casaDAO_.existeCasa(_con, codigo) == false) {                                    
                                    System.out.println("Codigo invalido, ingrese otra vez el codigo");
                                    codigo = in.nextInt();
                                    in.nextLine();
                                }
                                
                                System.out.println("El precio por dia de esta casa es de " + casaDAO_.precioCasaPorDia(_con, codigo));
                            break;
                            
                            case 8:
                                
                                casaDAO_.mostrarCasas(_con);
                                System.out.println("Seleccione la casa a consultar las coordenadas");
                                codigo = in.nextInt();
                                in.nextLine(); 
                                
                                while (casaDAO_.existeCasa(_con, codigo) == false) {                                    
                                    System.out.println("Codigo invalido, ingrese otra vez el codigo");
                                    codigo = in.nextInt();
                                    in.nextLine();
                                }
                                
                                double[] coordenadas = new double[2];
                                
                                coordenadas = casaDAO_.obtenerCoordenadasCasa(_con, codigo);
                                System.out.println("Latitude: " + coordenadas[0]);
                                System.out.println("Longitude: " + coordenadas[1]);
                            break;
            		default:break;
            		}
            	}while (menu!=0);
        		break;
        	//Gestion Propietarios
            case 3:
            	do {
                    ArrayList<Casa> listaCasas = new ArrayList<Casa>();
                    Propietario p = new Propietario();
            		getMenuProp();
            		menu=in.nextInt();
            		switch(menu) {
            		
                            case 1:
                                System.out.println("Introduzca los datos del propietario: ");
            			p=darAltaPropietario(p);
                                
                                if (pDAO.existePropietario(_con, p.getDni()) == false) {
                                    if (pDAO.darAlta(_con, p)) {
                                        System.out.println("Propietario anyadido");
                                    }
                                } else {
                                    System.out.println("Este propietario ya esta dado de alta");
                                }
                            break;
                            
                            case 2:
                            
                                System.out.println("Ingresa dni del propietario");
                                dni= in.next();
                                if (pDAO.existePropietario(_con, dni) == true) {
                                    System.out.println("Introduzca datos nuevos");
                                    p = darAltaPropietario(p);
                                    
                                    while(pDAO.existePropietario(_con, p.getDni()) == true){
                                        System.out.println("Este propietario ya existe. Por favor, ingrese datos nuevos.");
                                        p = darAltaPropietario(p);
                                    }
                                    
                                    if (pDAO.actualiza(_con, p, dni)) {
                                        System.out.println("Propietario actualizado");
                                    }
                                } else {
                                    System.out.println("Este propietario no existe");
                                }
                                
                            break;
                            
                            case 3:
                                
                                System.out.println("Ingresa dni del propietario");
                                dni= in.next();
                                if (pDAO.existePropietario(_con, dni) == true) {
                                    casaDAO_.eliminarCasaByPropietario(_con, dni);
                                    if (pDAO.elimina(_con, dni) == true) {
                                        System.out.println("Propietario eliminado");
                                    }
                                } else {
                                    System.out.println("Este propietario no existe");
                                }
                            break; 
                            
                            case 4:
                                pDAO.mostrarPropietario(_con);
                            break;
                            case 5: // Mostrar propietario y las propiedades que este tenga
                                System.out.println("Ingresa dni de propietario");
                                dni= in.next();
                                if(pDAO.existePropietario(_con, dni) == true){
                                    p = pDAO.buscarPropietario(_con, dni);
                                    System.out.println("Propietario encontrado:");
                                    System.out.println(p);
                                    listaCasas = casaDAO_.findByDniPropietario(_con, dni);
                                    if (listaCasas.isEmpty()) {
                                        System.out.println("No tiene propiedades registradas");
                                    } else {
                                        for (int i = 0; i < listaCasas.size(); i++) {
                                        System.out.println(listaCasas.get(i));
                                    }
                                  }
                                } else {
                                    System.out.println("El propietario ingresado no existe");
                                }
                            break; 
            		default:break;
            		}
            	}while (menu!=0);
            	break;
                
            //Gestion Reservas
            case 4:
            	do {
            		getMenuReservas();
            		menu=in.nextInt();
            		
            		switch(menu) {
            		case 1:
            			if (rDAO.registrarReserva(_con, nuevaReserva(_con))) {
            				System.out.println("Reserva registrada");
            			} else {
            				System.out.println("Ha ocurrido un error");
            			};
            			break;
            		case 2:
            	    	System.out.println("Introduzca el numero de reserva");
            	    	num=in.nextInt();
            	    	if (rDAO.buscarReserva(_con, num).getNumReserva()!=0) {
                	    	System.out.println(rDAO.buscarReserva(_con, num).toString());
                	    	mostrarAyS(_con,num);
            	    	}

            			break;
            		case 3:
            	    	System.out.println("Introduzca el numero de reserva");
            	    	num=in.nextInt();
            			if (rDAO.modificarReserva(_con, rDAO.buscarReserva(_con, num))) {
            				System.out.println("Reserva modificada");
            			} else {
            				System.out.println("Ha habido un fallo en la modificacion de la reserva");
            			}
            			break;
            		case 4:
            	    	System.out.println("Introduzca el numero de reserva");
            	    	num=in.nextInt();
            			rDAO.anularReserva(_con, fecha, rDAO.buscarReserva(_con, num));
            			break;
            		case 5:
            			double pago;
            			System.out.println("Introduzca el numero de reserva");
            	    	num=in.nextInt();
            	    	Reserva r1 = rDAO.buscarReserva(_con, num);
            	    	if (r1.getDiasReserva()!=0) {
                	    	if (r1.getEstadoReserva().equalsIgnoreCase("PAGADO")||r1.getEstadoReserva().equalsIgnoreCase("FINALIZADO")) {
                	    		System.out.println("La reserva con id: "+r1.getNumReserva()+" esta pagada o finalizada");
                	    	} else {
                	    		System.out.println("El importe es "+(r1.getImporteTotal()-r1.getEntradaReserva()));
                    	    	do {
                    	    		System.out.println("Introduzca el pago");
                    	    		pago = in.nextDouble();
                    	    	}while (!rDAO.pagarReserva(_con, r1, pago));	
                	    	}
            	    	}
            	    	
            	    	break;
            		case 6:
            			System.out.println("Introduzca el numero de reserva");
            	    	num=in.nextInt();
            	    	boolean pagado= pagadoCompleto(_con,num);
            	    	if (pagado) {
            	    		rDAO.finalizarReserva(_con, num,pagado); 
            	    		System.out.println("Reserva finalizada");
            	    	} else {
            	    		System.out.println("Debe terminar el pago para finalizar la reserva");
            	    	}
            	    	break;
            		case 7:
            			System.out.println("Buscar reservas segun estado: Abierta, Anulada, Pagada, Finalizada o Todas");
            			String estado = in.next().toUpperCase();
            			List<Reserva> listaReservas = new ArrayList<Reserva>();
            			if (estado.equals("TODAS")) {
            				listaReservas= rDAO.imprimirReserva(_con, null);
            			} else {
            				listaReservas= rDAO.imprimirReserva(_con, estado);
            			}
            			for(Reserva lista1: listaReservas) {
            				System.out.println(lista1.toString());
            			}
            			break;
            		case 8:
            			System.out.println("Introduzca el numero de reserva");
            	    	num=in.nextInt();
            	    	System.out.println("El importe a devolver es: "+rDAO.anularReserva(_con, fecha, rDAO.buscarReserva(_con, num)));
            	    	break;
            		
            		default:break;
            		}
            	}while (menu!=0);

            break;
            case 5:
            	//Gestion Facturas
            	do {
                    getMenuLiquidacion();
                    menu=in.nextInt();
            		switch (menu) {
            		case 1:
            			System.out.println("Introduzca el numero de reserva");
            	    	num=in.nextInt();
            	    	System.out.println("A que IVA?");
            	    	double IVA=in.nextDouble();
            	    	boolean pagado= pagadoCompleto(_con,num);
            	    	if (pagado) {
            	    		rDAO.finalizarReserva(_con, num, pagado);
                	    	lDAO.generarLiquidacion(_con, num, IVA);
            	    	}
            	    	break;
            		case 2:
            			System.out.println("Introduzca el numero de factura");
            	    	num=in.nextInt();
            	    	System.out.println(lDAO.buscarLiquidacion(_con, num).toString());
            	    	break;
            		case 3:
            	    	System.out.println("Introduzca el numero de factura");
            	    	num=in.nextInt();
            	    	lDAO.imprimirLiquidacion(_con, num);
            	    	break;
            		case 4:
            			List<Liquidacion> listaLiquidacion = lDAO.mostrarLiquidaciones(_con);
            			for (Liquidacion listaL1: listaLiquidacion) {
            				System.out.println(listaL1.toString());
            			}
            	    	break;
            		}
            	}while(menu!=0);
            	break;
          //Gestion Actividades
            case 6:
                    do {                
                    Actividad act = new Actividad();
                    getMenuActividades();
                    menu=in.nextInt();
                    ArrayList<TipoCasa> tipos = tipoDAO.buscarTiposDeCasa(_con); 
                    int eleccionesTipos[] = new int[tipos.size()];
                    boolean finalizado = false;
                    int numRes = 0;
                    int numAct = 0;
                    int entradaTipos = 0;
                    int[] contadorDeTipos = {0}; 
                    String resultadoTipos[] = {""};
	            switch (menu) {
	                case 1:
	                   act=crearActividad(act);
	                   do{
	                       tipoDAO.mostrarTiposDeCasa(_con);
	                       System.out.println("Selecciona los tipos de casa a los que se podran realizar estas actividades\nPresione 0 para terminar sus elecciones");
	                       entradaTipos = in.nextInt();
	                       if(tipoDAO.existeTipo(_con, entradaTipos) == true || entradaTipos == 0){
	                        finalizado = ingresarVariosTipos(entradaTipos, eleccionesTipos, contadorDeTipos, resultadoTipos);
	                       } 
	                   }while(finalizado != true);
	                    act.setTipo_casa(resultadoTipos[0]);
	                    if (actDAO.anyadirActividad(_con, act) == true) {
	                        System.out.println("La actividad a sido registrada");
	                    } else {
	                        System.out.println("No se ha añadido");
	                    }
	                break;
	
	                case 2:
	                    actDAO.mostrarActividades(_con);
	                    System.out.println("Cual actividad desea modificar?");
	                    int actividadAModificar = ingresarActividad(_con, actDAO);
	                    if(actividadAModificar != 0){
	                        act = crearActividad(act);
	                        do{
	                       tipoDAO.mostrarTiposDeCasa(_con);
	                       System.out.println("Selecciona los tipos de casa a los que se podran realizar estas actividades\nPresione 0 para terminar sus elecciones");
	                       entradaTipos = in.nextInt();
	                       
	                       if(tipoDAO.existeTipo(_con, entradaTipos) == true || entradaTipos == 0){
	                        finalizado = ingresarVariosTipos(entradaTipos, eleccionesTipos, contadorDeTipos, resultadoTipos);
	                       } 
	                        }while(finalizado != true);
	                        
	                        act.setTipo_casa(resultadoTipos[0]);
	                        
	                        if (actDAO.actualiza(_con, act, actividadAModificar) == true) {
	                            System.out.println("La actividad a sido modificada");
	                        } else {
	                            System.out.println("No se ha añadido");
	                        }
	                    } else {
	                        System.out.println("No se ha añadido");
	                    }
	                    
	                break;
	
	                case 3:
	                    actDAO.mostrarActividades(_con);
	                    System.out.println("Cual actividad desea eliminar?");
	                    int entrada = ingresarActividad(_con, actDAO);
	                    if(entrada!=0){
	                        if (actDAO.elimina(_con, entrada) == true) {
	                        System.out.println("La actividad ha sido eliminada");
	                        } else {
	                            System.out.println("No se ha eliminado");
	                        }
	                    } else {
	                        System.out.println("Saliendo....");
	                    }
	                break;
	                
	                case 4:
	                    actDAO.mostrarActividades(_con);
	                    break; 
	                case 5:
	                    System.out.println("A cual reserva quieres meter actividad?");
	                    numRes = in.nextInt();
	                    System.out.println("Que tipo de actividad?");
	                    actDAO.mostrarActividades(_con);
	                    numAct = in.nextInt();
	                    System.out.println("Que cantidad?");
	                    int cantidad = in.nextInt();
	                	if (arDAO.anyadirActividad(_con, numAct, numRes,cantidad)) {
	                		System.out.println("Anyadido");
	                	} else {
	                		System.out.println("No Anyadido");
	                	}
	                	break;
	                case 6:
	                    System.out.println("A cual reserva quieres eliminar actividad?");
	                    numRes = in.nextInt();
	                    System.out.println("Que tipo de actividad?");
	                    actDAO.mostrarActividades(_con);
	                    numAct = in.nextInt();
	                	if (arDAO.elimina(_con, numAct, numRes)) {
	                		System.out.println("Eliminado");
		            	} else {
		            		System.out.println("No eliminado");
		            	}
	                	break;
	            	}
                } while (menu!=0);
            break;
            
            
          case 7:
                  do {                
                  Servicio serv = new Servicio();
                  getMenuServicios();
                  menu=in.nextInt();
                  int numRes = 0;
                  int numSer = 0;
                  int entrada;
		          switch (menu) {
		              case 1:
		                 serv=crearServicio(serv);
		                  if (servDAO.anyadirServicio(_con, serv) == true) {
		                      System.out.println("El servicio a sido registrada");
		                  } else {
		                      System.out.println("No se ha añadido");
		                  }
		              break;
		
		              case 2:
		                  servDAO.mostrarServicios(_con);
		                  System.out.println("Cual servicio desea modificar?");
		                  int servicioAModificar = ingresarServicio(_con, servDAO);
		                      if (servicioAModificar != 0) {
		                          serv = crearServicio(serv);
		                          if (servDAO.actualiza(_con, serv, servicioAModificar) == true) {
		                          System.out.println("El servicio a sido modificada");
		                      } else {
		                          System.out.println("No se ha añadido");
		                      }
		                  } else {
		                      System.out.println("Saliendo...");
		                  }
		              break;
		
		              case 3:
		                  servDAO.mostrarServicios(_con);
		                  System.out.println("Cual servicio desea eliminar?");
		                  entrada = ingresarServicio(_con, servDAO);
		                  if (servDAO.elimina(_con, entrada) == true && entrada != 0) {
		                      System.out.println("El servicio a sido eliminada");
		                  } else {
		                      System.out.println("No se ha eliminado");
		                  }
		              break;
		              case 4: 
		                  servDAO.mostrarServicios(_con);
		                  break;
		              case 5:
		                    System.out.println("A cual reserva quieres meter servicio?");
		                    numRes = in.nextInt();
		                    System.out.println("Que tipo de servicio?");
		                    servDAO.mostrarServicios(_con);
		                    numSer = in.nextInt();
		                    System.out.println("Que cantidad?");
		                    int cantidad = in.nextInt();
		                	if(srDAO.anyadirServicio(_con, numSer, numRes, cantidad)) {
		                		System.out.println("Anyadido");
		                	} else {
		                		System.out.println("No Anyadido");
		                	}
		                	break;
		                case 6:
		                    System.out.println("A cual reserva quieres eliminar servicio?");
		                    numRes = in.nextInt();
		                    System.out.println("Que tipo de servicio?");
		                    servDAO.mostrarServicios(_con);
		                    numSer = in.nextInt();
		                	if (srDAO.elimina(_con, numSer, numRes)) {
		                		System.out.println("Eliminado");
		                	} else {
		                		System.out.println("No eliminado");
		                	}
		                	break;
		              default:
		              break;
		            }
		          
		      } while (menu!=0);
		              break;
        	}
        }while (menu2!=0);
    }
   
	/**
	 * Metodo para posibles errores de SQL 
	 * @param ex SQLException
	 */
    public static void printSQLException (SQLException ex) {
        ex.printStackTrace(System.err);
        System.err.println("SQLState: " + ex.getSQLState());
        System.err.println("Error Code: " + ex.getErrorCode());
        System.err.println("Message: " + ex.getMessage());
        Throwable t = ex.getCause();
        while (t != null) {
            System.out.println("Cause: " + t);
            t = t.getCause();
        }
    }
	/**
	 * Imprime el menu principal
	 */
    private static void getMenu() {
    	System.out.println("\n");
    	System.out.println("Menu principal");
    	System.out.println("-------------------------------");
    	System.out.println("0. Salir");
    	System.out.println("1. Gestion clientes");
    	System.out.println("2. Gestion casas");
    	System.out.println("3. Gestion propietarios");
    	System.out.println("4. Gestion reservas");
    	System.out.println("5. Gestion facturas");
    	System.out.println("6. Gestion actividades");
        System.out.println("7. Gestion servicios");
    	System.out.println("-------------------------------");
    }
	/**
	 * Imprime el menu gestion clientes
	 */
    private static void getMenuClientes() {
    	System.out.println("\n");
    	System.out.println("Menu gestion de clientes");
    	System.out.println("-------------------------------");
    	System.out.println("0. Salir");
    	System.out.println("1. Dar alta cliente");
    	System.out.println("2. Buscar cliente");
    	System.out.println("3. Modificar cliente");
    	System.out.println("4. Eliminar cliente");	
    	System.out.println("-------------------------------");
    }
	/**
	 * Imprime el menu gestion actividades
	 */
    private static void getMenuActividades() {
    	System.out.println("\n");
    	System.out.println("Menu gestion de actividades");
    	System.out.println("-------------------------------");
    	System.out.println("0. Salir");
    	System.out.println("1. Crear actividad");
    	System.out.println("2. Modificar actividad");
    	System.out.println("3. Eliminar actividad");
        System.out.println("4. Mostrar actividades");
        System.out.println("5. Insertar actividades a una reserva");
        System.out.println("6. Eliminar actividades a una reserva");
    	System.out.println("-------------------------------");
    }
	/**
	 * Imprime el menu gestion propietarios
	 */
    private static void getMenuProp() {
    	System.out.println("\n");
    	System.out.println("Menu gestion de propietarios");
    	System.out.println("-------------------------------");
    	System.out.println("0. Salir");
    	System.out.println("1. Dar alta propietario");
    	System.out.println("2. Modificar propietario");
    	System.out.println("3. Eliminar propietario");
        System.out.println("4. Mostrar propietarios");
        System.out.println("5. Buscar propietarios");
    	System.out.println("-------------------------------");
    }
	/**
	 * Imprime el menu gestion casas
	 */
    private static void getMenuCasas() {
    	System.out.println("\n");
    	System.out.println("Menu gestion de casas");
    	System.out.println("-------------------------------");
    	System.out.println("0. Salir");
    	System.out.println("1. Dar alta casa");
    	System.out.println("2. Modificar casa");
    	System.out.println("3. Eliminar casa");
        System.out.println("4. Mostrar casas");
    	System.out.println("5. Buscar casas disponibles por provincia");
        System.out.println("6. Buscar casas por su tipo");
        System.out.println("7. Buscar precio precio por dia de una casa");
        System.out.println("8. Mostrar coordenadas de casa");
    	System.out.println("-------------------------------");
    }
	/**
	 * Imprime el menu gestion reservas
	 */
    private static void getMenuReservas() {
    	System.out.println("\n");
    	System.out.println("Menu gestion de reservas");
    	System.out.println("-------------------------------");
    	System.out.println("0. Salir");
    	System.out.println("1. Hacer reserva nueva");
    	System.out.println("2. Buscar una reserva");
    	System.out.println("3. Modificar una reserva");
    	System.out.println("4. Anular una reserva");
    	System.out.println("5. Pagar una reserva");
    	System.out.println("6. Finalizar una reserva");
    	System.out.println("7. Mostrar reservas");
    	System.out.println("8. Eliminar reservas");	
    	System.out.println("-------------------------------");
    }
	/**
	 * Imprime el menu gestion facturas
	 */
    private static void getMenuLiquidacion() {
    	System.out.println("\n");
    	System.out.println("Menu gestion de facturacion");
    	System.out.println("-------------------------------");
    	System.out.println("0. Salir");
    	System.out.println("1. Generar factura de la reserva");
    	System.out.println("2. Mostrar una factura");
    	System.out.println("3. Imprimir una factura");
    	System.out.println("4. Mostrar todas las facturas");
    	System.out.println("-------------------------------");
    }
	/**
	 * Imprime el menu gestion servicios
	 */
    private static void getMenuServicios(){
    	System.out.println("\n");
    	System.out.println("Menu gestion de servicios");
    	System.out.println("-------------------------------");
        System.out.println("0. Salir");
    	System.out.println("1. Crear servicio");
    	System.out.println("2. Modificar servicio");
    	System.out.println("3. Eliminar servicio");
        System.out.println("4. Mostrar servicio");
        System.out.println("5. Insertar servicios a una reserva");
        System.out.println("6. Eliminar servicios a una reserva");
    	System.out.println("-------------------------------");
    }
	/**
	 * Metodo que inicializa un cliente
	 * @return c un cliente
	 */
    private static Cliente darAltaCliente() {
    	Cliente c = new Cliente();
    	System.out.println("Introduzca el dni del cliente");
    	String dni=in.next();
    	@SuppressWarnings("unused")
		String linebreak=in.nextLine();
    	c.setDni(dni);
    	System.out.println("Introduzca el nombre del cliente");

    	String nombre=in.nextLine();
    	c.setNombre(nombre);
    	System.out.println("Introduzca el primer apellido del cliente");
    	String apellido1=in.nextLine();
    	c.setApellido1(apellido1);
    	System.out.println("Introduzca el segundo apellido del cliente");
    	String apellido2=in.nextLine();
    	c.setApellido2(apellido2);
    	System.out.println("Introduzca la direccion del cliente");
    	String direccion=in.nextLine();
    	c.setDireccion(direccion);
    	System.out.println("Introduzca el telefono del cliente");
    	int telefono=in.nextInt();
    	c.setTelefono(telefono);
    	
    	return c;
    }
	/**
	 * Metodo que inicializa un propietario
	 * @return p un propietario
	 */
    private static Propietario darAltaPropietario(Propietario p){
        System.out.println("Introduzca el dni del propietario");
    	String dni=in.next();
        in.nextLine();
    	p.setDni(dni);
    	System.out.println("Introduzca el nombre del propietario");
    	String nombre=in.next();
        in.nextLine();
    	p.setNombre(nombre);
    	System.out.println("Introduzca el primer apellido del propietario");
    	String apellido1=in.next();
        in.nextLine();
    	p.setApellido1(apellido1);
    	System.out.println("Introduzca el segundo apellido del propietario");
    	String apellido2=in.next();
        in.nextLine();
    	p.setApellido2(apellido2);
    	System.out.println("Introduzca la direccion del propietario");
    	String direccion=in.nextLine();
        p.setDireccion(direccion);
    	System.out.println("Introduzca el telefono del propietario");
    	int telefono=in.nextInt();
        in.nextLine();
    	p.setTelefono(telefono);
        return p;
    }
    
	/**
	 * Metodo que inicializa una casa
	 * @return casa una casa
	 */
    private static Casa darAltaCasa(Casa casa) throws SQLException{
        
        System.out.println("Direccion de la casa");
        String direccion = in.nextLine();
        casa.setDireccion(direccion);       
        
    	System.out.println("Precio por dia (€)");
    	Double precio=in.nextDouble();
        in.nextLine();
    	casa.setPrecio(precio);
    	System.out.println("Provincia");
    	String provincia=in.next();
        in.nextLine();
    	casa.setProvincia(provincia);
        System.out.println("Disponibilidad\n1.Si\n2.No");
        int seleccion=in.nextInt();
        in.nextLine();
        while(seleccion != 1 && seleccion != 2){
          System.out.println("Vuelva a ingresar su eleccion");
        seleccion=in.nextInt();
        in.nextLine();
        }
        
        String disponibilidad;
        
        switch (seleccion) {
            case 1:;
                disponibilidad = "Si";
                casa.setDisponibilidad(disponibilidad);
                break;
            case 2:
                disponibilidad = "No";
                casa.setDisponibilidad(disponibilidad);
            break;
        }
        
        System.out.println("Capacidad");
        int capacidad= in.nextInt();
        in.nextLine();
        casa.setCapacidad(capacidad);
        System.out.println("Observaciones");
        String Observaciones=in.nextLine();
        casa.setObservaciones(Observaciones);
        System.out.println("Latitude");
        Double lat = in.nextDouble();
        casa.setLatitude(lat);
        System.out.println("Longitude");
        Double lng = in.nextDouble();
        casa.setLongitude(lng);
        return casa;
    }
	/**
	 * Metodo que inicializa una actividad
	 * @return actividad una actividad
	 */
    private static Actividad crearActividad(Actividad actividad){
        
        System.out.println("Introduzca nombre de actividad");
        in.nextLine();
        String nombreAct = in.nextLine();
        actividad.setNombre(nombreAct);
        System.out.println("Descripcion de actividad");
        String desc = in.nextLine();
        actividad.setDescripcion(desc);
        System.out.println("Precio");
        int precio = in.nextInt();
        in.nextLine();
        actividad.setPrecio(precio);
        return actividad;
    }
	/**
	 * Metodo que inicializa un servicio
	 * @return servicio un servicio
	 */
    private static Servicio crearServicio(Servicio servicio){
        
        System.out.println("Introduzca el tipo de servicio");
        in.nextLine();
        String nombreServ = in.nextLine();
        servicio.setTipoServicio(nombreServ);
        System.out.println("Precio");
        int precio = in.nextInt();
        in.nextLine();
        servicio.setPrecio(precio);
        return servicio;
    }
	/**
	 * Metodo que inicializa una reserva
	 * @param con conexion con la base de datos
	 * @return r una reserva
	 */
    private static Reserva nuevaReserva(Connection con) throws SQLException {
    	
    	Reserva r = new Reserva();
    	Date fecha= new Date(Calendar.getInstance().getTime().getTime());
    	CasaDAO cDAO = new CasaDAO();
    	
    	r.setFechaReserva((java.sql.Date) fecha);
    	System.out.println("Introduzca el dni del cliente");
    	String dni=in.next();
    	r.setdnicliente(dni);
    	System.out.println("Introduzca los dias de reserva");
    	int dias=in.nextInt();
    	r.setDiasReserva(dias);
    	System.out.println("Introduzca el codigo de la casa");
    	int codV = in.nextInt();
    	r.setCod_vivienda(codV);
    	System.out.println("Lo gestiona la agencia? 1.Si / Cualquier otra tecla: No");
    	int gestion=in.nextInt();
    	if(gestion!=1) {
        	r.setGestionAgencia(false);
    	} else {
        	r.setGestionAgencia(true);
    	}
    	double impTotal = (dias*3)+(cDAO.precioCasaPorDia(con, codV)*dias);
    	r.setImporteTotal(impTotal);
    	double entrada=0;
    	do {
        	System.out.println("Introduce el pago de la entrada");
        	entrada = in.nextDouble();
        	if (entrada < (impTotal/10)) {
        		System.out.println("Entrada insuficiente, para hacer la reserva, el minimo a pagar es: "+(impTotal/10));
        		System.out.println("Si no desea pagar la entrada, introduzca 0");
        	} else {
        		r.setEntradaReserva(entrada);
        		entrada=0;
        	}	
    	} while (entrada!=0);
    	r.setEstadoReserva("Abierta");
    	
    	return r;
   }
	/**
	 * Metodo que muestra todas las Actividades Y servicios de una reserva
	 * @param con conexion con la base de datos
	 * @param numRes el numero de la reserva
	 */
    private static void mostrarAyS(Connection _con, int numRes) {
        ARDAO arDAO = new ARDAO();
        SRDAO srDAO = new SRDAO();
        ActividadDAO actDAO = new ActividadDAO();
        ServicioDAO servDAO = new ServicioDAO();
        List<ActividadesRealizadas> listA = new ArrayList<ActividadesRealizadas>();
        List<ServiciosRealizados> listS = new ArrayList<ServiciosRealizados>();
        
        try {
            listA=arDAO.mostrarActividades(_con, numRes);
            listS=srDAO.mostrarServicios(_con, numRes);
        	System.out.println("Actividades:");
            for(ActividadesRealizadas lista: listA) {
            	System.out.println(actDAO.buscarActividad(_con, lista.getCodActividad()));
            	System.out.println("Cantidad: "+lista.getCantidad());
            	System.out.println("Fecha: "+lista.getFecha());
            	System.out.println("----------------------------");
            }
        	System.out.println("Servicios:");
            for(ServiciosRealizados lista: listS) {
            	System.out.println(servDAO.buscarServicio(_con, lista.getCodServicio()));
            	System.out.println("Cantidad: "+lista.getCantidad());
            	System.out.println("Fecha: "+lista.getFecha());
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
	/**
	 * Metodo que realiza el pago de la reserva y sus actividades y servicios
	 * @param _con conexion con la base de datos
	 * @param numReserva el numero de la reserva
	 * @return pagado Boolean que nos dice si esta pagado (true) o no
	 */
    private static boolean pagadoCompleto(Connection _con, int numReserva) throws SQLException {
    	boolean pagado = false;
    	ARDAO arDAO = new ARDAO();
    	SRDAO srDAO = new SRDAO();
    	ReservaDAO rDAO = new ReservaDAO();
    	Reserva r1 = rDAO.buscarReserva(_con, numReserva);
    	double precioTotal=0;
    	precioTotal+=r1.getImporteTotal();
    	precioTotal+=arDAO.getPrecio(_con, numReserva);
    	precioTotal+=srDAO.getPrecio(_con, numReserva);
    	double falta= precioTotal-r1.getEntradaReserva();
    	do {
    		System.out.println("Falta por pagar: "+falta);
    		System.out.println("Introduzca el pago:");
    		double pago=in.nextDouble();
    		if (pago>=falta) {
    			pagado=true;
    			if(pago>falta) {
    				System.out.println("Devolver"+(pago-falta));
    			}
    		} else if (pago<falta) {
    			System.out.println("Pago insuficiente");
    		}
    		
    	} while(pagado==false);
    	
    	return pagado;
    }
	/**
	 * Metodo que permite al usuario ingresar las actividades que quiere
	 * @param _con conexion con la base de datos
	 * @param actDAO conexion con las gestiones de actividad
	 * @return entrada int que Verifica si el codigo de actividad existe en la bases de datos
	 */
    private static int ingresarActividad(Connection _con, ActividadDAO actDAO) throws SQLException{
           int entrada = in.nextInt();
           while(actDAO.existeActividad(_con, entrada) == false && entrada != 0){
           System.out.println("Actividad no encontrada, intentalo de nuevo. (Presiona 0 para no añadir actividad)");
           entrada = in.nextInt(); 
        }
           return entrada;
    }
	/**
	 * Metodo que permite al usuario ingresar los servicios que quiere
	 * @param _con conexion con la base de datos
	 * @param servDAO conexion con las gestiones de servicios
	 * @return entrada int que Verifica si el codigo de servicio existe en la bases de datos
	 */
    private static int ingresarServicio(Connection _con, ServicioDAO servDAO) throws SQLException{
        int entrada = in.nextInt();
           while(servDAO.existeServicio(_con, entrada) == false && entrada != 0){
           System.out.println("Servicio no encontrado, intentalo de nuevo. (Presiona 0 para no añadir servicio)");
           entrada = in.nextInt(); 
        }
           return entrada;
    }
    /**
     * Metodo que permite crear un String con todas las selecciones que el usuario quiera.
     * Devuelve true si el usuario finaliza el proceso, devuelve false en caso contrario
     * @param EntradaTipos es el numero que el usuario ingresa
     * @param eleccionesTipo es el vector que almacena todas las entradas VALIDAS que el usuario ha realizado
	 * @param contadorDeTipos Es el contador de elementos que hay almacenados en eleccionesTipo
 	 * @param resultadoTipos Es la cadena resultante con todas las elecciones del usuario 
     */
    private static boolean ingresarVariosTipos(int entradaTipos, int eleccionesTipos[], int contadorDeTipos[], String resultadoTipos[]) throws SQLException{
        boolean terminado = false;
        boolean encontrado = false;
        int entradas;
        for (int i = 0; i < eleccionesTipos.length; i++) {
        if (eleccionesTipos[i] == entradaTipos) {
            if(entradaTipos != 0){
                System.out.println("Usted ya ha seleccionado este tipo");
            }
         encontrado = true;
        } 
       }
        entradas = entradaTipos;
       if(contadorDeTipos[0] < eleccionesTipos.length && encontrado == false){
          eleccionesTipos[contadorDeTipos[0]] = entradas;
          contadorDeTipos[0]++;
       } 
       if(entradas == 0){
           terminado = true;
           ordenadorDeVectores(eleccionesTipos);
            for (int i = 0; i < eleccionesTipos.length; i++) {
                if(eleccionesTipos[i] != 0){
                resultadoTipos[0] += String.valueOf(eleccionesTipos[i]);
                if(i != eleccionesTipos.length-1){
                resultadoTipos[0] += ","; 
                }
               }
            }
            
            if (contadorDeTipos[0] == 0) {
               resultadoTipos[0] += String.valueOf(0);
           }
       } 
       
        return terminado;
    }
	/**
	 * Metodo que ordena las casas
	 * @param v Array de vectores
	 */
    private static void ordenadorDeVectores(int []v) throws SQLException{
        int aux;
        for (int i = 0; i < v.length; i++) {
            for (int j = 1; j < v.length; j++) {
                if (v[j-1] > v[j]) {
                    aux = v[j];
                    v[j] = v[j-1];
                    v[j-1] = aux;
                } 
            }
        }
    }
	/**
	 * Metodo que muestra listas
	 * @param entrada arraylists de cosas
	 */
    private static void mostrarListasArrayList(ArrayList entrada){
        for (int i = 0; i < entrada.size(); i++) {
            System.out.println(entrada.get(i));
        }
    }
}
 

