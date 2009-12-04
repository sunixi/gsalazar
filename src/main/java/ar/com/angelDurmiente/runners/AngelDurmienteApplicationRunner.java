/**
 * 
 */
package ar.com.angelDurmiente.runners;

import ar.com.angelDurmiente.executables.AngelDurmienteDataAnnotationGeneratorExecutable;

import com.angel.architecture.runner.ApplicationProcessRunner;
import com.angel.architecture.spring.ApplicationContextSpring;

/**
 * @author Guillermo Salazar.
 *
 */
public class AngelDurmienteApplicationRunner {

	public static String[] MC_SPRING_APPLICATION_CONTEXTS = new String[]{
		"classpath:applicationContext-adflex.xml",
		"classpath:applicationContext-addaos.xml",
		"classpath:applicationContext-adservices.xml",
		"classpath:applicationContext-admodel.xml"
	};
	                                                                   
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContextSpring applicationContextSpring = ApplicationContextSpring.createInstance(MC_SPRING_APPLICATION_CONTEXTS).prepareApplicationContext();
		ApplicationProcessRunner applicationProcessRunner = applicationContextSpring.getApplicationProcessRunner();
		applicationProcessRunner.addExecutable(new AngelDurmienteDataAnnotationGeneratorExecutable());
		applicationProcessRunner.run();
	}

}
