package GuessTheCharacter.controllers;

import ComponentesGTC.ContadorGTC;
import ComponentesGTC.GuessFieldGTC;
import ComponentesGTC.GuessedListGTC;
import ComponentesGTC.HintButtonGTC;
import ComponentesGTC.ImageViewGTC;
import GuessTheCharacter.Main;
import GuessTheCharacter.model.CharacterGTC;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class MainAppController implements Initializable {
    
    private CharacterGTC personajeActual;
    
    private List<String> pistas;
    
    @FXML
    private Button btnSiguiente;

    @FXML
    private ContadorGTC contadorGTCRacha;

    @FXML
    private ContadorGTC contadorGTCVidas;

    @FXML
    private GuessFieldGTC guessFieldGTC;

    @FXML
    private GuessedListGTC guessListGTC;

    @FXML
    private HintButtonGTC hintButtonGTC;

    @FXML
    private ImageViewGTC imgGTC;

    @FXML
    private Label lblFallos;

    @FXML
    private Label lblNombres;

    @FXML
    private Label lblSoluciónCorrecta;

    @FXML
    private Label lblVidasRestantesPista;

    @FXML
    void btnRendirsePulsado(ActionEvent event) {
        imgGTC.decrecerBlur();
    }

    @FXML
    void btnSiguientePulsado(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.pistas = new ArrayList<>();
        this.personajeActual = Main.personajes.getFirst();
        lblSoluciónCorrecta.setVisible(false);
        lblNombres.setVisible(false);
        contadorGTCRacha.setContador(0);
        guessFieldGTC.setPalabras(Main.nombresDisponibles);
        iniciarDatosPersonaje();
    }
    
    private void iniciarDatosPersonaje() {
        imgGTC.setImage(new Image(personajeActual.getImage().medium_url()));
        imgGTC.setBlurFactor(40);
        contadorGTCVidas.setContador(20);
        StringBuilder sb = new StringBuilder(personajeActual.getName());
        personajeActual.getAliasesList().forEach(alias -> {
            sb.append("/"+alias);
        });
        lblNombres.setText(sb.toString());
    }

}
