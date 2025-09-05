package command.tokenizer;

import command.CommandType;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    /*
    * Example command string: create -c -p DungeonBox/ -n "Matthias Amend"
    * 1. Split string into token strings -> {"-c", "-p DungeonBox/", "-n "Matthias Amend""}
    *       Take into account that tokens located in quotation marks should not be split
    * 2. Convert token strings into Token objects
    * 3. Check token validity (does token have correct amount of arguments and are arguments of correct type)
     */


    public static Token[] tokenize(String commandString) {
        int index = 0;
        List<Token> tokenList = new ArrayList<>();
        while(index < commandString.length()) {
            char character = commandString.charAt(index);
            if(Character.isWhitespace(character)) {
                index++;
                continue;
            }
            if(character == '-') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(character);
                index++;
                if(Character.isDigit(commandString.charAt(index))) {
                    while(index < commandString.length() && (Character.isDigit(commandString.charAt(index)) ||
                            commandString.charAt(index) == '.')) {
                        stringBuilder.append(commandString.charAt(index));
                        index++;
                    }
                    if(stringBuilder.toString().matches("[-]?([0-9]*[.])?[0-9]+")) {
                        tokenList.add(new Token(TokenType.NUMBER, stringBuilder.toString()));
                    } else {
                        tokenList.add(new Token(TokenType.UNKNOWN, stringBuilder.toString()));
                    }
                } else if(Character.isLetter(commandString.charAt(index))) {
                    while(index < commandString.length() && Character.isLetter(commandString.charAt(index))) {
                        stringBuilder.append(commandString.charAt(index));
                        index++;
                    }
                    tokenList.add(new Token(TokenType.OPTION, stringBuilder.toString()));
                } else {
                    while(index < commandString.length() && !Character.isWhitespace(commandString.charAt(index))) {
                        stringBuilder.append(commandString.charAt(index));
                        index++;
                    }
                    tokenList.add(new Token(TokenType.UNKNOWN, stringBuilder.toString()));
                }
            } else if(Character.isDigit(character)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(character);
                index++;
                while(index < commandString.length() && (Character.isDigit(commandString.charAt(index)) ||
                        commandString.charAt(index) == '.')) {
                    stringBuilder.append(commandString.charAt(index));
                    index++;
                }
                if(stringBuilder.toString().matches("[-]?([0-9]*[.])?[0-9]+")) {
                    tokenList.add(new Token(TokenType.NUMBER, stringBuilder.toString()));
                } else {
                    tokenList.add(new Token(TokenType.UNKNOWN, stringBuilder.toString()));
                }
            } else if(character == '"') {
                StringBuilder stringBuilder = new StringBuilder();
                index++;
                while(index < commandString.length() && commandString.charAt(index) != '"') {
                    stringBuilder.append(commandString.charAt(index));
                    index++;
                }
                index++;
                tokenList.add(new Token(TokenType.STRING, stringBuilder.toString()));
            } else if(character == '/') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(character);
                index++;
                while(index < commandString.length()) {
                    if(Character.isWhitespace(commandString.charAt(index)) && commandString.charAt(index - 1) != '\\') {
                        break;
                    }
                    stringBuilder.append(commandString.charAt(index));
                    index++;
                }
                String tokenValue = stringBuilder.toString().replaceAll("\\\\", "");
                tokenList.add(new Token(TokenType.ARGUMENT, tokenValue));
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(character);
                index++;
                while(index < commandString.length() && !Character.isWhitespace(commandString.charAt(index))) {
                    stringBuilder.append(commandString.charAt(index));
                    index++;
                }
                String output = stringBuilder.toString();
                CommandType commandType = CommandType.getCommandType(output);
                if(commandType == null) {
                    tokenList.add(new Token(TokenType.UNKNOWN, output));
                } else {
                    tokenList.add(new Token(TokenType.COMMAND, output));
                }
            }
        }
        return tokenList.toArray(new Token[0]);
    }

}
