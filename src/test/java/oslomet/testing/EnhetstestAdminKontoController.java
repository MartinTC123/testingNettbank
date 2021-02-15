package oslomet.testing;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

public class EnhetstestAdminKontoController {
    @InjectMocks
    private AdminKundeController kundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sikkerhet;

    @Test
    public void hentAlleKonti_LoggetInn(){

    }
    @Test
    public void hentAlleKonti_IkkeLoggetInn(){

    }
    @Test
    public void registrerKonto_LoggetInn(){

    }
    @Test
    public void registrerKonto_IkkeLoggetInn(){

    }
    @Test
    public void endreKonto_LoggetInn(){

    }
    @Test
    public void endreKonto_IkkeLoggetInn(){

    }
    @Test
    public void slettKonto_LoggetInn(){

    }
    @Test
    public void slettKonto_IkkeLoggetInn(){

    }


}
