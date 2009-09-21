/**
 * 
 */
package ar.com.gsalazar.dataGenerators;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ar.com.gsalazar.beans.ContactoWeb;
import ar.com.gsalazar.resources.ResourceLocation;
import ar.com.gsalazar.rowProcessors.ContactoWebAnnotationRowProcessorCommand;

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
		objectClass = ContactoWeb.class,
		dependencies = {},
		daoName = "contactoWebDAO",
		pages = {}
)
public class ContactoWebAnnotationDataGenerator {

	@ImportFileProcessorRunnerBuilder(fileProcessorDescriptor = FileProcessorDescriptor.class, name = "Importacion de Web Amigas")
    public ImportFileProcessorRunner prepareImportFileProcessorRunner(FileProcessorDescriptor fileProcessorDescriptor) {
		ImportFileAnnotationProcessorRunner a = new ImportFileAnnotationProcessorRunner(fileProcessorDescriptor, new ExcelFileProcessorCommand(), new ContactoWebAnnotationRowProcessorCommand());
        return a;
    }

    @InputStreamBuilder
    public InputStream prepareInputStream() {
        try {
            return FileHelper.findInputStreamInClasspath(ResourceLocation.CONTACTO_WEB_EXCEL_FILE);
        } catch (FileNotFoundException e) {
            throw new DataGeneratorException("File not found [" + ResourceLocation.CONTACTO_WEB_EXCEL_FILE + "].", e);
        }
    }
	
}
