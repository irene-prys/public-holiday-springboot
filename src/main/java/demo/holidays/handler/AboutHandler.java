package demo.holidays.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class AboutHandler implements IntentRequestHandler {

    private final String ABOUT_MESSAGE = "This application gives the list of public Ukrainian holidays in a certain month. " +
            "WHen month is not specified it returns the data for August. The all data are valid for 2019.";

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(intentName("AboutIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {

        return handlerInput.getResponseBuilder()
                .withSpeech(ABOUT_MESSAGE)
                .build();
    }

}

