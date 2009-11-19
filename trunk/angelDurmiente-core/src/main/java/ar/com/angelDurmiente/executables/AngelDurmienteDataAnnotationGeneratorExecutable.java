/**
 * 
 */
package ar.com.angelDurmiente.executables;

import ar.com.angelDurmiente.dataGenerators.CancionAnnotationDataGenerator;
import ar.com.angelDurmiente.dataGenerators.UsuarioAnnotationDataGenerator;

import com.angel.common.interfaces.Executable;
import com.angel.data.generator.base.DataGeneratorRunner;
import com.angel.data.generator.builders.impl.DataGeneratorAnnotationRunnerBuilder;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class AngelDurmienteDataAnnotationGeneratorExecutable implements Executable {

	/* (non-Javadoc)
	 * @see com.angel.common.interfaces.Executable#execute()
	 */
	public void execute() {
		DataGeneratorAnnotationRunnerBuilder builder = new DataGeneratorAnnotationRunnerBuilder();
		builder.addDataGeneratorClass(UsuarioAnnotationDataGenerator.class);
		builder.addDataGeneratorClass(CancionAnnotationDataGenerator.class);
		DataGeneratorRunner runner = builder.buildDataGeneratorRunner();
		runner.generateData();
		runner.finalizeGenerator();
	}
}
