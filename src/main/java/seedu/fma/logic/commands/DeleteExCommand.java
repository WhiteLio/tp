package seedu.fma.logic.commands;

import seedu.fma.commons.core.Messages;
import seedu.fma.commons.core.index.Index;
import seedu.fma.logic.commands.exceptions.CommandException;
import seedu.fma.model.Model;
import seedu.fma.model.exercise.Exercise;
import seedu.fma.model.log.Log;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes an exercise identified using it's displayed index from the exercise book.
 */
public class DeleteExCommand extends Command {

    public static final String COMMAND_WORD = "deleteex";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the exercise identified by the index number used in the displayed exercise list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Deleted Exercise: %1$s";

    private final Index targetIndex;

    public DeleteExCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExercise(exerciseToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteExCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteExCommand) other).targetIndex)); // state check
    }
}