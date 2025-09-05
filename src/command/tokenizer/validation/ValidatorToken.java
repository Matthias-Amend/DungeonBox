package command.tokenizer.validation;

import command.tokenizer.TokenType;

public interface ValidatorToken {

    /**
     * Get the identifier of the validator token.
     * @return The identifier string
     */
    public String getIdentifier();

    /**
     * Get the token type of the validator token.
     * @return The token type
     */
    public TokenType getTokenType();

}
