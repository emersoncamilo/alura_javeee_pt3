package br.com.casadocodigo.loja.conf;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
		name="java:/jms/topics/CarrinhoComprasTopico",
		interfaceName="javax.jms.Topic",
		destinationName="java:/jms/topics/CarrinhoComprasTopico"
)
@Stateless
public class ConfigureJMSDestination {

}
