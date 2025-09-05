package command.tokenizer.validation;

import command.tokenizer.TokenType;
import file.documents.DocumentType;

public record PathToken(String identifier, boolean isFile, boolean exists, DocumentType documentType) implements ValidatorToken {

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
        return TokenType.ARGUMENT;
    }

}
