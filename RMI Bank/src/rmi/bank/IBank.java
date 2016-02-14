
package rmi.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBank extends Remote{
    
    public String acessarConta(String conta, String senha) throws RemoteException;
    
    public String criarConta(String nome, String senha) throws RemoteException;
    
    public boolean excluirConta(String conta) throws RemoteException;
    
    public boolean depositar(String conta, double valor, String dia) throws RemoteException;
    
    public boolean sacar(String conta, double valor, String dia) throws RemoteException;
    
    public boolean transferir(String contaOrigem, String contaDestino, double valor, String dia) throws RemoteException;
    
    public String verificarSaldo(String conta) throws RemoteException;
    
    public String[] retirarExtrato(String conta) throws RemoteException;
    
}
