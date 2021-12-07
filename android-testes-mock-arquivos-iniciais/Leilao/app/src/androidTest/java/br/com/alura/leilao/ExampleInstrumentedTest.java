package br.com.alura.leilao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        ListaLeilaoAdapter listaLeilaoAdapter = new ListaLeilaoAdapter(appContext);

        listaLeilaoAdapter.atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("Carro"),
                new Leilao("Casa")
        )));

        int quantDeLeiloesDevolvidas = listaLeilaoAdapter.getItemCount();

        Assert.assertThat(quantDeLeiloesDevolvidas, is(2));

    }
}
