
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    ServerSocket servidor;
    Map<String, String> diccionario;

    public static void main(String[] args) {
        Servidor obj1 = new Servidor();
        obj1.iniciarSocketServidor(100);
    }

    public void iniciarSocketServidor(int puerto) {
        try {
            cargarDiccionario();
            servidor = new ServerSocket(puerto);

            while (true) {
                System.out.println("Esperando conexiones en el puerto: " + puerto);
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                String lenguajeP = entrada.readUTF();

                String resultado = diccionario.get(lenguajeP);
                
                if (resultado != null) {
                    DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
                    salida.writeUTF(resultado);
                    System.out.println("Resultado para el cliente: " + resultado);
                } else {
                    DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
                    salida.writeUTF("Palabra no encontrada en el diccionario.");
                }

                cliente.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarDiccionario() {
        diccionario = new HashMap<>();
        diccionario.put("Java", "Lenguaje de programación");
        diccionario.put("Python", "Lenguaje de programación");
        diccionario.put("HTML", "Lenguaje de marcado");
        diccionario.put("CSS", "Styles");
    }

    public Servidor() {
    }

    public ServerSocket getServidor() {
        return servidor;
    }
}
