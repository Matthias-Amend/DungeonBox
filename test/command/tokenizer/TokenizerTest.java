package command.tokenizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TokenizerTest {

    @Test
    public void testTokenize() {
        String testCommandString = "create -c \"DungeonBox/\" \"Matthias Amend\"";
        Token[] actualTokens = Tokenizer.tokenize(testCommandString);
        Token[] expectedTokes = new Token[]{new Token(TokenType.COMMAND, "create"), new Token(TokenType.OPTION, "-c"),
                new Token(TokenType.STRING, "DungeonBox/"), new Token(TokenType.STRING, "Matthias Amend")};
        assertArrayEquals(expectedTokes, actualTokens);
    }

    @Test
    public void testTokenizeNegativeNumber() {
        String testCommandString = "modify_health \"characters/Matthias Amend\" \"Head\" -10";
        Token[] actualTokens = Tokenizer.tokenize(testCommandString);
        Token[] expectedTokens = new Token[]{new Token(TokenType.COMMAND, "modify_health"),
                new Token(TokenType.STRING, "characters/Matthias Amend"),
                new Token(TokenType.STRING, "Head"), new Token(TokenType.NUMBER, "-10")};
        assertArrayEquals(expectedTokens, actualTokens);
    }

}
