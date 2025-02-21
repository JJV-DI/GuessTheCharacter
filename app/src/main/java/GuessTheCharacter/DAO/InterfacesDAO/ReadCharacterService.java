package GuessTheCharacter.DAO.InterfacesDAO;

import GuessTheCharacter.model.ResultCharactersGTC;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.concurrent.CompletableFuture;
import retrofit2.Call;

public interface ReadCharacterService {
    @GET("characters/")
    Call<ResultCharactersGTC> getCharacters(
            @Query("api_key") String apiKey,
            @Query("format") String format
    );
    
}