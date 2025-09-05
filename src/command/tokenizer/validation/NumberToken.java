package command.tokenizer.validation;

import command.tokenizer.TokenType;

public record NumberToken(String identifier, Float upperBound, Float lowerBound) implements ValidatorToken {

    /**
     * Get the identifier of the validator token.
     * @return The identifier string
     */
    @Override
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Get the token type of the validator token.
     * @return The token type
     */
    @Override
    public TokenType getTokenType() {
        return TokenType.NUMBER;
    }

}
