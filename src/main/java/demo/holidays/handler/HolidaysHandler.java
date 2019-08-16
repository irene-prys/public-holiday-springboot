package demo.holidays.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.RequestHelper;
import demo.holidays.utils.HolidayGenerator;


import java.text.MessageFormat;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class HolidaysHandler implements IntentRequestHandler {

    private final String FIRST_PART_OF_MESSAGE = "We have the next public holidays this year in ";
    private final String HOLIDAYS_DEFAULT_MESSAGE = "This year in August it's gonna be: '{0}'";
    private final String DEFAULT_MONTH = "aug";

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(intentName("PublicHolidaysIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        RequestHelper requestHelper = RequestHelper.forHandlerInput(handlerInput);

        Optional<String> month = requestHelper.getSlotValue("month");

        String speechText = month.map(m -> FIRST_PART_OF_MESSAGE + m + ": " + HolidayGenerator.getHolidays(m.substring(0, 3)))
                .orElse(getDefaultContent());

        return handlerInput.getResponseBuilder()
                .withSpeech("Spring Boot: " + speechText)
                .build();
    }

    private String getDefaultContent() {
        return MessageFormat.format(HOLIDAYS_DEFAULT_MESSAGE, HolidayGenerator.getHolidays(DEFAULT_MONTH));
    }

}


