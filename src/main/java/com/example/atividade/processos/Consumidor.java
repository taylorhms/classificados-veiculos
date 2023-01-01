package com.example.atividade.processos;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import com.example.atividade.domain.Veiculo;

public class Consumidor {
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String subject = "classificados";
	private static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	private static void consumir() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageConsumer consumer = session.createConsumer(destination);
        Message message = consumer.receive();
        
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            
            XStream xstream = new XStream(new StaxDriver());
            xstream.addPermission(AnyTypePermission.ANY);
            Veiculo v = (Veiculo) xstream.fromXML(textMessage.getText());
            
            System.out.printf("Recebido:\n"
            		+ "Nome cliente: %s\n"
            		+ "Marca/Modelo: %s\n"
            		+ "Ano: %d\n"
            		+ "Valor venda: R$ %.2f\n"
            		+ "Publicação: %s\n",
            		v.getNomeCliente(), v.getMarcaModeloVeiculo(), v.getAnoModelo(), v.getValorVenda(), formato.format(v.getDataPublicacao()));
            
            // Adicionando via Rest
            RestTemplate rt = new RestTemplate();
            rt.postForEntity("http://localhost:8080/veiculos", v, Veiculo.class);
        } else {
            System.out.println("** SEM MENSAGENS **  ");
        }
        consumer.close();
        session.close();
        connection.close();
	}
	
	private static void exibirVeiculos() {
		System.out.println("\nVeiculos:\n");
		System.out.printf("%-4s %-20s %-20s %-7s %-13s %-10s\n", "ID", "NOME CLIENTE", "MARCA/MODELO", "ANO", "VALOR VENDA", "PUBLICACAO");
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<Veiculo[]> resposta = rt.getForEntity("http://localhost:8080/veiculos", Veiculo[].class);
		Veiculo[] veiculos = resposta.getBody();
		
		for (Veiculo v : veiculos) {
			System.out.printf("%-4d %-20s %-20s %-7d R$ %-10.2f %-10s\n",
					v.getId(), v.getNomeCliente(), v.getMarcaModeloVeiculo(), v.getAnoModelo(), v.getValorVenda(), formato.format(v.getDataPublicacao()));
		}
	}
	
	public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("=========================\nPara visualizar a lista de Veiculos tecle enter\n=========================");
		
        // Recebe os veiculos
        new Thread(() -> {
        	try {
				while (true) consumir();
			} catch (JMSException e) {
				e.printStackTrace();
			}
        }).start();
		
        // Aguarda Enter para exibir a lista de veiculos
        while (true) {
			s.nextLine();
			exibirVeiculos();
		}
    }

}
