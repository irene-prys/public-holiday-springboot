package demo.holidays;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.services.Serializer;
import com.amazon.ask.servlet.util.ServletUtils;
import com.amazon.ask.servlet.verifiers.AlexaHttpRequest;
import com.amazon.ask.servlet.verifiers.ServletRequest;
import com.amazon.ask.servlet.verifiers.SkillRequestSignatureVerifier;
import com.amazon.ask.servlet.verifiers.SkillRequestTimestampVerifier;
import com.amazon.ask.util.JacksonSerializer;
import demo.holidays.handler.AboutHandler;
import demo.holidays.handler.HolidaysHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class HolidaysController {

    private final SkillRequestSignatureVerifier signatureVerifier;
    private final transient Serializer serializer;
    private final SkillRequestTimestampVerifier timestampVerifier;
    private final transient Skill skill;

    public HolidaysController() {
        this.signatureVerifier = new SkillRequestSignatureVerifier();
        this.serializer = new JacksonSerializer();
        Long timestampToleranceProperty = ServletUtils.getTimeStampToleranceSystemProperty();
        this.timestampVerifier = new SkillRequestTimestampVerifier(timestampToleranceProperty != null ? timestampToleranceProperty : 30000L);
        this.skill = getSkill();
    }

    @PostMapping(value = "/public-holidays/main", consumes = "application/json")
    public ResponseEnvelope handleAlexaRequest(HttpServletRequest request) throws IOException {
        InputStream is = request.getInputStream();
        byte[] serializedRequestEnvelope = IOUtils.toByteArray(is);
        RequestEnvelope deserializedRequestEnvelope = (RequestEnvelope) this.serializer.deserialize(IOUtils.toString(serializedRequestEnvelope, "UTF-8"), RequestEnvelope.class);
        AlexaHttpRequest alexaHttpRequest = new ServletRequest(request, serializedRequestEnvelope, deserializedRequestEnvelope);
        signatureVerifier.verify(alexaHttpRequest);
        timestampVerifier.verify(alexaHttpRequest);

        ResponseEnvelope skillResponse = this.skill.invoke(deserializedRequestEnvelope);


        return skillResponse;
    }

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new HolidaysHandler(),
                        new AboutHandler())
                .build();
    }

}
