package es.bcn.imi.framework.vigia.orquestrador.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import es.bcn.imi.framework.vigia.orquestrador.test.parent.RestServerParentTest;
import net.opentrends.openframe.services.rest.apidocs.filegeneration.ApiDocsFilesGenerator;

/**
 * The Class SwaggerFilesGenerationTest.
 */
public class SwaggerFilesGenerationTest extends RestServerParentTest {
	private final String SWAGGER_FILE_PATH = "target/swagger-files/";
	@Autowired
	@Qualifier("apiDocsFilesGenerator")
	private ApiDocsFilesGenerator apiDocsFilesGenerator;

	@Value("${rest.apidocs.swagger.apiInfo.basePath}")
	private String basePath;

	@Test
	public void testGenerateApiDocumentationFiles() throws Exception {
		apiDocsFilesGenerator.generateApiDocumentationFiles();
		addPath();
		return;
	}

	private void addPath() throws IOException {
		File jsonFile = getJsonFile();
		BufferedReader bf = new BufferedReader(new FileReader(jsonFile));
		String linea;
		StringBuilder buffer = new StringBuilder();
		while ((linea = bf.readLine()) != null) {
			if (linea.contains("basePath")) {
				linea = "\"basePath\":\"" + basePath + "\",";
			}
			buffer.append(linea);
		}

		bf.close();
		JsonNode json = new ObjectMapper().readTree(buffer.toString());
		BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));
		writer.write(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json));
		writer.flush();
		writer.close();

		BufferedWriter writerYaml = new BufferedWriter(
				new FileWriter(Paths.get(jsonFile.getAbsolutePath().replace(".json", ".yaml")).toFile()));
		writerYaml.write(new YAMLMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json));
		writerYaml.flush();
		writerYaml.close();
	}

	private File getJsonFile() throws IOException {
		return Paths.get(SWAGGER_FILE_PATH + getJsonFileName()).toFile();
	}

	private String getJsonFileName() throws IOException {
		String[] names = Paths.get(SWAGGER_FILE_PATH).toFile().list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().endsWith(".json"))
					return true;
				return false;
			}
		});

		if (names.length != 1)
			throw new IOException("File not found");

		return names[0];
	}
}