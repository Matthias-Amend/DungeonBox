package command.output;

import command.CommandType;
import command.ErrorType;

import java.io.File;
import java.util.List;

public record ExecutionReceipt(CommandType commandType, ErrorType error, String errorString) {
}
