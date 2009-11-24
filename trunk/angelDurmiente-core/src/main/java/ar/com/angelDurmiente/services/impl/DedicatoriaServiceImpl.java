/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.beans.Dedicatoria;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.DedicatoriaDAO;
import ar.com.angelDurmiente.dtos.TextoDTO;
import ar.com.angelDurmiente.helpers.CalendarHelper;
import ar.com.angelDurmiente.services.DedicatoriaService;
import ar.com.angelDurmiente.services.TextoService;

import com.angel.architecture.exceptions.BusinessException;
import com.angel.architecture.services.impl.GenericServiceImpl;
import com.angel.email.account.AccountInformation;
import com.angel.email.account.impl.MockAccountInformation;
import com.angel.email.builder.EmailBuilder;
import com.angel.email.builder.impl.SimpleEmailBuilder;
import com.angel.email.configuration.builders.EmailConfigurationBuilder;
import com.angel.email.configuration.builders.OutgoingEmailConfigurationBuilder;
import com.angel.email.configuration.builders.impl.yahoo.YahooOutgoingEmailConfigurationBuilder;
import com.angel.email.services.EmailService;

/**
 * @author Guillermo Salazar
 *
 */
public class DedicatoriaServiceImpl extends GenericServiceImpl implements DedicatoriaService {

	@Autowired
	private TextoService textoService;
	
	@Autowired
	private EmailService emailService;

	protected DedicatoriaDAO getDedicatoriaDAO(){
		return (DedicatoriaDAO) super.getGenericDAO();
	}

	public List<Dedicatoria> buscarTodosPorDedicador(Usuario dedicador) {
		return this.getDedicatoriaDAO().buscarTodosPorDedicador(dedicador);
	}

	public List<Dedicatoria> buscarTodosPorDedicatorio(Usuario dedicatorio) {
		return this.getDedicatoriaDAO().buscarTodosPorDedicatorio(dedicatorio);
	}

	public List<Dedicatoria> buscarTodosPorRangoFechas(Date desde, Date hasta) {
		return this.getDedicatoriaDAO().buscarTodosPorRangoFechas(desde, hasta);
	}

	public List<Dedicatoria> buscarTodosPorTexto(Texto texto) {
		return this.getDedicatoriaDAO().buscarTodosPorTexto(texto);
	}

	public List<Dedicatoria> buscarTodosPorUsuarioTexto(String nombreUsuario) {
		return this.getDedicatoriaDAO().buscarTodosPorUsuarioTexto(nombreUsuario);
	}
	
	public Dedicatoria dedicarTexto(TextoDTO textoDTO, Usuario dedicatario, Usuario dedicador, Date desde, Date hasta) {
		List<Dedicatoria> dedicatorias = this.getDedicatoriaDAO().buscarTodosPorRangoFechas(desde, hasta);
		if(dedicatorias.isEmpty()){
			Texto texto = this.getTextoService().buscarUnicoPorTitulo(textoDTO.getTitulo());
			Dedicatoria dedicatoria = this.buildDedicatoria(texto, dedicatario, dedicador, desde, hasta);
			dedicatoria = (Dedicatoria) super.create(dedicatoria);
	
			EmailBuilder emailBuilder = this.buildEmailBuilder(dedicatario, dedicador, texto);
	
			this.getEmailService().sendEmail(emailBuilder);
			return dedicatoria;
		}
		throw new BusinessException("Ya existe dedicatorias [" + dedicatorias.size() + "] para ese " +
				"periodo de fechas [" + CalendarHelper.formatDate("DD-MM-YYYY", desde) + " - " + 
				CalendarHelper.formatDate("DD-MM-YYYY", hasta) + "].");
	}
	
	public EmailBuilder buildEmailBuilder(Usuario dedicatario, Usuario dedicador, Texto texto){
		AccountInformation accountInfo = new MockAccountInformation(dedicador.getEmail(), "42416467");
		EmailConfigurationBuilder ecb = new EmailConfigurationBuilder(Boolean.FALSE);
		ecb.setOutgoingConfigurationBuilder(new YahooOutgoingEmailConfigurationBuilder(accountInfo));
		OutgoingEmailConfigurationBuilder outgoingConfBuilder = ecb.getOutgoingConfigurationBuilder();
		List<String> toEmails = new ArrayList<String>();
		toEmails.add(dedicatario.getEmail());
		String subject = ":: elAngelDurmiente.com.ar :: - " + dedicatario.getNombreCompleto() + " ha recibido una dedicatoria de " + dedicador.getNombreCompleto() + ".";
		
		String message = "\nHola " + dedicatario.getNombre() +",\n\n";
		message += "\t\t" + dedicador.getNombreCompleto() + " (" + dedicador.getEmail() + ") te ha realizado una dedicatoria en \"El Angel Durmiente\" del texto \"" + texto.getTitulo() + "\".\n\n";
		message += "\t\tPara poder visualizarla, por favor ingresa a http://www.elangeldurmiente.com.ar.\n\n";
		message += "Muchas Gracias\n";
		message += "Equipo de El Angel Durmiente.\n";
		EmailBuilder emailBuilder = new SimpleEmailBuilder(outgoingConfBuilder, message, toEmails, subject);
		return emailBuilder;
	}
	
	protected Dedicatoria buildDedicatoria(Texto texto, Usuario dedicatario, Usuario dedicador, Date desde, Date hasta){
		Dedicatoria dedicatoria = new Dedicatoria();
		dedicatoria.setDedicador(dedicador);
		dedicatoria.setDedicatario(dedicatario);
		dedicatoria.setTexto(texto);
		dedicatoria.setDesde(desde);
		dedicatoria.setHasta(hasta);
		return dedicatoria;
	}

	/**
	 * @return the textoService
	 */
	protected TextoService getTextoService() {
		return textoService;
	}

	/**
	 * @param textoService the textoService to set
	 */
	protected void setTextoService(TextoService textoService) {
		this.textoService = textoService;
	}

	/**
	 * @return the emailService
	 */
	protected EmailService getEmailService() {
		return emailService;
	}

	/**
	 * @param emailService the emailService to set
	 */
	protected void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	
}
