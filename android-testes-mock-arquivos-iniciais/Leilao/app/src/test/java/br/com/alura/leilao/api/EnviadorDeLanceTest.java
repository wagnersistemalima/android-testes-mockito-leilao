package br.com.alura.leilao.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

@RunWith(MockitoJUnitRunner.class)    // iniciar todos os mocks
public class EnviadorDeLanceTest {

    // objetos mockados

    @Mock
    private Leilao leilao;

    @Mock
    private LeilaoWebClient client;

    @Mock
    private EnviadorDeLance.LanceProcessadoListener listener;

    @Mock
    private AvisoDialogManager manager;

    Usuario alex = new Usuario("Alex");

    @Test
    public void deveLancarMensagemDeFalha_QuandoLancarUmLanceMenorDoQueUltimoLance() {

        // cenario de um leilao

        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(
                client,
                listener,
                manager
        );

        // comportamento:
        Mockito.doThrow(LanceMenorQueUltimoLanceException.class).when(leilao).propoe(ArgumentMatchers.<Lance>any(Lance.class));

        // Ação

        enviadorDeLance.envia(leilao, new Lance(new Usuario("Joao"), 100));

        // assertivas

        Mockito.verify(manager).mostraAvisoLanceMenorQueUltimoLance();

    }

    @Test
    public void deveMostrarMensagemDeFalhaQuando_UsuarioComCincoLanceDerNovoLance() {

        // cenario de um leilao

        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(
                client,
                listener,
                manager
        );

        // comportamento: para nao ter que criar lances consecutivos para lançar exceção
        Mockito.doThrow(UsuarioJaDeuCincoLancesException.class).when(leilao).propoe(ArgumentMatchers.<Lance>any(Lance.class));

        // ação
        enviadorDeLance.envia(leilao, new Lance(alex, 200));

        Mockito.verify(manager).mostraAvisoUsuarioJaDeuCincoLances();
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoOUsuarioDoUltimoLanceDerNovoLance(){
        // cenario
        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(
                client,
                listener,
                manager
        );

        // comportamento para não ter que criar lances consecutivos
        Mockito.doThrow(LanceSeguidoDoMesmoUsuarioException.class).when(leilao).propoe(ArgumentMatchers.<Lance>any(Lance.class));

        // ação
        enviadorDeLance.envia(leilao, new Lance(alex, 200));

        // assertivas

        Mockito.verify(manager).mostraAvisoLanceSeguidoDoMesmoUsuario();
    }

}