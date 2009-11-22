/**
 * 
 */
package ar.com.angelDurmiente.dataGenerators;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.helpers.ResourceLocation;
import ar.com.angelDurmiente.rowProcessors.AlbumCancionAnnotationRowProcessorCommand;

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
		objectClass = Album.class,
		dependencies = {
			AlbumAnnotationDataGenerator.class,
			CancionAnnotationDataGenerator.class
		},
		daoName = "albumDAO",
		pages = {}
)
public class AlbumCancionAnnotationDataGenerator {

	@ImportFileProcessorRunnerBuilder(fileProcessorDescriptor = FileProcessorDescriptor.class, name = "Importacion de Albums de Canciones")
    public ImportFileProcessorRunner prepareImportFileProcessorRunner(FileProcessorDescriptor fileProcessorDescriptor) {
		ImportFileAnnotationProcessorRunner a = new ImportFileAnnotationProcessorRunner(fileProcessorDescriptor, new ExcelFileProcessorCommand(), new AlbumCancionAnnotationRowProcessorCommand());
        return a;
    }

    @InputStreamBuilder
    public InputStream prepareInputStream() {
        try {
            return FileHelper.findInputStreamInClasspath(ResourceLocation.ALBUMS_CANCIONES_INIT_DATA_EXCEL_FILE);
        } catch (FileNotFoundException e) {
            throw new DataGeneratorException("File not found [" + ResourceLocation.ALBUMS_CANCIONES_INIT_DATA_EXCEL_FILE + "].", e);
        }
    }
	
}
