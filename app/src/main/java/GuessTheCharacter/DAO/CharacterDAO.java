package GuessTheCharacter.DAO;

import GuessTheCharacter.Util.ConfigProvider;
import GuessTheCharacter.DAO.InterfacesDAO.ReadCharacterService;
import GuessTheCharacter.model.CharacterGTC;
import GuessTheCharacter.model.ResultCharactersGTC;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterDAO {
    private List<CharacterGTC> personajes = new ArrayList<>();
    private ResultCharactersGTC resultado;

    private ReadCharacterService readCharacterService;
    private Call<ResultCharactersGTC> callRead;

    public CharacterDAO() {
        String url = "https://www.giantbomb.com/api/";
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        readCharacterService = retrofit.create(ReadCharacterService.class);
    }

    public CompletableFuture<List<CharacterGTC>> cargarTodosPersonajes() {
        String format = "json";
        String API_KEY = new ConfigProvider().loadApiKey();
        CompletableFuture<List<CharacterGTC>> future = new CompletableFuture<>();
        callRead = readCharacterService.getCharacters(API_KEY, format);
        enqueueRead(future);

        return future;
    }

    private void enqueueRead(CompletableFuture<List<CharacterGTC>> future) {
        callRead.enqueue(new Callback<ResultCharactersGTC>() {
            @Override
            public void onFailure(Call<ResultCharactersGTC> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
                future.completeExceptionally(t);
            }

            @Override
            public void onResponse(Call<ResultCharactersGTC> call, Response<ResultCharactersGTC> response) {
                Platform.runLater(() -> {
                    System.out.println("---Response READ ALL:" + response + "---");
                    System.out.println("HTTP status: " + response.message());
                    if (response.isSuccessful() && response.body() != null) {
                        resultado = response.body();
                        personajes.clear();
                        personajes.addAll(resultado.results());
                        future.complete(personajes);
                    } else {
                        System.err.println("HTML ERROR: " + response.code());
                        resultado = null;
                        future.complete(new ArrayList<>());
                    }
                });
            }
        });
    }
}