package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_loggetInn(){
        // arrange

        Transaksjon transaksjon1= new Transaksjon(1, "01000000", 700.0, "2021-01-01", "Penger sendt", null, "02000000");
        Transaksjon transaksjon2= new Transaksjon(2, "02000000", 700.0, "2021-01-01", "Penger sendt tilbake", null, "01000000");
        List<Transaksjon> transaksjonList = new ArrayList<>();
        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        Konto konto1= new Konto("01010112345", "01000000", 1500.0, "Brukskonto", "NOK", transaksjonList);

        when(sjekk.loggetInn()).thenReturn("01010112345");
        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(konto1);
        // act

        Konto resultat = bankController.hentTransaksjoner("01000000","2021-01-01","2100-01-01");

        // assert
        assertEquals(konto1,resultat);
    }

    @Test
    public void hentTransaksjoner_ikkeloggetInn(){
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);
        // act

        Konto resultat = bankController.hentTransaksjoner("","","");
        // assert

        assertNull(resultat);
    }

    @Test
    public void hentSaldi_loggetInn(){
        // arrange

        List<Konto> kontoList = new ArrayList<>();
        Konto konto1= new Konto("11139931758", "01010110523", 11660.0, "Brukskonto", "NOK",null);
        Konto konto2= new Konto("11139931758", "01310111678", 10.0, "Brukskonto", "NOK",null);

        kontoList.add(konto1);
        kontoList.add(konto2);

        when(sjekk.loggetInn()).thenReturn("11139931758");
        when(repository.hentSaldi(anyString())).thenReturn(kontoList);
        // act

        List<Konto> resultat = bankController.hentSaldi();

        // assert

        assertEquals(kontoList, resultat);
    }

    @Test
    public void hentSaldi_ikkeLoggetInn(){
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);
        // act

        List<Konto> resultat = bankController.hentSaldi();

        // assert

        assertNull(resultat);
    }
}

