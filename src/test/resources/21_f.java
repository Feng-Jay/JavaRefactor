public class FAKECLASS{
    private void processOptionToken(String token, boolean stopAtNonOption)
    {
        if (options.hasOption(token))
        {
            currentOption = options.getOption(token);
        }
        else if (stopAtNonOption)
        {
            eatTheRest = true;
        }

        tokens.add(token);
    }
}