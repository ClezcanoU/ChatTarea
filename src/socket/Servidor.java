
package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor extends javax.swing.JFrame {


    public Servidor() {
        initComponents();
        this.setVisible(true);
       // this.conexion();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
                conexion();
            }
        });
    }

    public static void conexion() {
        jTextArea1.setText("Esperando..");
        try {
            ServerSocket serverSocket = new ServerSocket(12345); 
            jTextArea1.append("Esperando conexiones...");
            //System.out.println("Esperando conexiones...");

            Socket socket = serverSocket.accept();
            jTextArea1.append("Cliente conectado");
            //System.out.println("Cliente conectado");

            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

            BufferedReader entradaConsola = new BufferedReader(new InputStreamReader(System.in));

            Datos datosCliente;
            Datos datosServidor;

            while (true) {
                datosCliente = (Datos) entrada.readObject(); 
                if (datosCliente.getMensaje().equalsIgnoreCase("fin")) {
                    break;
                }
                jTextArea1.append("Cliente - Nick: " + datosCliente.getNick() + ", IP: " + datosCliente.getIp() + ", Mensaje: " + datosCliente.getMensaje());
                //System.out.println("Cliente - Nick: " + datosCliente.getNick() + ", IP: " + datosCliente.getIp() + ", Mensaje: " + datosCliente.getMensaje());

                jTextArea1.append("Servidor: ");
                //System.out.print("Servidor: ");
                String mensajeServidor = entradaConsola.readLine();
                datosServidor = new Datos("Servidor", "localhost", mensajeServidor);
                salida.writeObject(datosServidor);
            }

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
//