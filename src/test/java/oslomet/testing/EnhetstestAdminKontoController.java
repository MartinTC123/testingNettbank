package oslomet.testing;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    //denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    //denne skal Mock'es
    private AdminRepository repository;

    @Mock
    //denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_LoggetInn(){
        //Arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentAlleKonti()).thenReturn(konti);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertEquals(konti,resultat);

    }
    @Test
    public void hentAlleKonti_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat =adminKontoController.hentAlleKonti();

        assertNull(resultat);

    }
    @Test
    public void registrerKonto_LoggetInn(){
        //arrange
        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerKonto(any(Konto.class))).thenReturn("OK");

        //act
        String resultat = adminKontoController.registrerKonto(konto);

        //assert
        assertEquals("OK",resultat);


    }
    @Test
    public void registrerKonto_IkkeLoggetInn(){
        //arrange
        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.registrerKonto(konto);

        //assert
        assertEquals("Ikke innlogget",resultat);

    }
    @Test
    public void endreKonto_LoggetInn(){
        //arrange
        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");

        //act
        String resultat = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("OK",resultat);

    }
    @Test
    public void endreKonto_IkkeLoggetInn(){
        //arrange
        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("Ikke innlogget", resultat);

    }
    @Test
    public void slettKonto_LoggetInn(){
        //arrange
        String kontonummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKonto(anyString())).thenReturn("OK");

        //act
        String resultat = adminKontoController.slettKonto(kontonummer);

        //assert
        assertEquals("ok",resultat);

    }
    @Test
    public void slettKonto_IkkeLoggetInn(){
        //arrange
        String kontonummer= "01010110523";

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.slettKonto(kontonummer);

        //assert
        assertEquals("Ikke innlogget",resultat);

    }


}
