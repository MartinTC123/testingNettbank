package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Equals;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    private AdminKundeController kundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sikkerhet;

    @Test
    public void test_lagreKundeOK(){
        Kunde kunde1 = new Kunde("123456789", "lene", "jensen",
                "osloveien 82","3270",
                "asker","22224444","lene123");

        when(sikkerhet.loggetInn()).thenReturn("123456789");

        when(repository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        String melding = kundeController.lagreKunde(kunde1);

        assertEquals("OK",melding);
    }

    @Test
    public void test_lagreKundeFeil(){
        Kunde kunde1 = new Kunde("123456789", "lene", "jensen",
                "osloveien 82","3270",
                "asker","22224444","lene123");

        when(sikkerhet.loggetInn()).thenReturn(null);

        String melding = kundeController.lagreKunde(kunde1);

        assertEquals("Ikke logget inn",melding);
    }

    @Test
    public void hentAlle_LoggetInn(){
        List<Kunde> kunder = new ArrayList<>();
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde kunde2 = new Kunde("01010110524",
                "Martin", "Jensen", "Askerveien 22", "3270",
                "Asker", "33338888", "HeiHei");

        kunder.add(kunde1);
        kunder.add(kunde2);

        when(sikkerhet.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKunder()).thenReturn(kunder);

        List<Kunde> melding = kundeController.hentAlle();

        assertEquals(kunder, melding);
    }

    @Test
    public void hentAlle_IkkeLoggetInn(){
        when(sikkerhet.loggetInn()).thenReturn(null);

        List<Kunde> melding = kundeController.hentAlle();

        assertEquals(null, melding);

    }

    @Test
    public void endre_LoggetInn(){
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        when(sikkerhet.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String melding = kundeController.endre(kunde1);

        assertEquals("OK", melding);
    }

    @Test
    public void endre_IkkeLoggetInn() {
        //arrange
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sikkerhet.loggetInn()).thenReturn(null);

        //act
        String melding = kundeController.endre(kunde1);

        //assert
        assertEquals("Ikke logget inn", melding);
    }

    @Test
    public void slett_LoggetInn() {

        String personnummer = "01010110523";

        when(sikkerhet.loggetInn()).thenReturn("01010110523");

        when(repository.slettKunde(anyString())).thenReturn("OK");

        String melding = kundeController.slett("01010110523");

        assertEquals("OK", melding);
    }

    @Test
    public void slett_IkkeLoggetInn() {
        //arrange
        String personnummer = "01010110523";

        when(sikkerhet.loggetInn()).thenReturn(null);


        String melding = kundeController.slett("01010110523");

        //assert
        assertEquals("Ikke logget inn", melding);
    }

}
