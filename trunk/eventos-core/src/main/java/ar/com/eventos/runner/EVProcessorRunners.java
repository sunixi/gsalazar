/**
 * 
 */
package ar.com.eventos.runner;

import ar.com.eventos.executables.EVDataAnnotationGeneratorExecutable;

import com.angel.architecture.runner.ApplicationProcessRunner;
import com.angel.architecture.spring.ApplicationContextSpring;

/**
 * @author Guillermo Salazar.
 *
 */
public class EVProcessorRunners {

	public static String[] EVENTOS_SPRING_APPLICATION_CONTEXTS = new String[]{
		"classpath:applicationContext-evflex.xml",
		"classpath:applicationContext-evdaos.xml",
		"classpath:applicationContext-evservices.xml",
		"classpath:applicationContext-evmodel.xml"
	};
	                                                                   
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContextSpring applicationContextSpring = ApplicationContextSpring.createInstance(EVENTOS_SPRING_APPLICATION_CONTEXTS).prepareApplicationContext();
		ApplicationProcessRunner applicationProcessRunner = applicationContextSpring.getApplicationProcessRunner();
		applicationProcessRunner.addExecutable(new EVDataAnnotationGeneratorExecutable());
		applicationProcessRunner.run();
	}

}
