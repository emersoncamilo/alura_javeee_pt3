package br.com.casadocodigo.loja.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.infra.MailSender;
import br.com.casadocodigo.loja.models.Compra;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destinationLookup",
				propertyValue = "java:/jms/topics/CarrinhoComprasTopico"),
		@ActivationConfigProperty(
				propertyName = "destinationType",
				propertyValue = "javax.jms.Topic")
})
public class EnviaEmailCompra implements MessageListener {

	@Inject
	private MailSender mailSender;
	@Inject
	CompraDao compraDao;

	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			Compra compra = compraDao.buscaPorUuid(textMessage.getText());
			String messageBody = "Sua compra foi realizada com Sucesso, com numero de pedido " + compra.getUuid();
			mailSender.send("compras@casadocodigo.com.br", compra.getUsuario().getEmail(), "Nova compra do CDC",
					messageBody);

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
