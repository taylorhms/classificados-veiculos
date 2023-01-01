package com.example.atividade.processos;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import com.example.atividade.domain.Veiculo;

public class Produtor {
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String subject = "classificados";
	
	private static void enviar(Veiculo veiculo) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);
        
        XStream xstream = new XStream(new StaxDriver());
        TextMessage message = session.createTextMessage(xstream.toXML(veiculo));
        producer.send(message);
        connection.close();
	}
	
	public static void main(String[] args) throws JMSException {
		Scanner s = new Scanner(System.in);
		Veiculo v = new Veiculo();
		boolean continuar;
		
		do {
			System.out.println("VEICULO");
			System.out.print(" | Nome do Cliente: ");
	    	v.setNomeCliente(s.nextLine());
	    	System.out.print(" | Marca/Modelo: ");
	    	v.setMarcaModeloVeiculo(s.nextLine());
	    	System.out.print(" | Ano: ");
	    	v.setAnoModelo(Integer.parseInt(s.nextLine()));
	    	System.out.print(" | Valor de Venda: ");
	    	v.setValorVenda(Double.parseDouble(s.nextLine()));
	    	enviar(v);
	    	System.out.println("\nEnviado.");
	    	System.out.print("Adicionar outro? (s/n) ");
	    	continuar = s.nextLine().toLowerCase().equals("s");
		} while (continuar);
    	
        s.close();
    }
}
