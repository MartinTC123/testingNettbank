package oslomet.testing;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;


import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhetsController {
    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private BankRepository repository;

    @Mock
    private MockHttpSession httpSession;

    @Test
    public void sjekkLoggInn_OK(){
        // arrange

        when(repository.sjekkLoggInn("01010110523","HeiHei")).thenReturn("OK");
        // act

        String resultat = sikkerhet.sjekkLoggInn("01010110523", "HeiHei");
        // assert

        assertEquals("OK", resultat);
    }

    @Test
    public void sjekkLoggInn_feilPersonnr(){
        // arrange

        // act

        String resultat = sikkerhet.sjekkLoggInn("0101011052", "Hei1234");
        // assert

        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void sjekkLoggInn_feilPassord(){
        // arrange

        // act

        String resultat = sikkerhet.sjekkLoggInn("01010110523", "Hei");
        // assert

        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void sjekkLoggInn_Feil(){
        // arrange

        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i personnummer eller passord");
        // act

        String resultat = repository.sjekkLoggInn("01010110523", "HeiHei");
        // assert

        assertEquals("Feil i personnummer eller passord", resultat);
    }

    @Test
    public void loggUt(){
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        }).when(httpSession).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(httpSession).setAttribute(anyString(), any());
        // arrange

        // act
        sikkerhet.loggUt();

        // assert
        assertNull(httpSession.getAttribute("Innlogget"));
    }

    @Test
    public void loggInnAdmin_loggetInn(){
        // arrange

        when(sikkerhet.loggInnAdmin(anyString(),anyString())).thenReturn("Logget inn");
        // act

        String resultat = sikkerhet.loggInnAdmin("Per", "Hei1234");

        // assert
        assertEquals("Logget inn", resultat);
    }

    @Test
    public void loggInnAdmin_ikkeLoggetInn(){
        // arrange

        when(sikkerhet.loggInnAdmin(anyString(), anyString())).thenReturn("Ikke logget inn");
        // act

        String resultat = sikkerhet.loggInnAdmin("Per", "Hei1234");

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void loggetInn_innlogget(){
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        }).when(httpSession).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(httpSession).setAttribute(anyString(), any());
        // arrange
        httpSession.setAttribute("Innlogget", "01010110523");

        // act

        String resultat = sikkerhet.loggetInn();
        // assert

        assertEquals("01010110523", resultat);
    }

    @Test
    public void loggetInn_null(){
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        }).when(httpSession).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(httpSession).setAttribute(anyString(), any());
        // arrange
        httpSession.setAttribute(null,null);
        // act

        String resultat = sikkerhet.loggetInn();
        // assert

        assertNull(resultat);
    }
}
