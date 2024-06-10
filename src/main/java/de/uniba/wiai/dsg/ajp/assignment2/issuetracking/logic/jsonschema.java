package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class jsonschema {
    public void applyschema(String path){
        ObjectMapper objectMapper = new ObjectMapper();

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        JsonSchema jsonSchema = null;
        try {
            jsonSchema = factory.getSchema(Files.readString(Path.of("src/main/resources/schema.json")));
        } catch (IOException e) {
            System.err.println("Error while reading Schema" + e);
            return;
        }
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(Files.readString(Path.of(path)));
        } catch (IOException e) {
            System.err.println("Error while reading File");
            return;
        }
        Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
        if(!errors.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for(ValidationMessage message :errors){
                errorMessages.add(message.getMessage());
            }
            System.err.println("Validition failed for the following reason(s): " + String.join("\n",errorMessages));
            return;
        }

    }
}
