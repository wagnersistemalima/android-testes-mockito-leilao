package br.com.alura.leilao;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.AtualizadorDeUsuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

@RunWith(MockitoJUnitRunner.class)
public class AtualizaLeilaoUsuarioTest {

    @Mock
    private UsuarioDAO dao;

    @Mock
    private ListaUsuarioAdapter adapter;

    @Mock
    private RecyclerView recyclerView;

    @Test
    public void deveAtualizarListaDeUsuario_quandoSalvarUsuario() {
        // cenario

        AtualizadorDeUsuario atualizadorDeUsuario = new AtualizadorDeUsuario(dao, adapter, recyclerView);

        Usuario joao = new Usuario("Joao");

        // ação

        // comportamento retorna um novo usuario salvo com id e nome
        Mockito.when(dao.salva(joao)).thenReturn(new Usuario(1, "Joao"));

        // comportamento
        Mockito.when(adapter.getItemCount()).thenReturn(1);

        atualizadorDeUsuario.salva(joao);

        // assertivas

        // verifica se foi salvo um usuario com o nome Joao
        Mockito.verify(dao).salva(new Usuario("Joao"));

        // verifica se foi adicionado o usuario com id 1 e nome Joao
        Mockito.verify(adapter).adiciona(new Usuario(1, "Joao"));
        Mockito.verify(recyclerView).smoothScrollToPosition(0);

    }
}
