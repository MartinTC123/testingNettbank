package oslomet.testing;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Equals;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        String resultat = kundeController.lagreKunde(kunde1);

        assertEquals("OK",resultat);
    }

    @Test
    public void test_lagreKundeFeil(){
        Kunde kunde1 = new Kunde("123456789", "lene", "jensen",
                "osloveien 82","3270",
                "asker","22224444","lene123");

        when(sikkerhet.loggetInn()).thenReturn(null);

        String resultat = kundeController.lagreKunde(kunde1);

        assertEquals("Ikke logget inn",resultat);
    }
}
