/**
 * 
 */
package ar.com.gsalazar.executables;

import ar.com.gsalazar.dataGenerators.ConfigurationParameterDataGenerator;
import ar.com.gsalazar.dataGenerators.ParameterAreaDataGenerator;

import com.angel.common.interfaces.Executable;
import com.angel.data.generator.base.DataGeneratorRunner;
import com.angel.data.generator.builders.impl.DataGeneratorRunnerImplBuilder;

/**
 * @author Guillermo Salazar
 * @since 27/Agosto/2009.
 *
 */
public class GSDataClassesGeneratorExecutable implements Executable {

	/* (non-Javadoc)
	 * @see com.angel.common.interfaces.Executable#execute()
	 */
	public void execute() {
		DataGeneratorRunnerImplBuilder builder = new DataGeneratorRunnerImplBuilder();
		builder.addDataGeneratorClass(ParameterAreaDataGenerator.class);
		builder.addDataGeneratorClass(ConfigurationParameterDataGenerator.class);
		DataGeneratorRunner runner = builder.buildDataGeneratorRunner();
		runner.generateData();
		runner.finalizeGenerator();
	}
}
