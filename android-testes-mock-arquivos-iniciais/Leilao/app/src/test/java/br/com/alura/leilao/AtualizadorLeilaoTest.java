package br.com.alura.leilao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.AtualizadorDeLeiloes;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorLeilaoTest {

    @Mock
    private AtualizadorDeLeiloes.ErroCarregaLeiloesListen listener;

    @Mock
    private ListaLeilaoAdapter adapter;

    // objeto que representa o server, onde estao armazenado as listas de leioloes backend
    @Mock
    private LeilaoWebClient client = new LeilaoWebClient();

    @Test
    public void deveAtualizarListaDeLeioloes_QuandoBuscarListaDaApi() {
        // cenario

        AtualizadorDeLeiloes atualizadorDeLeiloes = new AtualizadorDeLeiloes();

        // Tem a capacidade de pegar atributos de metodos que estamos mockando
        // e modificar comportamentos especificos sucess
        Mockito.doAnswer(new Answer() {

            // espera uma implementação de uma interfacer Anser()
            @Override
            public Object answer(InvocationOnMock invocationOnMock) {
                // indice do argumento que seria client.todos()
                RespostaListener<List<Leilao>> argument = invocationOnMock.getArgument(0);
                argument.sucesso(
                        new ArrayList<Leilao>(Arrays.asList(
                                new Leilao("Carro"),
                                new Leilao("Computador")
                        )));
                return null;
            }
        }).when(client).todos(ArgumentMatchers.any(RespostaListener.class));

        atualizadorDeLeiloes.buscaLeiloes(adapter, client, listener);

        // assertivas

        // verifica se foi chamado o metodos todos
        Mockito.verify(client).todos(ArgumentMatchers.any(RespostaListener.class));

        Mockito.verify(adapter).atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("Carro"),
                new Leilao("Computador")
        )));
    }

    @Test
    public void deveApresentarFalha_quandoTentarBuscarListaDeLeioloes() {
        // cenario

        AtualizadorDeLeiloes atualizadorDeLeiloes = new AtualizadorDeLeiloes();

        // Tem a capacidade de pegar atributos de metodos que estamos mockando
        // e modificar comportamentos especificos falha
        Mockito.doAnswer(new Answer() {

            // espera uma implementação de uma interfacer Anser()
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                // indice do argumento que seria client.todos()
                RespostaListener<List<Leilao>> argument = invocationOnMock.getArgument(0);
                argument.falha(Mockito.anyString());

                return null;
            }
        }).when(client).todos(Mockito.any(RespostaListener.class));

        // ação

        atualizadorDeLeiloes.buscaLeiloes(adapter, client, listener);

        // assertivas

        Mockito.verify(listener).errorAoCarregar(Mockito.anyString());
    }
}
