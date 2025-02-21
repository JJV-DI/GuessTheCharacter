package GuessTheCharacter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterGTC {
    private int id;
    
    private String aliases;
    
    private String deck;
    
    private FirstAppeared first_appeared_in_game;
    
    private int gender;
    
    private CharacterImages image;
    
    private String name;

    public CharacterGTC() {
    }

    public CharacterGTC(int id, String aliases, String deck, FirstAppeared first_appeared_in_game, int gender, CharacterImages image, String name) {
        this.id = id;
        this.aliases = aliases;
        this.deck = deck;
        this.first_appeared_in_game = first_appeared_in_game;
        this.gender = gender;
        this.image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAliases() {
        return aliases;
    }
    
    public List<String> getAliasesList() {
        if (aliases != null) {
            return Arrays.asList(aliases.split("\n"));
        } else {
            return new ArrayList<>();
        }
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public FirstAppeared getFirst_appeared_in_game() {
        return first_appeared_in_game;
    }

    public void setFirst_appeared_in_game(FirstAppeared first_appeared_in_game) {
        this.first_appeared_in_game = first_appeared_in_game;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public CharacterImages getImage() {
        return image;
    }

    public void setImage(CharacterImages image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CharacterGTC other = (CharacterGTC) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "\n\nPERSONAJE:\nCharacterGTC{" + "id=" + id + ", aliases=" + aliases + ", deck=" + deck + ", first_appeared_in_game=" + first_appeared_in_game + ", gender=" + gender + ", image=" + image + ", name=" + name + '}';
    }

    
    
}
