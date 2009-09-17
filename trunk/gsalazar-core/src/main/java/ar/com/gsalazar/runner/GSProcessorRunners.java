/**
 * 
 */
package ar.com.gsalazar.runner;

import ar.com.gsalazar.executables.DataGeneratorExecutable;

import com.angel.architecture.runner.ApplicationProcessRunner;
import com.angel.architecture.spring.ApplicationContextSpring;

/**
 * @author Guillermo Salazar.
 *
 */
public class GSProcessorRunners {

	public static String[] MC_SPRING_APPLICATION_CONTEXTS = new String[]{
		"classpath:applicationContext-gsflex.xml",
		"classpath:applicationContext-gsdaos.xml",
		"classpath:applicationContext-gsservices.xml",
		"classpath:applicationContext-gsmodel.xml"
	};
	                                                                   
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContextSpring applicationContextSpring = ApplicationContextSpring.createInstance(MC_SPRING_APPLICATION_CONTEXTS).prepareApplicationContext();
		ApplicationProcessRunner applicationProcessRunner = applicationContextSpring.getApplicationProcessRunner();
		applicationProcessRunner.addExecutable(new DataGeneratorExecutable());
		applicationProcessRunner.run();
	}

}
