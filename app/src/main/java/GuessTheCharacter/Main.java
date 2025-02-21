package GuessTheCharacter;

import GuessTheCharacter.DAO.CharacterDAO;
import GuessTheCharacter.Util.ConfigProvider;
import GuessTheCharacter.model.CharacterGTC;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
    
    public static List<CharacterGTC> personajes = new ArrayList<>();
    public static List<String> nombresDisponibles = new ArrayList<>();
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        cargarPersonajes(stage);        
    }
    
    private boolean cargarPersonajes(Stage stage) {
        CharacterDAO characterDAO = new CharacterDAO();
        characterDAO.cargarTodosPersonajes().thenAccept(personajesCargados -> {
            this.personajes.addAll(personajesCargados);
            System.out.println("Personajes cargados: " + personajes.size());
            Collections.shuffle(this.personajes);
            cargarNombres();
            iniciarApp(stage);
        }).exceptionally(t -> {
            System.err.println("Error al cargar personajes: " + t.getMessage());
            return null;
        });
        return !personajes.isEmpty();
    }
    
    private void cargarNombres() {
        List<String> todosNombres = new ArrayList<>();
        GuessTheCharacter.Main.personajes.forEach(personaje -> {
            todosNombres.add(personaje.getName());
            if (personaje.getAliasesList() != null && !personaje.getAliasesList().isEmpty()) {
                personaje.getAliasesList().forEach((alias) -> {
                    todosNombres.add(alias);
                });
            }
        });
        Set<String> set = new HashSet<>(todosNombres);
        nombresDisponibles.addAll(set);
        System.out.println("Nombres cargados: " + nombresDisponibles.size());
    }
    
    private void iniciarApp(Stage stage) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/views/frmMainApp.fxml"))));
            stage.setResizable(false);
            stage.setTitle("GUESS THE CHARACTER");
            stage.getIcons().add(new Image("/media/logo.jpg"));
            stage.show();
        } catch (IOException ex) {
            System.err.println("Error in " + this.getClass().toString() + " cargando Main App archivo fxml");
            System.err.println(ex.getMessage());
        }
    }
    
    //Para debug (no debería ir a producción)
    private void createConfigProperties() {
        new ConfigProvider().createConfigProperties("389971aab6c71dd0973d3b1d2b5792758ac848ce");
    }
}
