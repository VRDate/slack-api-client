package io.slack.api.client;

import io.slack.api.client.invoker.JSON;
import io.slack.api.client.model.AppRateLimited;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * API tests for EventHandler
 */
public class EventHandlerUnitTest {

    private EventHandler eventHandler;

    @Mock
    private EventVisitor eventVisitorMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        eventHandler = new EventHandler(eventVisitorMock, new JSON());
    }

    @Test
    public void shouldParseEventAndInvokeVisitor() {
        //given: receive the following payload
        String payload = "{\n" +
                "    \"token\": \"Jhj5dZrVaK7ZwHHjRyZWjbDl\",\n" +
                "    \"type\": \"app_rate_limited\",\n" +
                "    \"team_id\": \"T123456\",\n" +
                "    \"minute_rate_limited\": 1518467820,\n" +
                "    \"api_app_id\": \"A123456\"\n" +
                "}";
        doNothing().when(eventVisitorMock).visit(any(AppRateLimited.class));
        //when: parse the payload
        eventHandler.handleEvent(payload);
        //then: an event object should be created
        verify(eventVisitorMock, times(1)).visit(any(AppRateLimited.class));
    }
}