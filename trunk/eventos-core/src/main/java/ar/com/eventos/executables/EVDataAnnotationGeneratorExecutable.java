/**
 * 
 */
package ar.com.eventos.executables;

import ar.com.eventos.dataGenerators.EventoAnnotationDataGenerator;
import ar.com.eventos.dataGenerators.PersonaAnnotationDataGenerator;
import ar.com.eventos.dataGenerators.SalonAnnotationDataGenerator;

import com.angel.common.interfaces.Executable;
import com.angel.data.generator.base.DataGeneratorRunner;
import com.angel.data.generator.builders.impl.DataGeneratorAnnotationRunnerBuilder;

/**
 * @author Guillermo Salazar
 * @since 27/Agosto/2009.
 *
 */
public class EVDataAnnotationGeneratorExecutable implements Executable {

	/* (non-Javadoc)
	 * @see com.angel.common.interfaces.Executable#execute()
	 */
	public void execute() {
		DataGeneratorAnnotationRunnerBuilder builder = new DataGeneratorAnnotationRunnerBuilder();
		builder.addDataGeneratorClass(SalonAnnotationDataGenerator.class);
		builder.addDataGeneratorClass(EventoAnnotationDataGenerator.class);
		builder.addDataGeneratorClass(PersonaAnnotationDataGenerator.class);
		DataGeneratorRunner runner = builder.buildDataGeneratorRunner();
		runner.generateData();
		runner.finalizeGenerator();
	}
}
