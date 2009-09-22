/**
 * 
 */
package ar.com.gsalazar.executables;

import ar.com.gsalazar.dataGenerators.CategoriaAnnotationDataGenerator;
import ar.com.gsalazar.dataGenerators.ContactoWebAnnotationDataGenerator;
import ar.com.gsalazar.dataGenerators.ItemCategoriaAnnotationDataGenerator;
import ar.com.gsalazar.dataGenerators.SubCategoriasAnnotationDataGenerator;
import ar.com.gsalazar.dataGenerators.TagSearchAnnotationDataGenerator;

import com.angel.common.interfaces.Executable;
import com.angel.data.generator.base.DataGeneratorRunner;
import com.angel.data.generator.builders.impl.DataGeneratorAnnotationRunnerBuilder;

/**
 * @author Guillermo Salazar
 * @since 27/Agosto/2009.
 *
 */
public class DataGeneratorExecutable implements Executable {

	/* (non-Javadoc)
	 * @see com.angel.common.interfaces.Executable#execute()
	 */
	public void execute() {
		DataGeneratorAnnotationRunnerBuilder builder = new DataGeneratorAnnotationRunnerBuilder();
		builder.addDataGeneratorClass(ItemCategoriaAnnotationDataGenerator.class);
		builder.addDataGeneratorClass(CategoriaAnnotationDataGenerator.class);
		builder.addDataGeneratorClass(SubCategoriasAnnotationDataGenerator.class);
		builder.addDataGeneratorClass(ContactoWebAnnotationDataGenerator.class);
		builder.addDataGeneratorClass(TagSearchAnnotationDataGenerator.class);
		DataGeneratorRunner runner = builder.buildDataGeneratorRunner();
		runner.generateData();
		runner.finalizeGenerator();
	}
}
