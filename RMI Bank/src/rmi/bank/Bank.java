
package rmi.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bank extends UnicastRemoteObject implements IBank {

    Connection c;
    
    public Bank() throws RemoteException {
    }
    
    @Override
    public String acessarConta(String codigo, String senha) throws RemoteException {
        try {
            String query = "select nome from conta where codigo = '" + codigo + "' AND senha = '" + senha + "'";
            Statement st = (Statement) c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    return rs.getString("nome");
                }
            }
        } catch (Exception e) {}
        
        return "BUGATTI VEYRON";
    }

    @Override
    public String criarConta(String senha, String nome) throws RemoteException {
        try {
            String query = "insert into conta (senha, nome, saldo_total) "
                         + "values ('" + senha + "', '" + nome + "', 0)";
            Statement st = (Statement) c.createStatement();
            int resultado = st.executeUpdate(query);
            
            /*if(resultado == 1) {
                for(int contador = 1; contador < 31; contador++) {
                    String query2 = "insert into calendario (codigo, dia, saldo) "
                                 + "values (" + codigo + ", '" + nome + "', 0)";
                    Statement st2 = (Statement) c.createStatement();
                    st.executeUpdate(query);
                }
            }*/
        } catch (Exception e) {}
        
        return "BUGATTI VEYRON";
    }

    @Override
    public boolean excluirConta(String codigo) throws RemoteException {
        try {
            String query = "delete from conta where codigo = " + codigo;
            Statement st = (Statement) c.createStatement();
            int resultado = st.executeUpdate(query);
            
            if (resultado == 1) {
                System.out.println("Conta de número " + codigo + " deletada.");
            }
        } catch (Exception e) {}
        
        return true;
    }

    @Override
    public boolean depositar(String codigo, double valor, String dia) throws RemoteException {
        try {
            String query = "select saldo from calendario where codigo = " + codigo + " AND dia = " + dia;
            Statement st = (Statement) c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    if(rs.getFloat("saldo") > 0) {
                        
                    }
                    return true;
                }
            }
        } catch (Exception e) {}
        
        return false;
    }

    @Override
    public boolean sacar(String codigo, double valor, String dia) throws RemoteException {
        do {
        } while(valor > 0);
        return true;
    }

    @Override
    public boolean transferir(String codigoOrigem, String codigoDestino, double valor, String dia) throws RemoteException {
        return true;
    }

    @Override
    public String verificarSaldo(String codigo) throws RemoteException {
        try {
            String query = "select saldo_total from conta where codigo = " + codigo;
            Statement st = (Statement) c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    return String.format("%.2f" + rs.getFloat("saldo_total"));
                }
            }
        } catch (Exception e) {}
        
        return "BUGATTI VEYRON";
    }

    @Override
    public String[] retirarExtrato(String codigo) throws RemoteException {
        String extrato[] = new String[30];
        
        try {
            String query = "select dia, saldo from calendario where codigo = " + codigo;
            Statement st = (Statement) c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    int dia = Integer.parseInt(rs.getString("dia"));
                    extrato[dia] = "Dia " + dia + ": R$ " + rs.getString("saldo");
                }
            }
        } catch (Exception e) {}
        
        return extrato;
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
