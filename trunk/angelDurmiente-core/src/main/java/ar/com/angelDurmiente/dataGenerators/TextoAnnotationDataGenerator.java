/**
 * 
 */
package ar.com.angelDurmiente.dataGenerators;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.helpers.ResourceLocation;
import ar.com.angelDurmiente.rowProcessors.TextoAnnotationRowProcessorCommand;

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
		objectClass = Cancion.class,
		dependencies = {
			UsuarioAnnotationDataGenerator.class,
			CancionAnnotationDataGenerator.class,
			ArtistaAnnotationDataGenerator.class
		},
		daoName = "cancionDAO",
		pages = {}
)
public class TextoAnnotationDataGenerator {

	@ImportFileProcessorRunnerBuilder(fileProcessorDescriptor = FileProcessorDescriptor.class, name = "Importacion de Textos de Canciones")
    public ImportFileProcessorRunner prepareImportFileProcessorRunner(FileProcessorDescriptor fileProcessorDescriptor) {
		ImportFileAnnotationProcessorRunner a = new ImportFileAnnotationProcessorRunner(fileProcessorDescriptor, new ExcelFileProcessorCommand(), new TextoAnnotationRowProcessorCommand());
        return a;
    }

    @InputStreamBuilder
    public InputStream prepareInputStream() {
        try {
            return FileHelper.findInputStreamInClasspath(ResourceLocation.TEXTOS_CANCIONES_INIT_DATA_EXCEL_FILE);
        } catch (FileNotFoundException e) {
            throw new DataGeneratorException("File not found [" + ResourceLocation.TEXTOS_CANCIONES_INIT_DATA_EXCEL_FILE + "].", e);
        }
    }
	
}
