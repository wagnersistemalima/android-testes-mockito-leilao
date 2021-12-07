package br.com.alura.leilao.adapter;

import static org.hamcrest.core.Is.is;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoAdapterTest {

    // objeto mockado / mockito espiao contexto android
    @Mock
    private Context context;

    // objeto mockado / mockito espiao contexto android
    @Spy
    private ListaLeilaoAdapter listaLeilaoAdapter = new ListaLeilaoAdapter(context);


    @Test
    public void deveAtualizarListaDeLeiloes_quandoReceberUmaListaDeLeiloes() {
        // cenario

        // comportamento do objeto mockado, não faça nada quando chamar o metodo
        Mockito.doNothing().when(listaLeilaoAdapter).atualizaLista();

        listaLeilaoAdapter.atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("Carro"),
                new Leilao("Casa")
        )));

        int quantDeLeiloesDevolvidas = listaLeilaoAdapter.getItemCount();

        // verifica se o metodo atualiza lista foi chamado
        Mockito.verify(listaLeilaoAdapter, Mockito.times(1)).atualizaLista();

        // verifica se a quantidade de leioloes devolvida foi 2
        Assert.assertThat(quantDeLeiloesDevolvidas, is(2));


    }
}
