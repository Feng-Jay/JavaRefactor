public class FAKECLASS{
    public void validate(final WriteableCommandLine commandLine)
        throws OptionException {
        // number of options found
        int present = 0;

        // reference to first unexpected option
        Option unexpected = null;

        for (final Iterator i = options.iterator(); i.hasNext();) {
            final Option option = (Option) i.next();

            // needs validation?
            boolean validate = option.isRequired() || option instanceof Group;

            // if the child option is present then validate it
            if (commandLine.hasOption(option)) {
                if (++present > maximum) {
                    unexpected = option;

                    break;
                }
                validate = true;
            }

            if (validate) {
                option.validate(commandLine);
            }
        }

        // too many options
        if (unexpected != null) {
            throw new OptionException(this, ResourceConstants.UNEXPECTED_TOKEN,
                                      unexpected.getPreferredName());
        }

        // too few option
        if (present < minimum) {
            throw new OptionException(this, ResourceConstants.MISSING_OPTION);
        }

        // validate each anonymous argument
        for (final Iterator i = anonymous.iterator(); i.hasNext();) {
            final Option option = (Option) i.next();
            option.validate(commandLine);
        }
    }
}