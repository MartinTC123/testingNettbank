package oslomet.testing;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;


import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhetsController {
    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private BankRepository repository;

    @Mock
    private HttpSession httpSession;

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
        // arrange

        when(httpSession.getAttribute(anyString())).thenReturn("Innlogget");
        // act

        String resultat = sikkerhet.loggetInn();
        // assert

        assertEquals("Innlogget", resultat);
    }

    @Test
    public void loggetInn_null(){
        // arrange

        when(httpSession.getAttribute(anyString())).thenReturn(null);
        // act

        String resultat = sikkerhet.loggetInn();
        // assert

        assertNull(resultat);
    }
}
