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
    
    private int fallos = 0;
    
    private int intentosHastaPista = 5;
    
    @FXML
    private Button btnRendirse;
    
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
    void btnRendirsePulsado() {
        perderRonda();
    }

    @FXML
    void btnSiguientePulsado() {
        iniciarSiguienteRonda();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hintButtonGTC.getTextArea().setWrapText(true);
        this.personajeActual = Main.personajes.getFirst();
        lblSoluciónCorrecta.setVisible(false);
        lblNombres.setVisible(false);
        contadorGTCRacha.setContador(0);
        guessFieldGTC.setPalabras(Main.nombresDisponibles);
        rellenarDatosPersonaje();
        inicializarGuessFieldGTC();
    }
    
    private void rellenarDatosPersonaje() {
        imgGTC.setImage(new Image(personajeActual.getImage().medium_url()));
        imgGTC.setFitHeight(220);
        imgGTC.setFitWidth(220);
        imgGTC.setBlurFactor(40);
        StringBuilder sb = new StringBuilder(personajeActual.getName());
        personajeActual.getAliasesList().forEach(alias -> {
            sb.append("/"+alias);
        });
        System.out.println("Nombre: "+sb.toString());
        lblNombres.setText(sb.toString());
    }
    
    private void inicializarGuessFieldGTC() {
        guessFieldGTC.getButton().setOnAction(event -> {
            if (comprobarNombreCorrecto(guessFieldGTC.getTextField().getText())) {
                ganarRonda();
            } else {
                if (contadorGTCVidas.getContador() == 1) {
                    perderRonda();
                } else {
                    fallarIntento();
                }
                añadirFallo();
            }
            guessFieldGTC.getTextField().clear();
        });
    }
    
    private boolean comprobarNombreCorrecto(String intento) {
        boolean checkNombre = intento.equalsIgnoreCase(personajeActual.getName());
        boolean checkAlias = personajeActual.getAliasesList().stream()
                                .map(String::toLowerCase)
                                .anyMatch(alias -> alias.equals(intento.toLowerCase()));
        return checkNombre || checkAlias;
    }
    
    private void ganarRonda() {
        lblSoluciónCorrecta.setText("¡Correcto! ¿Otra ronda?");
        contadorGTCRacha.setContador(contadorGTCRacha.getContador() + 1);
        finDeRonda();
    }
    
    private void fallarIntento() {
        contadorGTCVidas.setContador(contadorGTCVidas.getContador() - 1);
    }
    
    private void añadirFallo() {
        fallos++;
        lblFallos.setText(String.valueOf(fallos));
        guessListGTC.sumarSugerenciaIncorrecta(guessFieldGTC.getTextField().getText());
        actualizarPista();
    }
    
    private void actualizarPista() {
            mostrarPista(contadorGTCVidas.getContador());
            intentosHastaPista--;
            lblVidasRestantesPista.setText(String.valueOf(intentosHastaPista));
    }
    
    private void mostrarPista(int numPista) {
        switch (numPista) {
            case 15 -> {
                hintButtonGTC.setTextoPista("Pista 1:\nGénero: " + parseGenero(personajeActual.getGender()));
                intentosHastaPista = 5;
            }
            case 10 -> {
                hintButtonGTC.setTextoPista(
                        hintButtonGTC.getTextoPista() +
                        "\nPista 2:\nFranquícia: " + personajeActual.getFirst_appeared_in_game().name()
                );
                intentosHastaPista = 5;
            }
            case 5 -> {
                hintButtonGTC.setTextoPista(
                        hintButtonGTC.getTextoPista() +
                        "\nPista 3:\nDescripción: " + cargarPistaDescrip()
                );
                intentosHastaPista = 5;
            }
            default -> {}
        }
    }
    
    private String cargarPistaDescrip() {
        String pista = personajeActual.getDeck();
        pista = pista.replace(personajeActual.getName(), "(MI PERSONAJE)");
        for (String alias : personajeActual.getAliasesList()) {
            pista = pista.replace(alias, "(MI PERSONAJE)");
        }
        return pista;
    }
    
    private String parseGenero(int genero) {
        switch (genero) {
            case 1 -> {return "Masculino";}
            case 2 -> {return "Femenino";}
            case 3 -> {return "Otro";}
            default -> {return "Otro";}
        }
    }
    
    private void perderRonda() {
        lblSoluciónCorrecta.setText("Has perdido, ¿echas otra?");
        contadorGTCVidas.setContador(contadorGTCVidas.getContador() - 1);
        finDeRonda();
    }
    
    private void finDeRonda() {
        imgGTC.setBlurFactor(1);
        lblNombres.setVisible(true);
        btnRendirse.setDisable(true);
        btnSiguiente.setDisable(false);
        if (contadorGTCVidas.getContador() >= 15) {
            mostrarPista(15);
        }
        if (contadorGTCVidas.getContador() >= 10) {
            mostrarPista(10);
        }
        if (contadorGTCVidas.getContador() >= 5) {
            mostrarPista(5);
        }
    }
    
    private void iniciarSiguienteRonda() {
        contadorGTCVidas.setContador(20);
        lblFallos.setText(String.valueOf(0));
        lblNombres.setVisible(false);
        lblSoluciónCorrecta.setVisible(false);
        btnRendirse.setDisable(false);
        btnSiguiente.setDisable(true);
        lblVidasRestantesPista.setText(String.valueOf(5));
        hintButtonGTC.setTextoPista("¡Tendrás que desbloquearla!");
        if (Main.personajes.indexOf(personajeActual) < Main.personajes.size()) {
            personajeActual = Main.personajes.get(Main.personajes.indexOf(personajeActual)+1);
        } else {
            personajeActual = Main.personajes.get(0);
        }
        rellenarDatosPersonaje();
    }

}
