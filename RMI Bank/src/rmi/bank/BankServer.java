
package rmi.bank;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankServer implements Runnable {
    
    Connection c;
    static int dataAtual;
    
    public BankServer() {
        conectar();
    }
    
    public static void main(String args[]) {
        Thread t1 = new Thread();
        t1.start();
        
        try {
            Bank b = new Bank();
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.bind("rmi://localhost/Bank", b);
            System.out.println("Rodando!");
            for(int i = 0; i < 100000000; i++) {
                System.out.println("dia: " + dataAtual);
            }
        } catch (Exception ex) {
            System.out.println("Bugou!");
        }
    }
    
    public void run() {
        while(true) {
            for(dataAtual = 1; dataAtual < 31; dataAtual++) {
                try {
                    System.out.println("Hoje é o dia " + dataAtual + "\n");
                    Thread.sleep(10000);
                } catch(InterruptedException ex) {
                    Logger.getLogger(BankServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void conectar() {
        try {
            String url = "jdbc:mysql://localhost:3306/rmi_bank?user=root&password=";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            c = (Connection) DriverManager.getConnection(url);
            System.out.println("Conectado!");
        } catch(Exception e) {}
    }
    
}
