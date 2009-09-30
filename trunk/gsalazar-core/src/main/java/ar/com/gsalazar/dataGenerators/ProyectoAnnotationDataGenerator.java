/**
 * 
 */
package ar.com.gsalazar.dataGenerators;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.resources.ResourceLocation;
import ar.com.gsalazar.rowProcessors.ProyectoAnnotationRowProcessorCommand;

import com.angel.common.helpers.FileHelper;
import com.angel.data.generator.annotations.Generator;
import com.angel.data.generator.annotations.importFileProcessorRunner.ImportFileProcessorRunnerBuilder;
import com.angel.data.generator.annotations.inputStream.InputStreamBuilder;
import com.angel.data.generator.exceptions.DataGeneratorException;
import com.angel.io.descriptor.FileProcessorDescriptor;
import com.angel.io.processors.commands.impl.ExcelFileProcessorCommand;
import com.angel.io.processors.runners.imports.impl.ImportFileAnnotationProcessorRunner;
import com.angel.io.processors.runners.imports.impl.ImportFileProcessorRunner;

/**
 * @author Guillermo Salazar
 * @since 27/Agosto/2009
 *
 */
@Generator(
		objectClass = Proyecto.class,
		dependencies = {PersonaAnnotationDataGenerator.class, TagSearchAnnotationDataGenerator.class},
		daoName = "proyectoDAO",
		pages = {}
)
public class ProyectoAnnotationDataGenerator {

	@ImportFileProcessorRunnerBuilder(fileProcessorDescriptor = FileProcessorDescriptor.class, name = "Importacion de Proyectos")
    public ImportFileProcessorRunner prepareImportFileProcessorRunner(FileProcessorDescriptor fileProcessorDescriptor) {
		ImportFileAnnotationProcessorRunner a = new ImportFileAnnotationProcessorRunner(fileProcessorDescriptor, new ExcelFileProcessorCommand(), new ProyectoAnnotationRowProcessorCommand());
        return a;
    }

    @InputStreamBuilder
    public InputStream prepareInputStream() {
        try {
            return FileHelper.findInputStreamInClasspath(ResourceLocation.PROYECTOS_EXCEL_FILE);
        } catch (FileNotFoundException e) {
            throw new DataGeneratorException("File not found [" + ResourceLocation.PROYECTOS_EXCEL_FILE + "].", e);
        }
    }
	
}
